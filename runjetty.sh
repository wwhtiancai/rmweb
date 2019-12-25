#!/bin/sh

export GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=50001"
gradle jettyRun -s -S
