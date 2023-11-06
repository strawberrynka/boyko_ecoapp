const mongoose = require("mongoose")

const UserSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    login: {
        type: String,
        required: false
    },
    password: {
        type: String,
        required: true
    },
    photo: {
        type: String,
        required: false
    },
    scores: {
        type: Number,
        required: false,
        default: 200
    },
    id: {
        type: String,
        required: true
    },
    habitsList: {
        type: [String],
        required: false
    },
    guidesList: {
        type: [String],
        required: false
    }
})

module.exports = mongoose.model("User", UserSchema)