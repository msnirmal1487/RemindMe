var mailTo = function fnMailTo(inMailOptions, cb){

  var nodemailer = require('nodemailer');
  var transporter = nodemailer.createTransport();

  var logger_util = require('./logger_util');
  var logger = logger_util.logger.getLogger('mail');

  if (cb == null){
    cb = inMailOptions ;
    inMailOptions = null ;
  }

  var mailOptions = inMailOptions || {
     from: 'nirmal.subramanian@staples.com',
     to: 'nirmal.subramanian@staples.com',
     subject: 'Test mail',
     html: '<b>hello world!</b>'
  };

  transporter.sendMail(mailOptions, (err, info)=>{
    if (err) {
      logger.error(err);
      cb(err);
    }
    else {
      logger.info(info);
      transporter.close(cb(null,"Mail Sent"));
    }
  });
}

var mailOptions = {
  from: "nirmal.subramanian@staples.com",
  to: "nirmal.subramanian@staples.com",
  text: "\nRegards,\nNirmal",
  subject: "Automation : "
};

var sendMessageInSubject = function fnSendMessageInSubject(inMessage){

  mailOptions.subject =  mailOptions.subject  + inMessage + " <EOM>"
  
  mailTo(mailOptions, (err)=> {});
}

var sendTestReport = function fnSendtestReport(AllTestPassed, resultRows){
  if (AllTestPassed){
    mailOptions.subject =  mailOptions.subject  + "Test Execution Completed Succesfully"
  } else {
    mailOptions.subject =  mailOptions.subject  + "Test Execution Completed with Issues"
  }

  var text  = "Hi Team, \n\n Test Status as below. \n\n\n " ;

  for (i in resultRows){
    text = text + resultRows[i] + "\n"
  }

  mailOptions.text =  text + mailOptions.text ;

  
  mailTo(mailOptions, (err)=> {});
}

// module.exports.mailTo = mailTo ;
module.exports.sendMessageInSubject = sendMessageInSubject ;
module.exports.sendTestReport = sendTestReport ;