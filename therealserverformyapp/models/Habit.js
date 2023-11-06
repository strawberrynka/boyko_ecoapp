const mongoose = require("mongoose")

const HabitSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    frequency: {
        type: Number,
        required: true
    },
    type: {
        type: String,
        required: true
    },
    habitID: {
        type: String,
        required: true
    },
    authorID: {
        type: String,
        required: true
    },
    isDone: {
        type: Boolean,
        required: false,
        default: false
    },
    dateOfCreated: {
        type: String,
        required: false
    }
})

module.exports = mongoose.model("Habit", HabitSchema)