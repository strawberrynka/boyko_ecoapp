const express = require('express');
const router = express.Router();
const guideController = require("../controllers/guide.controller.js")
let upload = require("../configs/upload.js")
let passport = require("../configs/passportConfig.js")

router.post("/create", upload.array("img"), passport.authenticate('jwt', { session: false }), guideController.create)
router.delete("/delete", passport.authenticate('jwt', { session: false }), guideController.delete)
router.get("/getGuideByID", passport.authenticate('jwt', { session: false }), guideController.getGuideByID)
router.get("/get_guides", passport.authenticate('jwt', { session: false }), guideController.getGuides)
router.put("/change_guide", upload.array("img"), passport.authenticate('jwt', { session: false }), guideController.update)
router.get("/get_saved_guides", passport.authenticate('jwt', { session: false }), guideController.getSavedGuides)

module.exports = router;