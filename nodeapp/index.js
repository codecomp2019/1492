const express = require('express');
const app = express();
let memes = [
    {
        file: "badluckfire.jpg",
        description: "Bad Luck Brian",
        text: "Stop, drop and roll into another fire"
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

app.get("/meme", (req, res) => res.send(memes));
app.get("/meme/:id", (req, res) => res.send(memes[req.params.id]));
app.get('/', (req, res) => res.send('Express Server for hosting memes for our app'));
app.listen(3000, () => console.log('Hosting memes....'));

//Static folder using a logical path
app.use('/memes', express.static(__dirname + '/memes'));

