FROM eclipse-temurin:17-jdk

WORKDIR /workspace

# Install curl, bash, and Maven
RUN apt-get update && apt-get install -y curl bash maven && rm -rf /var/lib/apt/lists/*

# Copy project files
COPY . /workspace

# Build the project (skip tests here; tests run at container start)
RUN mvn -B -q -DskipTests package

# Simple entrypoint script to wait for Grid and run tests
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
