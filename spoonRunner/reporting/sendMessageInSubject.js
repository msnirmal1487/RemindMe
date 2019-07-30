var mailer = require("./uSendMail")
  
process.argv.forEach((val, index) => {
    //  console.log(`${index}: ${val}`)
      if(index == 2){
        mailer.sendMessageInSubject(val)
      }
    })