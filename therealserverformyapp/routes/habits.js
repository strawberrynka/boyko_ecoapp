const express = require('express');
const router = express.Router();
const habitController = require("../controllers/habit.controller.js")
let upload = require("../configs/upload.js")
let passport = require("../configs/passportConfig.js")

router.post("/create", passport.authenticate('jwt', { session: false }), habitController.create)
router.delete("/delete", passport.authenticate('jwt', { session: false }), habitController.delete)
router.get("/getList", passport.authenticate('jwt', { session: false }), habitController.getHabitsList)
router.get("/getHabitsByType", passport.authenticate('jwt', { session: false }), habitController.getHabitsByType)
router.put("/habitUpdate", passport.authenticate('jwt', { session: false }), habitController.habitUpdate)
router.get("/getStatistics", passport.authenticate('jwt', { session: false }), habitController.getStatistics)

module.exports = router;