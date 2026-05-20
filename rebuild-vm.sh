#!/bin/bash
set -e
cp ../COMPILER/target/COMPILER-1.0-SNAPSHOT.jar libs/pcode-vm-1.0-snapshot.jar
rm -f libs/pcode-vm-1.0-obfuscated.jar
./gradlew obfuscateJar
jar tf libs/pcode-vm-1.0-obfuscated.jar | grep javiergs/vm
./gradlew clean runIde