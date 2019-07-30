#!/usr/bin/env sh
pwd
ls -l spoon-output/
rm -rf spoon-output/*
ls -l spoon-output/
cd /Users/subni001/Nirmal/android/RemindMe/spoonRunner
pwd
./stepsToRunSpoonRunner.sh
cp -R /Users/subni001/Nirmal/android/RemindMe/spoonRunner/spoon-output/* /Users/Shared/Jenkins/Home/workspace/RemindMe-SpoonRunner-Automation/spoon-output/