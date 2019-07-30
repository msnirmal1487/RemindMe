  // This module is used to read an object file to array of objects - ASYNCHRONOUSLY.
// fileName should be relative to the path where the script is available starting with "./"

var readObjFile = function fnReadObjFile (fileName, cb){

  var logger_util = require('./logger_util');
  var logger = logger_util.logger.getLogger('ReadFile');
  var _ = require('lodash')

  var fs = require('fs');
  var readline = require('readline');
  var stream = require('stream');


  if (fs.existsSync(fileName)){
    processfile();
  } else {
    cb(null, []);
  }
  function processfile(){
    // Initalise instream and out stream
    var instream = fs.createReadStream(fileName);
    var outstream = new stream;
    var rl = readline.createInterface(instream, outstream);
    var outObj = "";

    // If any error happen, call the error block
    rl.on('error',function(err){
      logger.error('Failed to Read from file ' + fileName);
      cb(err);
    });

    // For every line read, push it as an JSON object to out array
    rl.on('line', function(line) {
      // process line here
      outObj = outObj + line ;
    });

    //On close event call back with contents
    rl.on('close', function() {
      // do something on finish here
      //logger.debug(outObjArray);
      cb(null, outObj);

    });
  }
}

module.exports.readObjFile = readObjFile;