FROM openjdk:8-jdk-alpine
MAINTAINER tombu.dev
COPY target/web_search-0.0.1-SNAPSHOT.jar web_search-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/web_search-0.0.1-SNAPSHOT.jar"]