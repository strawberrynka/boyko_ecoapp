const mongoose = require("mongoose")

const EventSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    photo: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    time: {
        type: String,
        required: true
    },
    place: {
        type: String,
        required: true
    },
    authorID: {
        type: String,
        required: true
    },
    scores: {
        type: Number,
        required: true
    },
    maxUsers: {
        type: Number,
        required: true
    },
    currentUsers: {
        type: Number,
        required: 1
    },
    eventID: {
        type: String,
        required: true
    },
    usersList: {
        type: [String],
        required: false
    },
    lat: {
        type: Number,
        required: false
    },
    longt: {
        type: Number,
        required: false
    }
})

module.exports = mongoose.model("Event", EventSchema)