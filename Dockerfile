####################################### Build stage #######################################
FROM maven:3.6.2-jdk-11-slim AS maven-package-stage

# Copy poms
COPY ./pom.xml /build/amazing-root/
COPY ./amazing-parent/pom.xml /build/amazing-root/amazing-parent/
COPY ./amazing-domain/pom.xml /build/amazing-root/amazing-domain/
COPY ./amazing-persistence/pom.xml /build/amazing-root/amazing-persistence/
COPY ./amazing-core/pom.xml /build/amazing-root/amazing-core/
COPY ./amazing-application/pom.xml /build/amazing-root/amazing-application/

WORKDIR /build/amazing-root/amazing-parent/
RUN mvn dependency:go-offline

#Copy sources
COPY ./amazing-domain/src /build/amazing-root/amazing-domain/src/
COPY ./amazing-persistence/src /build/amazing-root/amazing-persistence/src/
COPY ./amazing-core/src /build/amazing-root/amazing-core/src/
COPY ./amazing-application/src /build/amazing-root/amazing-application/src/

# Build project
WORKDIR /build/amazing-root/
RUN mvn clean package -DskipTests


####################################### Run Stage #######################################
FROM adoptopenjdk/openjdk11:jdk-11.0.4_11-slim

ARG JAR_FILE=target/amazing-application-1.0-SNAPSHOT.jar

COPY --from=maven-package-stage /build/amazing-root/amazing-application/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
