let passport = require("passport")
let JwtStrategy = require("passport-jwt").Strategy
let ExtractJwt = require("passport-jwt").ExtractJwt
let User = require("../models/User.js")
let { jwtsecret } = require("./jwt.js")

let opts = {}
opts.jwtFromRequest = ExtractJwt.fromAuthHeaderAsBearerToken()
opts.secretOrKey = jwtsecret


passport.use(
  new JwtStrategy(opts, async (jwtPayload, done) => {
    try {
      let user = await User.findOne({id: jwtPayload.sub})

      if (user) done(null, user)
      else done(null, false)
    } catch (error) { return done(error, false) }
  })
)

module.exports = passport