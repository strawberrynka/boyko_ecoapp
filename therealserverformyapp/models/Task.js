const mongoose = require("mongoose")

const TaskSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: false
    },
    scores: {
        type: Number,
        required: true
    },
    taskID: {
        type: String,
        required: true
    },
    authorID: {
        type: String,
        required: true
    },
    dateOfCreated: {
        type: String,
        required: true
    },
    images: {
        type: [String],
        required: false
    },
    userDescription: {
        type: String,
        required: false
    },
    userID: {
        type: String,
        required: false
    }
})

module.exports = mongoose.model("Task", TaskSchema)