const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const mysql = require('mysql');

// parse application/json
app.use(bodyParser.json());

//create database connection
const conn = mysql.createConnection({
    host: 'mc.underscorexox.com',
    user: 'root',
    password: 'EmoBeemo6716!',
    database: 'hmdb'
});

//connect to database
conn.connect((err) =>{
    if(err) throw err;
    console.log('Mysql Connected...');
});

//show all userdata
app.get('/api/userdata',(req, res) => {
    let sql = "SELECT * FROM userdata";
    let query = conn.query(sql, (err, results) => {
        if(err) throw err;
        res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
    });
});

//show single user's data
app.get('/api/userdata/uuid/:id',(req, res) => {
    let sql = "SELECT * FROM userdata WHERE UUID= '"+req.params.id+"';";
    let query = conn.query(sql, (err, results) => {
        if(err) throw err;
        res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
    });
});
app.get('/api/userdata/name/:id',(req, res) => {
    let sql = "SELECT * FROM userdata WHERE username= '"+req.params.id+"';";
    let query = conn.query(sql, (err, results) => {
        if(err) throw err;
        res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
    });
});
app.get('/api/sales/:id',(req, res) => {
    let sql = "SELECT * FROM chest_shop_sales WHERE shop= '"+req.params.id+"';";
    let query = conn.query(sql, (err, results) => {
        if(err) throw err;
        res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
    });
});
app.get('/api/purchases/:id',(req, res) => {
    let sql = "SELECT * FROM chest_shop_sales WHERE player= '"+req.params.id+"';";
    let query = conn.query(sql, (err, results) => {
        if(err) throw err;
        res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
    });
});


//Server listening
app.listen(3000,() =>{
    console.log('Server started on port 3000...');
});