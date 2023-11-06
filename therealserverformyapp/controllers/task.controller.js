const Task = require("../models/Task")
const User = require("../models/User")
const generateRandomString = require("../utils/generateRandomString.js")
const { format, differenceInDays, getDay, getMonth, getYear, parse } = require('date-fns'); 

// {title, scores, authorID}
module.exports.create = async (req, res) => {
    try {
        const currentDate = new Date();
        const formattedDate = format(currentDate, 'dd.MM.yy');

        let task = await Task.create({ 
            title: req.body.title,
            scores: req.body.scores,
            authorID: req.body.authorID,
            description: req.body.description,
            dateOfCreated: formattedDate,
            taskID: generateRandomString(10) 
        })

        let user = await User.findOne({ id: req.body.authorID }).exec()
        user.scores -= req.body.scores

        await user.save()

        let result = await task.save()
        res.send(result) 
    } catch (err) {
        console.log(err.message);
        res.status(400).send(err.message)
    }
}


// query {id}
module.exports.makeTaskDone = async (req, res) => {
    try {
        let result = await Task.findOne({taskID: req.query.taskID}).exec()
        let user = await User.findOne({ id: result.userID }).exec()
        user.scores += result.scores

        await user.save()
        await result.deleteOne()

        res.send(user)
    } catch (error) {
        console.log(error.message);
        res.status(400).send(error.message)
    }
}

module.exports.delete = async (req, res) => {
    try {
        await Task.findOneAndDelete({ taskID: req.query.taskID });
        res.send("...")
    } catch (error) {
        console.log(error.message);
        res.status(400).send(error.message)
    }
}

// {id}
module.exports.getTaskByID = async (req, res) => {
    try {
        let result = await Task.findOne({taskID: req.query.id}).exec()
        let user = await User.findOne({ id: result.authorID }).exec()
        
        if (result == null) res.status(404).send("Not Found")
        else res.send({ title: result.title, description: result.description, scores: result.scores,
            taskID: result.taskID, authorID: result.authorID, dateOfCreated: result.dateOfCreated, images: result.images,
            userDescription: result.userDescription, userID: result.userID, authorName: user.name })
    } catch (error) {
        console.log(error.message);
        res.status(400).send(error.message)
    }
}

module.exports.getAllTasks = async (req, res) => {
    try {
        let result = await Task.find({}).exec()
        res.send({ item: result })
    } catch (error) {
        console.log(error.message);
        res.status(400).send(error.message)
    }
}

// {authorID}
module.exports.getTasksList = async (req, res) => {
    let result = await Task.find({authorID: req.query.authorID}).exec()
    if (result == null) res.status(404).send("Not Found")
    else res.send({item: result})
}

module.exports.getTasksListWithUser = async (req, res) => {
    let result = await Task.find({userID: req.query.userID}).exec()
    if (result == null) res.status(404).send("Not Found")
    else res.send({item: result})
}

module.exports.cancelTakeTask = async (req, res) => {
    try {
        let result = await Task.findOne({taskID: req.query.taskID}).exec()
        result.userID = ""
        result.userDescription = ""
        result.images = []

        let resResult = await result.save()
        res.send(resResult)
    } catch (error) {
        console.log(error.message);
        res.status(400).send(error.message)
    }
}

module.exports.takeTask = async (req, res) => {
    try {
        let result = await Task.findOne({taskID: req.body.taskID}).exec()
        result.userID = req.body.userID
        result.userDescription = req.body.userDescription
        for (let i = 0; i < req.files?.length; i++) {
            result.images.push(req.files[i].filename)
        }

        let resResult = await result.save()
        res.send(resResult)
    } catch (error) {
        console.log(error.message);
        res.status(400).send(error.message)
    }
}