#!/usr/bin/env sh
# Lightweight Gradle bootstrapper for this project (wrapper JAR is not required).
set -eu
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
GRADLE_VERSION=8.9
GRADLE_HOME_BASE="${GRADLE_USER_HOME:-$HOME/.gradle}/mess-hisab-gradle"
GRADLE_HOME="$GRADLE_HOME_BASE/gradle-$GRADLE_VERSION"
if [ ! -x "$GRADLE_HOME/bin/gradle" ]; then
  mkdir -p "$GRADLE_HOME_BASE"
  ZIP="$GRADLE_HOME_BASE/gradle-$GRADLE_VERSION-bin.zip"
  echo "Gradle $GRADLE_VERSION download required (first build only)..."
  if command -v curl >/dev/null 2>&1; then
    curl -fL "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o "$ZIP"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$ZIP" "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"
  else
    echo "Install curl or wget, or install Gradle 8.9 manually." >&2
    exit 1
  fi
  if command -v unzip >/dev/null 2>&1; then
    unzip -q -o "$ZIP" -d "$GRADLE_HOME_BASE"
  else
    echo "Install unzip to extract Gradle." >&2
    exit 1
  fi
fi
exec "$GRADLE_HOME/bin/gradle" -p "$APP_HOME" "$@"
