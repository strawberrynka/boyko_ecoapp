const mongoose = require("mongoose");
const express = require('express');
const app = express();
require("dotenv").config();

(async function() {
    try {
        await mongoose.connect(process.env.DB_CONNECT, { useNewUrlParser: true, useUnifiedTopology: true });
        console.log("Сервер ожидает подключения...");

        process.on("SIGINT", async() => {
            await mongoose.disconnect();
            console.log("Приложение завершило работу");
            process.exit();
        });
    }
    catch (err) {
        console.log(err);
    }
}())