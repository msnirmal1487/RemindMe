#!/usr/bin/env sh
cd /Users/subni001/Nirmal/android/RemindMe
rm -rf spoonRunner/spoon-output/*
device_to_connect="10.1.73.80:5555"
device_connected_message="connected to"
/Users/subni001/Library/Android/sdk/platform-tools/adb disconnect
devices=`/Users/subni001/Library/Android/sdk/platform-tools/adb connect $device_to_connect`
#devices=`/Users/subni001/Library/Android/sdk/platform-tools/adb devices`
if [[ $devices == *$device_connected_message* && $devices == *$device_to_connect* ]]; then
    echo "Device connected"
    echo $devices
    #./gradlew -version
    ./gradlew clean
    ./gradlew assemble
    ./gradlew assembleAndroidTest
    cd spoonRunner
    java -jar spoon-runner-1.1.1-jar-with-dependencies.jar --apk ../app/build/outputs/apk/debug/app-debug.apk --test-apk ../app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk --sdk /Users/subni001/Library/Android/sdk
else
    echo "Device NOT connected"
    echo $devices
fi