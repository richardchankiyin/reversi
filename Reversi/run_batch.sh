#!/bin/bash
args="$1"
echo "args--$args"
mvn exec:java -Dexec.mainClass="com.richard.OthelloBatchGame" -Dexec.args="'$args'"
