#!/bin/bash

mvn exec:java -Dexec.mainClass="com.richard.OthelloBatchGame" -Dexec.args="$1"
