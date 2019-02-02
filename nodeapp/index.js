const express = require('express');
const app = express();
app.get('/', (req, res) => res.send('Express Server for hosting memes for our app'));
app.listen(3000, () => console.log('Hosting memes....'));

app.use(express.static(__dirname + '/memes'));