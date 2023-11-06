const express = require('express');
const router = express.Router();
const Rating = require("../models/Rating")
const generateRandomString = require("../utils/generateRandomString")

router.get('/', async (req, res, next) => res.send("Добро пожаловать!"));

router.get("/image/:imageName", async (req, res) => {
    const options = { root: "./" }
    res.sendFile("uploads/" + req.params.imageName, options)
})

router.put("/set_rating", async (req, res) => {
    let rating = await Rating.findOne({ authorID: req.body.authorID, guideID: req.body.guideID }).exec()
    if (rating != null) {
        rating.mark = req.body.mark
        await rating.save()
    } else rating = await Rating.create({ authorID: req.body.authorID, guideID: req.body.guideID, mark: req.body.mark, ratingID: generateRandomString(20) })

    res.send(rating)
})

router.get("/get_rating", async (req, res) => {
    let rating = await Rating.findOne({ authorID: req.query.authorID, guideID: req.query.guideID }).exec()
    if (rating != null) res.send(rating)
    else res.status(404).send("Not Found")
})

module.exports = router;