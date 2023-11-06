const mongoose = require("mongoose")

const CommentSchema = new mongoose.Schema({
    content: {
        type: String,
        required: true
    },
    dateOfCreated: {
        type: String,
        required: true
    },
    id: {
        type: String,
        required: true
    },
    eventID: {
        type: String,
        required: true
    },
    authorID: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model("Comment", CommentSchema)