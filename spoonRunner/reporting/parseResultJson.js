var parseResultsJson = function fnParseResultJson(){

    var readResultJson = require('./uReadResultJson')
    var mailer = require('./uSendMail')

    readResultJson.readObjFile('spoon-output/result.json', (err, results) =>
    { 
        if (err) 
        {
            logger.error(err)
            mailer.sendMessageInSubject("Something went Wrong. Not able to Read result.json");
        } 
        else {
            try {
                var spoonResult = JSON.parse(results)
                var resultToMail = []
                var AllTestPased = true;
                var spoonResultKeys = Object.keys(spoonResult.results)
                for(var k in spoonResultKeys){
                    var deviceName = spoonResultKeys[k]
                    for(var i in spoonResult.results[deviceName].testResults){
                    var androidTestClass = "";
                    var androidTestMethod = "";
                    var androidTestStatus = "";
                    testResult = spoonResult.results[deviceName].testResults[i]
                    for (var j in testResult){
                        // console.log(testResult[j])
                        var testResultRow = testResult[j]
                        var testResultRowKeys=Object.keys(testResultRow)
                        for(var l in testResultRowKeys){
                        // console.log(testResultRowKeys[l])
                        if (testResultRowKeys[l].match("className")){
                            androidTestClass = testResultRow[testResultRowKeys[l]]
                        }
                        if (testResultRowKeys[l].match("methodName")){
                            androidTestMethod = testResultRow[testResultRowKeys[l]]
                        }
                        if (testResultRowKeys[l].match("status")){
                            androidTestStatus = testResultRow[testResultRowKeys[l]]
                            if(!androidTestStatus.match("PASS")){
                                AllTestPased = false ;
                            }
                        }
                        }
                    }
                    var resultLine = deviceName + " \t\t" + androidTestClass + " \t\t" + androidTestMethod + " \t\t" + androidTestStatus

                    // console.log(resultLine)
                    resultToMail.push(resultLine)
                    }
                    mailer.sendTestReport(AllTestPased, resultToMail)
                }
            } catch (error) {
                logger.error(error)
                mailer.sendMessageInSubject("Something went Wrong. Not able to Parse result.json");
            }
        
        }
    });
}
parseResultsJson()