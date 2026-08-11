FROM eclipse-temurin:17-jdk

WORKDIR /workspace

RUN apt-get update && apt-get install -y curl bash maven dos2unix && rm -rf /var/lib/apt/lists/*

COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN dos2unix /usr/local/bin/docker-entrypoint.sh && chmod +x /usr/local/bin/docker-entrypoint.sh

COPY . /workspace

RUN mvn -B -q -DskipTests package

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
