#!/usr/bin/env bash
set -e

STATUS_URL="http://selenium-hub:4444/status"

echo "Waiting for Selenium Grid at ${STATUS_URL} ..."

for i in $(seq 1 24); do
  if curl -fsSL "${STATUS_URL}" | grep -q '"ready": true'; then
    echo "Selenium Grid is ready."
    break
  fi
  echo "Grid not ready yet (attempt $i). Sleeping 5s..."
  sleep 5
done

if [ -f mvnw ]; then
  MAVEN_CMD="./mvnw"
else
  MAVEN_CMD="mvn"
fi

echo "Running tests with command: ${MAVEN_CMD} test ${DOCKER_MAVEN_ARGS}"
exec ${MAVEN_CMD} test ${DOCKER_MAVEN_ARGS}
