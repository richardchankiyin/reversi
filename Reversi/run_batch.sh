#!/bin/bash

args="$1"
echo "args--$args"
jar="target/Reversi-jar-with-dependencies.jar"
java -cp $jar com.richard.OthelloBatchGame "$args"
