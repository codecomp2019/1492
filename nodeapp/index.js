const express = require('express');
const app = express();
var upload = require("express-fileupload");
let memes = [
    {
        file: "badluckfire.jpg",
        description: "Bad Luck Brian",
        text: "Stops, drops and rolls... into another fire"
    },
    {
        file: "firstworldproblem.jpg",
        description: "First World Problem",
        text: "Wants to eat cake, brushed teeth already"
    },
    {
        file: "philosoraptor.jpg",
        description: "Philosoraptor",
        text: "Is an argument between two vegans still called a beef?"
    },
    {
        file: "scumbagsteve.jpg",
        description: "Scumbag Steve",
        text: "Borrows your college books, sells them back to bookstore"
    },
    {
        file: "successkid.jpg",
        description: "Success Kid",
        text: "Ordered a 10 piece chicken mcnugget, got 11"
    }
];

var fs = require('fs');

// function to encode file data to base64 encoded string
function base64_encode(file) {
    // read binary data
    var bitmap = fs.readFileSync(file);
    // convert binary data to base64 encoded string
    return new Buffer(bitmap).toString('base64');
}

//Static folder using a logical path
app.use('/memes', express.static(__dirname + '/memes'));
app.use(upload());

//testing base64 encoding
var string = base64_encode("memes/successkid.jpg");
app.get("/test", (req, res) => res.send(string));
//displays on test page

//message on console
app.listen(3000, () => console.log('Hosting memes....'));
//Route for all JSON data of all memes
app.get("/meme", (req, res) => res.send(memes));
//Routes for individual meme JSON data
app.get("/meme/:id", (req, res) => res.send(memes[req.params.id]));
//landing page, for uploading more memes
app.get('/', (req, res) =>
    res.sendFile(__dirname +"/index.html")
);

//post function 
app.post("/", function (req, res) {
    if (req.files) { //check if file has been uploaded
        var file = req.files.filename;
        var filename = file.name;
        file.mv("./memes/" + filename, function (err) {
            if (err) {
                console.log(err);
                res.send("Error occured");
            }
            else {
                res.send("Done");
                var object = {
                    file: ""+filename,
                    description: "Description of meme",
                    text: "text of meme"
                }
                memes.push(object);
            }
        })
    }
    else {
        res.send("No file added");
    }
});