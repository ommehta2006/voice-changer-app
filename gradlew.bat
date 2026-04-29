@rem Gradle wrapper script for Windows
@rem This script runs Gradle using the wrapper JAR

@echo off
set APP_HOME=%~dp0
set CLASSPATH=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

@rem Execute Gradle with wrapper JAR
java -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
