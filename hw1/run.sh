#!/bin/bash

export LANG=ru_RU.UTF-8

# config variables
SRC_DIR="src/main"
OUT_DIR="out"
ARTIFACT_DIR="artifacts"
JAR_NAME="hw1.jar"
PACKAGE_PREFIX="mfti.java.hw1"
MAIN_CLASS="$PACKAGE_PREFIX.Main"

# utility function
error_exit() {
    echo -e "\n\e[31m[Error] Execution finished on stage: $1\e[0m"
    exit 1
}

# shell commands for build and run jar file
echo "[1/3] Remove old builds..."
rm -rf "$OUT_DIR" "$ARTIFACT_DIR"
mkdir -p "$OUT_DIR" "$ARTIFACT_DIR"

echo "[2/3] Compile code..."
find "$SRC_DIR" -name "*.java" > sources.txt
javac -encoding UTF-8 -d "$OUT_DIR" @sources.txt || error_exit "Compile code"
rm sources.txt

echo "[3/3] Build jar file..."
jar cfe "$ARTIFACT_DIR/$JAR_NAME" "$MAIN_CLASS" -C "$OUT_DIR" . || error_exit "Build jar file"

echo -e "\n\e[32mBuild finish. Jar filename: $ARTIFACT_DIR/$JAR_NAME\e[0m"
echo -e "\n\e[32mRun jar file: $ARTIFACT_DIR/$JAR_NAME\e[0m"
java -jar $ARTIFACT_DIR/$JAR_NAME