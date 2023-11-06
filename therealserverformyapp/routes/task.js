const express = require('express');
const router = express.Router();
const taskController = require('../controllers/task.controller');
let passport = require("../configs/passportConfig.js")
let upload = require("../configs/upload.js")

router.post("/create", passport.authenticate('jwt', { session: false }), taskController.create)
router.get("/getTaskByID", passport.authenticate('jwt', { session: false }), taskController.getTaskByID)
router.delete("/delete", passport.authenticate('jwt', { session: false }), taskController.makeTaskDone)
router.delete("/deleteTask", passport.authenticate('jwt', { session: false }), taskController.delete)
router.get("/getTasksList", passport.authenticate('jwt', { session: false }), taskController.getTasksList)
router.get("/getAllTasks", passport.authenticate('jwt', { session: false }), taskController.getAllTasks)
router.post("/takeTask", upload.array("img"), passport.authenticate('jwt', { session: false }), taskController.takeTask)
router.delete("/cancelTakeTask", passport.authenticate('jwt', { session: false }), taskController.cancelTakeTask)
router.get("/getTasksListWithUser", passport.authenticate('jwt', { session: false }), taskController.getTasksListWithUser)

module.exports = router;
