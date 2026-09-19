#!/bin/bash

set -e
echo "🚀 Starting Spring Boot application..."

if [ -f "./mvnw" ]; then
    MVN_CMD="./mvnw"
else
    if command -v mvn &> /dev/null; then
        MVN_CMD="mvn"
    else exit 1
    fi
fi

$MVN_CMD clean spring-boot:run "$@"
