let User = require("../models/User.js")
let { jwtsecret } = require("../configs/jwt.js")
let jwt = require("jsonwebtoken")
const bcrypt = require("bcrypt")
const generateRandomString = require("../utils/generateRandomString.js")

module.exports.login = async (req, res) => {
  let user = await User.findOne({email: req.body.email}).exec()
  if (!user) {
    return res.status(404).send({ token: "" })
  } else {
    const match = await bcrypt.compare(req.body.password, user.password);
    if (!match) return res.status(400).send({ token: "" })
    else {
      let token = jwt.sign({
        sub: user.id,
        email: user.email,
        name: user.name,
      }, jwtsecret)
      return res.send({token: "Bearer " + token, user: { name: user.name, email: user.email, id: user.id }})
    }
  }
}

module.exports.signup = async (req, res, next) => {
  const isUserWithEmail = await User.findOne({ email: req.body.email }).exec()
  
  if (isUserWithEmail != null) return res.status(403).send({ message: "Пользователь с такой почтой уже существует" })

  const salt = await bcrypt.genSalt(10)
  const password = await bcrypt.hash(req.body.password, salt);
  let user = await User.create({ 
    password,
    name: req.body.name, 
    email: req.body.email, 
    id: generateRandomString(20),
    login: generateRandomString(16) })
  let token = jwt.sign({
    sub: user.id,
    email: user.email,
    name: user.name
  }, jwtsecret)
  res.send({token: "Bearer " + token, user: { login: user.name, email: user.email, id: user.id }})
}
