@echo off
echo 🚀 Starting Spring Boot application...

REM Check for Maven Wrapper
IF EXIST "mvnw.cmd" (
    set MVN_CMD=mvnw.cmd
) ELSE (
    REM Check if system Maven exists
    where mvn >nul 2>nul
    IF %ERRORLEVEL% NEQ 0 (
        exit /b 1
    )
    set MVN_CMD=mvn
)

%MVN_CMD% clean spring-boot:run -Dspring-boot.run.jvmArguments="-Durl.log.path=./logs" %*
