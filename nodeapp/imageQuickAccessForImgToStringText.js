//Used: To try fast meme access for test.js
//Download.js gets images url and makes them available to test.js
var request = require('request')
var fs = require('fs')
var url= 'https://starecat.com/content/wp-content/uploads/god-damn-i-love-being-white-original-meme.jpg'

var filename = 'pic.png'

var writeFileStream = fs.createWriteStream(filename)

request(url).pipe(writeFileStream).on('close', function() {
 console.log(url, 'saved to', filename)
})