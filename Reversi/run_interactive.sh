#!/bin/bash

mvn exec:java -Dexec.mainClass="com.richard.OthelloInteractiveGame" -Dexec.args="$1"
