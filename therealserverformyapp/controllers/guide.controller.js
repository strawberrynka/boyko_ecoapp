const Guide = require("../models/Guide")
const User = require("../models/User")
const generateRandomString = require("../utils/generateRandomString.js")

// {id, title, img, description, time, place, authorID, scores, maxUsers, currentUsers }
module.exports.create = async (req, res) => {
  try {
    let guide = await Guide.create({ 
        title: req.body.title,
        photo: req.files[0].filename,
        description: req.body.description,
        authorID: req.body.authorID,
        source: req.body.source,
        guideID: generateRandomString(10)
    })

    let result = await guide.save()
    res.send(result)
  } catch (err) {
    console.log(err.message);
    res.status(400).send("error")
  }
}

// {id}
module.exports.delete = async (req, res) => {
  await Guide.findOneAndDelete({guideID: req.query.id}).exec()
  res.status(204).send("Удалено")
}

// {id}
module.exports.getGuideByID = async (req, res) => {
  let result = await Guide.findOne({guideID: req.query.id}).exec()
  if (result == null) res.status(404).send("Not Found")
  else {
    let user = await User.findOne({ id: result.authorID }).exec()
    res.send({ authorName: user.name, title: result.title, photo: result.photo, description: result.description,
      authorID: result.authorID, source: result.source, guideID: result.guideID })
  }
}

module.exports.getGuides = async (req, res) => {
  let result = await Guide.find({}).exec()
  if (result == null) res.status(404).send("Not Found")
  else res.send(result)
}

module.exports.update = async (req, res) => {
    let result = await Guide.findOneAndUpdate({guideID: req.query.id}, {
        title: req.body.title,
        photo: req.files[0].filename,
        description: req.body.description,
        source: req.body.source
    }).exec()

    res.status(204).send(result)
}

module.exports.getSavedGuides = async (req, res) => {
  let user = await User.findOne({ id: req.query.authorID }).exec()

  if (user == null) res.status(404).send("Not Found")
  else {
    let arr = []
    for (let guideID of user.guidesList) {
      let guide = await Guide.findOne({ guideID: guideID }).exec()
      if (guide != null) arr.push(guide)
    }
    
    res.send({ item: arr })
  }
}
