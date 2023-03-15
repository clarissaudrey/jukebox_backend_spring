FROM openjdk:11
LABEL maintainer="clarissaudrey.net"
ADD target/jukebox-get-api-0.0.1.jar jukebox-get-api.jar
ENTRYPOINT ["java", "-jar", "jukebox-get-api.jar"]