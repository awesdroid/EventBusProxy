#!/usr/bin/env bash

./gradlew clean build -PdisableSampleApp
ret=$?
if [ ${ret} -eq 0 ]; then
    echo "Final build finished!"
else
    echo "Final build failed with $ret"
fi