#!/usr/bin/env bash

# define variables
is_debug=true
ret=0
version=$1

function jarFiles {
    for file in $1/*; do
        jar -xf ${file}
#      jar -xvf ${file##*/}
    done
}

# clean intermediates artifacts
function checkResult {
    ret=$?
    if [ $ret -ne 0 ]; then
        echo "Exited with error $ret"
        cleanAndReturn
        exit 1
    fi
}

# clean intermediates artifacts and back to root dir
function cleanAndReturn {
    cd ..
    rm -rf temp
    cd ..
}

# enter output directory
cd build

# create temp directory to generate jar
if [ -d temp ]; then
	rm -rf temp
fi
mkdir temp
cd temp

# exact classes
if ${is_debug}; then
    jar -xf ../../cm-eventbus/build/intermediates/bundles/debug/classes.jar
    checkResult

    jar -xf ../../cm-eventbus/build/dependencies/eventbus-3.0.0.jar
    checkResult

    jar -xf ../../cm-qisdk/qimessaging.jar
    checkResult

    jar -xf ../../cm-log/build/intermediates/bundles/debug/classes.jar
    checkResult

    jarFiles ../../cm-log/build/dependencies
    checkResult
fi

# generate common.jar
jar -cfM common-${version}.jar .
checkResult

# move new jar to export directory
mv common-${version}.jar ../../export/
checkResult
cleanAndReturn
echo "Export ${PWD}/export/common-${version}.jar "