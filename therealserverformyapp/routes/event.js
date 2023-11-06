const express = require('express');
const router = express.Router();
const eventController = require("../controllers/event.controller")
let upload = require("../configs/upload.js")
let passport = require("../configs/passportConfig.js")

router.post("/create", upload.array("img"), passport.authenticate('jwt', { session: false }), eventController.create)
router.delete("/delete", passport.authenticate('jwt', { session: false }), eventController.delete)
router.get("/getEventByID", passport.authenticate('jwt', { session: false }), eventController.getEventByID)
router.put("/addUserToEvent", passport.authenticate('jwt', { session: false }), eventController.addUserToEvent)
router.get("/all", passport.authenticate('jwt', { session: false }), eventController.getEventsList)
router.put("/removeUserToEvent", passport.authenticate("jwt", { session: false }), eventController.refusePeople)
router.get("/findEventsByAuthorID", passport.authenticate('jwt', {session: false}), eventController.findEventsByAuthorID)
router.get("/findAuthorsEvents", passport.authenticate('jwt', {session: false}), eventController.findAuthorsEvents)
router.get("/findNearestEventsByAuthorCoords", passport.authenticate("jwt", {session: false}), eventController.findNearestEventsByAuthorCoords)
router.get("/getUsersFromEvents", passport.authenticate("jwt", {session: false}), eventController.getUsersFromEvents)
router.get("/searchPosts", passport.authenticate("jwt", {session: false}), eventController.searchPosts)
router.post("/create_comment", passport.authenticate("jwt", {session: false}), eventController.createComment)
router.get("/get_comments", passport.authenticate("jwt", {session: false}), eventController.getCommentsList)
router.delete("/delete_comment", passport.authenticate("jwt", {session: false}), eventController.deleteComment)

module.exports = router;