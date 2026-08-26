#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
./gradlew build
echo
echo "BUILD ERFOLGREICH: build/libs/"
