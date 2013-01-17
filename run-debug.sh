#!/bin/sh

export GRADLE_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"
exec ./run.sh $@

