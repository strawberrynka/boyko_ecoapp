const User = require("../models/User")
const Event = require("../models/Event")
const bcrypt = require("bcrypt")
const Habit = require("../models/Habit")

// {id, img}
module.exports.addPhoto = async (req, res) => {
  try {
    let user = await User.findOne({id: req.user.id}).exec()
    user.photo = req.file.filename
  
    console.log(req.file.filename);

    let result = await user.save()
    res.status(200).send(result) 
  } catch (err) {
    console.log(err.message);
    res.status(400).send("error")
  }
}

module.exports.changeScores = async (req, res) => {
  try {
    let user = await User.findOne({ id: req.body.id }).exec()
    let event = await Event.findOne({ eventID: req.body.eventID }).exec()
    
    user.scores += (event.scores / event.maxUsers)
    let index = event.usersList.findIndex(item => item == req.body.id)
    if (index != -1) {
      event.usersList.splice(index, 1)
      console.log(event.usersList);
      event.currentUsers = event.currentUsers - 1
    }

    await event.save()
    let result = await user.save()
      
    res.send(result)
  } catch (err) {
    console.log(err.message);
    res.status(400).send("error")
  }
}

// query {id}
module.exports.getUserByID = async (req, res) => {
  let user = await User.findOne({id: req.query.id}).exec()
  if (user == null) res.status(404).send("Not found")
  else res.send(user)
}

// {old_password, new_password, img, name, id}
module.exports.editProfile = async (req, res) => {
  try {
    let user = await User.findOne({id: req.user.id}).exec()

    // const match = await bcrypt.compare(req.body.old_password, user.password);
    // if (!match) return res.status(400).send({ message: "Incorrect Password" })

    user.name = req.body.name

    // const salt = await bcrypt.genSalt(10)
    // const password = await bcrypt.hash(req.body.new_password, salt);
    // user.password = password

    // if (req.files[0]?.filename != null) user.photo = "uploads/" + req.files[0].filename

    const result = await user.save()
    res.status(201).send(result)
  } catch (error) {
    console.log(error.message);
    return res.status(400).send({message: error.message})
  }
}

module.exports.editLogin = async (req, res) => {
  try {
    let user = await User.findOne({id: req.user.id}).exec()

    user.login = req.body.login
    const result = await user.save()

    res.status(201).send(result)
  } catch (error) {
    console.log(error.message);
    return res.status(400).send({message: error.message})
  }
}

module.exports.addHabit = async (req, res) => {
  try {
    let user = await User.findOne({id: req.user.id}).exec()
    if (user == null) return res.status(404).send("not found") 

    user.habitsList.push(req.body.title)
    const result = await user.save()
    res.status(201).send(result)
  } catch (error) {
    console.log(error.message);
    return res.status(400).send({message: error.message})
  }
}

module.exports.removeHabit = async (req, res) => {
  let user = await User.findOne({id: req.user.id}).exec()
  if (user == null) return res.status(404).send("not found") 

  let index = user.habitsList.findIndex(item => item == req.body.title)
  if (index != -1) user.habitsList.splice(index, 1)

  const result = await user.save()
  res.send(result)
}

module.exports.getHabitByTitle = async (req, res) => {
  let habit = await Habit.findOne({ title: req.query.title }).exec()
  if (habit != null) res.send(habit)
  else res.status(404).send("not found")
}

module.exports.updateGuideToUser = async (req, res) => {
  let user = await User.findOne({ id: req.body.userID }).exec()
  if (user != null) {
    let index = user.guidesList.findIndex(item => item == req.body.guideID)
    if (index != -1) user.guidesList.splice(index, 1)
    else user.guidesList.push(req.body.guideID)

    await user.save()
    res.status(200).send(user)
  }
  else res.status(404).send("not found")
}