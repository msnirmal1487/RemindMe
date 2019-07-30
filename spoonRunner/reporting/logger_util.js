var log4js = require('log4js');
log4js.configure({
  appenders: [
    { type: 'console' },
    { type: 'file',
      filename: 'logs/app.log',
      //category: 'KioskMonitoring',
      maxLogSize : 1000000,
      backups: 10 }
  ]
});

//var logger = log4js.getLogger('KioskMonitoring');

exports.logger = {

		getLogger: function(category) {

			var theLogger = log4js.getLogger(category);

			//theLogger.setLevel(level);

			return theLogger;
		}
  };

//logger.setLevel('ERROR');
/*
logger.trace('Entering cheese testing');
logger.debug('Got cheese.');
logger.info('Cheese is Gouda.');
logger.warn('Cheese is quite smelly.');
logger.error('Cheese is too ripe!');
logger.fatal('Cheese was breeding ground for listeria.');
*/
