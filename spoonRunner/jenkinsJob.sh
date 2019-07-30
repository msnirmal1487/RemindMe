#!/usr/bin/env sh
ls -l $WORKSPACE/spoon-output/
rm -rf $WORKSPACE/spoon-output/*
ls -l $WORKSPACE/spoon-output/
cd ~/Nirmal/android/RemindMe/spoonRunner
pwd
./stepsToRunSpoonRunner.sh
cp -R ~/Nirmal/android/RemindMe/spoonRunner/spoon-output/* $WORKSPACE/spoon-output/