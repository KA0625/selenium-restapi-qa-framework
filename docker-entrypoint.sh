#!/usr/bin/env bash
set -e

# Correct Selenium Grid status endpoint
STATUS_URL="http://selenium-hub:4444/status"

echo "Waiting for Selenium Grid at ${STATUS_URL} ..."

# Wait up to 120s for Grid to be ready
for i in $(seq 1 24); do
  if curl -s "${STATUS_URL}" | grep -q '"ready":true'; then
    echo "Selenium Grid is ready."
    break
  fi
  echo "Grid not ready yet (attempt $i). Sleeping 5s..."
  sleep 5
done

# Select Maven command
if [ -f mvnw ]; then
  MAVEN_CMD="./mvnw"
else
  MAVEN_CMD="mvn"
fi

# Run tests
echo "Running tests with ${MAVEN_CMD} test ${DOCKER_MAVEN_ARGS}"
exec ${MAVEN_CMD} test ${DOCKER_MAVEN_ARGS}
