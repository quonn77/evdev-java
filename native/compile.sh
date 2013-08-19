#!/bin/sh

$CC -Wall -shared -fPIC -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -o libevdev-java.so evdev-java.c

