const Event = require("../models/Event")
const Guide = require("../models/Guide")
const User = require("../models/User")
const Comment = require("../models/Comment")
const generateRandomString = require("../utils/generateRandomString.js")
const { format, differenceInDays, getDay, getMonth, getYear, parse } = require('date-fns'); 

// {id, title, img, description, time, place, authorID, scores, maxUsers, currentUsers }
module.exports.create = async (req, res) => {
  try {
    console.log(req);
    let event = await Event.create({ 
        title: req.body.title,
        photo: req.files[0].filename,
        description: req.body.description,
        time: req.body.time,
        place: req.body.place,
        authorID: req.body.authorID,
        scores: Number.parseInt(req.body.scores),
        maxUsers: Number.parseInt(req.body.maxUsers),
        currentUsers: Number.parseInt(req.body.currentUsers),
        eventID: generateRandomString(10),
        longt: parseFloat(req.body.longt),
        lat: parseFloat(req.body.lat)
    })
    
    let user = await User.findOne({ id: req.body.authorID }).exec()
    user.scores -= Number.parseInt(req.body.scores)
    await user.save()
  
    let result = await event.save()
    res.send(result)
  } catch (err) {
    console.log(err.message);
    res.status(400).send("error")
  }
}

// {id}
module.exports.delete = async (req, res) => {
  await Event.findOneAndDelete({eventID: req.query.id}).exec()
  res.status(204).send("Удалено")
}

// {id}
module.exports.getEventByID = async (req, res) => {
  let result = await Event.findOne({eventID: req.query.id}).exec()
  if (result == null) res.status(404).send("Not Found")
  else {
    let user = await User.findOne({ id: result.authorID }).exec()
    if (user != null) res.send({ eventID: result.eventID, authorID: result.authorID, dateOfCreated: result.dateOfCreated,
      title: result.title, photo: result.photo, authorName: user.name, description: result.description,
      time: result.time, place: result.place, scores: result.scores, maxUsers: result.maxUsers, currentUsers: result.currentUsers,
      usersList: result.usersList, lat: result.lat, longt: result.longt })
    else res.send(result)
  }
}

// {eventID}
module.exports.addUserToEvent = async (req, res) => {
  let result = await Event.findOne({eventID: req.body.eventID}).exec()
  if (result == null) res.status(404).send("Not Found")
  else {
    result.usersList.push(req.body.authorID)
    await result.save()
    res.send(result)
  }
}

module.exports.refusePeople = async (req, res) => {
  let result = await Event.findOne({ eventID: req.body.eventID }).exec()
  if (result == null) res.status(404).send("Not Found")
  else {
    let index = result.usersList.findIndex(item => item == req.body.authorID)
    if (index != -1) result.usersList.splice(index, 1)
    await result.save()
    res.send(result)
  }
}

module.exports.getEventsList = async (req, res) => {
  let result = await Event.find({}).exec()
  res.send({item: result})
}

module.exports.findEventsByAuthorID = async (req, res) => {
  let result = await Event.find({ usersList: req.query.authorID }).exec()
  if (result != null) res.send({item: result})
  else res.status(404).send("Not Found")
}

module.exports.findAuthorsEvents = async (req, res) => {
  let result = await Event.find({ authorID: req.query.authorID }).exec()
  if (result != null) res.send({item: result})
  else res.status(404).send("Not Found")
}

function distance(lat1, lon1, lat2, lon2) {
  const R = 6371;
  const dLat = deg2rad(lat2 - lat1);
  const dLon = deg2rad(lon2 - lon1);

  const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  const distance = R * c;

  return distance;
}

function deg2rad(deg) {
  return deg * (Math.PI / 180);
}

function sortByDistance(userLat, userLong, array) {
  let result = array.sort((a, b) => {
    const distanceA = distance(userLat, userLong, a.lat, a.longt);
    const distanceB = distance(userLat, userLong, b.lat, b.longt);

    return distanceA - distanceB;
  });

  return result
}

module.exports.findNearestEventsByAuthorCoords = async (req, res) => {
  let result = await Event.find({}).exec()
  let arr = sortByDistance(req.query.lat, req.query.longt, result)

    res.send({item: arr})
}

module.exports.getUsersFromEvents = async (req, res) => {
  try {
    let result = await Event.findOne({ eventID: req.query.eventID }).exec()
    let arr = []
    for (let userID of result.usersList) {
      if (userID != req.query.authorID) {
        let user = await User.findOne({ id: userID }).exec()
        if (user != null) arr.push(user)
      }
    }

    res.send({ item: arr })
  } catch (err) {
    res.status(400).send(err.message)
  }
}

module.exports.searchPosts = async (req, res) => {
  try {
    const regex = new RegExp(req.query.title, 'i');
    let result = await Event.find({ title: regex }).exec()
    let result2 = await Guide.find({ title: regex }).exec()
    let arr = []
    for (let event of result) {
      arr.push({ title: event.title, image: event.photo, type: "event", id: event.eventID })
    }
    for (let guide of result2) {
      arr.push({ title: guide.title, image: guide.photo, type: "guide", id: guide.guideID })
    }

    res.send({ item: arr })
  } catch (err) {
    res.status(400).send(err.message)
  }
}

module.exports.createComment = async (req, res) => {
  try {
    const currentDate = new Date();
    const formattedDate = format(currentDate, 'dd.MM.yy');

    let result = await Comment.create({
      content: req.body.content,
      authorID: req.body.authorID,
      eventID: req.body.eventID,
      id: generateRandomString(10),
      dateOfCreated: formattedDate
    })

    let resResult = await result.save()

    let user = await User.findOne({ id: req.body.authorID }).exec()

    res.send({ id: resResult.id, eventID: resResult.eventID, authorID: resResult.authorID, dateOfCreated: resResult.dateOfCreated, content: resResult.content, photo: user.photo, login: user.name })
  } catch (err) {
    res.status(400).send(err.message)
  }
}

module.exports.getCommentsList = async (req, res) => {
  try {
    let result = await Comment.find({ eventID: req.query.eventID }).exec()
    let arr = []
    for (let item of result) {
      let user = await User.findOne({ id: item.authorID }).exec()
      arr.push({ id: item.id, eventID: item.eventID, authorID: item.authorID, dateOfCreated: item.dateOfCreated, content: item.content, photo: user.photo, login: user.name })
    }

    res.send({ item: arr })
  } catch (err) {
    res.status(400).send(err.message)
  }
}

module.exports.deleteComment = async (req, res) => {
  try {
    await Comment.findOneAndDelete({ id: req.query.id }).exec()
    res.status(204).send("Удалено")
  } catch (err) {
    res.status(400).send(err.message)
  }
}