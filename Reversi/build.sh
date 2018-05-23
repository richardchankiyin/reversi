#!/bin/bash

jar="target/Reversi-jar-with-dependencies.jar"

mvn clean install test package

if [ ! -f "$jar" ]; then echo "target jar file not exists!!!"; exit -1; else echo "build sucess."; fi
