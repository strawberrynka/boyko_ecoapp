let moment = require("moment")
let multer  = require('multer')

let storage = multer.diskStorage({
  destination(req, file, callback) {
    if (
      file.mimetype === "image/png" ||
      file.mimetype === "image/jpeg" ||
      file.mimetype === "image/gif" || file.mimetype === "image/*"
    ) callback(null, "uploads/");
    if (file.mimetype === "audio/mpeg") callback(null, "../audio/");
  },
  filename(req, file, callback) {
    let date = moment().format("DDMMYYYY-HHmmss_SSS");
    callback(null, date + "-" + file.originalname);
  },
});

let fileFilter = (req, file, callback) => {
  file.mimetype === "image/png" || file.mimetype === "image/jpeg" || 
  file.mimetype === "image/gif" || file.mimetype === "audio/mpeg" || file.mimetype === "image/*"  ? 
    callback(null, true) : callback(null, false)
}


module.exports = multer({ storage, fileFilter })