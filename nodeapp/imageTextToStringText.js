//Test file, is used to turn image text to actual string text
//using Tesseract, an OCR package provide for node
var Tesseract = require('tesseract.js');
var fs = require('fs');
var Jimp = require('jimp');
//dummy file: Meme with drake in it
//filename can be any image but in this case it is a dummy file
var filename = 'drake.png';

 
// open filename
Jimp.read(filename, (err, img) => {
  if (err) throw err;
  img
  //imageMod gets modified, respective to the file provided to the filename
    .resize(300, 256) // resize
    .quality(100) // set JPEG quality
    .greyscale() // set greyscale
    .write('imageMod.jpg'); // save
	//console.log(base64_encode('imageMod.jpg'));
});
//image text to actual text
Tesseract.recognize('imageMod.jpg')
 .progress(function  (p) { console.log('progress', p)  })
 .catch(err => console.error(err))
 .then(function (result) {
   console.log(result.text)
   process.exit(0)
})
// function to encode file data to base64 encoded string
function base64_encode(file) {
   // read binary data
   var bitmap = fs.readFileSync(file);
   // convert binary data to base64 encoded string
   return new Buffer(bitmap).toString('base64');
}
