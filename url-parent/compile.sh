#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "jdk 21 mvn compile"
mvn clean install -DskipTests -U -Dtest.skip=true


