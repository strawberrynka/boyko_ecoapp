const mongoose = require("mongoose")

const GuideSchema = new mongoose.Schema({
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
    authorID: {
        type: String,
        required: true
    },
    source: {
        type: String,
        required: true
    },
    guideID: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model("Guide", GuideSchema)