#!/usr/bin/env sh
cd /Users/subni001/Nirmal/android/RemindMe
/Users/subni001/Library/Android/sdk/platform-tools/adb disconnect
/Users/subni001/Library/Android/sdk/platform-tools/adb connect 10.1.73.80:5555
#./gradlew -version
./gradlew clean
./gradlew assemble
./gradlew assembleAndroidTest
cd spoonRunner
java -jar spoon-runner-1.1.1-jar-with-dependencies.jar --apk ../app/build/outputs/apk/debug/app-debug.apk --test-apk ../app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk --sdk /Users/subni001/Library/Android/sdk