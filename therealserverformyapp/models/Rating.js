const mongoose = require("mongoose")

const RatingSchema = new mongoose.Schema({
    guideID: {
        type: String,
        required: true
    },
    authorID: {
        type: String,
        required: true
    },
    mark: {
        type: Number,
        required: true
    },
    ratingID: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model("Rating", RatingSchema)