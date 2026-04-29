#!/bin/sh

# Gradle wrapper script for Unix
# This script runs Gradle using the wrapper JAR

APP_HOME=$(cd "$(dirname "$0")" && pwd)

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Run Gradle with wrapper JAR
exec java -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
