var mailTo = function fnMailTo(inMailOptions, cb){

    var nodemailer = require('nodemailer');
    var transporter = nodemailer.createTransport();
  
    if (cb == null){
      cb = inMailOptions ;
      inMailOptions = null ;
    }
  
    var mailOptions = inMailOptions || {
       from: 'KioskSupportMobile@Staples.com',
       to: 'nirmal.subramanian@staples.com',
       subject: 'Test mail',
       html: '<b>hello world!</b>'
    };
  
    transporter.sendMail(mailOptions, (err, info)=>{
      if (err) {
        cb(err);
      }
      else {
        transporter.close(cb(null,"Mail Sent"));
      }
    });
  }

var sendMail = function fnSendMail(inMessage){

  var mailOptions = {
    from: "KioskSupportMobile@Staples.com",
    to: "Nirmal.Subramanian@Staples.com",
    text: "\nRegards,\nKiosk Support",
    subject: inMessage + " <EOM>"
  };
  mailTo(mailOptions, (err)=> {});
}

process.argv.forEach((val, index) => {
//  console.log(`${index}: ${val}`)
  if(index == 2){
    sendMail(val)
  }
})