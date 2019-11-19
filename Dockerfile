####################################### Build stage #######################################
FROM maven:3.6.0-jdk-11-slim AS maven-package-stage

# Copy poms
COPY ../../../pom.xml /build/

#COPY ../../../piseto-web-commons/pom.xml /build/piseto-web-commons/

COPY ../../pom.xml /build/piseto-content/
COPY ../../piseto-content-parent/pom.xml /build/piseto-content/piseto-content-parent/
COPY ../../piseto-image-utils/pom.xml /build/piseto-content/piseto-image-utils/
COPY ../pom.xml /build/piseto-content/piseto-content-management/

# Download dependenies for docker caching
#WORKDIR /build/piseto-web-commons/
#RUN mvn dependency:go-offline

WORKDIR /build/piseto-content/piseto-content-parent/
RUN mvn dependency:go-offline

#Copy sources
#COPY ../../../piseto-web-commons/src /build/piseto-web-commons/src/
COPY ../../piseto-image-utils/src /build/piseto-content/piseto-image-utils/src/
COPY ../src /build/piseto-content/piseto-content-management/src/

# Build project
WORKDIR /build/amazing-application/
RUN mvn clean package -pl gr.cite.piseto:piseto-content-management -am -DskipTests


####################################### Run Stage #######################################
FROM adoptopenjdk/openjdk11:jdk-11.0.4_11-slim

#Arguments
ARG ARG_CONTENT_MANAGEMENT_LOG_PATH

ARG ARG_CONTENT_MANAGEMENT_SERVER_PORT
ARG ARG_CONTENT_MANAGEMENT_SERVLET_PATH

ARG ARG_CONTENT_MANAGEMENT_DB_HOST
ARG ARG_CONTENT_MANAGEMENT_DB_PORT

ARG ARG_CONTENT_MANAGEMENT_FILE_STORAGE_BASE_PATH

# Environment variables
ENV LOG_PATH=${ARG_CONTENT_MANAGEMENT_LOG_PATH}

ENV SERVER_PORT=${ARG_CONTENT_MANAGEMENT_SERVER_PORT}
ENV SERVLET_PATH=${ARG_CONTENT_MANAGEMENT_SERVLET_PATH}

ENV DB_HOST=${ARG_CONTENT_MANAGEMENT_DB_HOST}
ENV DB_PORT=${ARG_CONTENT_MANAGEMENT_DB_PORT}

ENV FILE_STORAGE_BASE_PATH=${ARG_CONTENT_MANAGEMENT_FILE_STORAGE_BASE_PATH}

VOLUME ${FILE_STORAGE_BASE_PATH}
VOLUME ${LOG_PATH}

EXPOSE ${SERVER_PORT}

ARG JAR_FILE=target/content-management.jar

#COPY piseto-content/piseto-content-management/${JAR_FILE} ${JAR_FILE}
COPY --from=maven-package-stage /build/piseto-content/piseto-content-management/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Djava.security.egd=file:/dev/./urandom", "-jar", "target/content-management.jar"]