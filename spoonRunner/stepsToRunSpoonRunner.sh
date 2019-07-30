#!/usr/bin/env sh
pwd
cd ~/Nirmal/android/RemindMe
pwd
spoonRunner/reporting/sendMessageInSubject.sh "Spoon Runner Starting"
rm -rf spoonRunner/spoon-output/*
device_to_connect="10.1.73.80:5555"
device_connected_message="connected to"
~/Library/Android/sdk/platform-tools/adb disconnect
devices=`~/Library/Android/sdk/platform-tools/adb connect $device_to_connect`
devices=$device_connected_message$device_to_connect
if [[ $devices == *$device_connected_message* && $devices == *$device_to_connect* ]]; then
    echo "Device connected"
    echo $devices
    #./gradlew -version
    ./gradlew clean
    ./gradlew assemble
    ./gradlew assembleAndroidTest
    cd spoonRunner
    java -jar spoon-runner-1.1.1-jar-with-dependencies.jar --apk ../app/build/outputs/apk/debug/app-debug.apk --test-apk ../app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk --sdk ~/Library/Android/sdk
    FILE=spoon-output/result.json
    if test -f "$FILE"; then
        echo "Parsing result.json"
        /usr/local/bin/node ~/Nirmal/android/RemindMe/spoonRunner/reporting/parseResultJson.js
    else
        echo "result.json missing"
        reporting/sendMessageInSubject.sh "Something Went Wrong!! Spoon-runner Test Not successful."
    fi
else
    echo "Device NOT connected"
    echo $devices
    spoonRunner/reporting/sendMessageInSubject.sh "Not able to connect to device. Aborting spoon-Runner"
fi