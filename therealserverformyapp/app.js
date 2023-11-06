const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const cors = require('cors')
const bodyParser = require('body-parser')

require("./configs/db")
require("dotenv").config()

const indexRouter = require('./routes/index');
const authUserRouter = require('./routes/authUser');
const userRouter = require('./routes/user');
const taskRouter = require('./routes/task');
const eventRouter = require('./routes/event');
const guideRouter = require('./routes/guide');
const habitRouter = require('./routes/habits');

const app = express();

app.use(cors())
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use('/uploads', express.static('uploads'));
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

app.use('/', indexRouter);
app.use('/auth/users', authUserRouter);
app.use('/users', userRouter);
app.use('/tasks', taskRouter);
app.use('/events', eventRouter);
app.use('/guides', guideRouter);
app.use("/habits", habitRouter)

const http = require("http")
const server = http.createServer(app)

const Habit = require("./models/Habit")
const generateRandomString = require("./utils/generateRandomString.js")

const cron = require('node-cron');
const { parse, format, subDays, getISOWeek } = require('date-fns');
const moment = require('moment-timezone');

const moscowTime = moment.tz('00:18', 'HH:mm', 'Europe/Moscow');
const utcTime = moscowTime.clone().utc();

const schedule = `${utcTime.minute()} ${utcTime.hour()} * * *`;

let foo = '0 0 * * *'
cron.schedule(foo, () => {
    const currentDate = new Date();
    const datePrev = subDays(currentDate, 1)
    const previousDayString = format(datePrev, 'dd.MM.yy');

    const formattedDate = format(currentDate, 'dd.MM.yy');

    Habit.find({ dateOfCreated: previousDayString }).then(habits => {
      if (habits != null) {
          habits.forEach(item => {
            if (item != null) {
              let newHabit = { authorID: item.authorID, habitID: generateRandomString(20), type: item.type, frequency: item.frequency, title: item.title, isDone: false, dateOfCreated: formattedDate }
              Habit.create(newHabit).then(res => res.save())
            }
          })
      }
    })
});

cron.schedule('0 0 * * 1', () => {
    const currentDate = new Date();

    const formattedDate = format(currentDate, 'dd.MM.yy');
    Habit.find({ type: req.query.type, authorID: req.query.authorID, dateOfCreated: { $regex: formattedTimeWeek, $options: 'i' } }).then(result => {
        if (result == null) res.status(404).send("Not Found")
        else {
          for (let item of result) {
            const date1 = parse(item.dateOfCreated, 'dd.MM.yy', new Date());
            const previousWeekNumber = getISOWeek(date1)
            if (previousWeekNumber == getISOWeek(currentDate) - 1) {
                let newItem = { ...item, habitID: generateRandomString(20), dateOfCreated: formattedDate, isDone: false }
                Habit.create(newItem).then(res1 => res1.save())
            }
          }
        }
    })
});

// server.listen(process.env.PORT || 3000, "192.168.0.101", () => console.log("сервер запущен 8000"))
server.listen(process.env.PORT || 8080, () => console.log("сервер запущен 8000"))   