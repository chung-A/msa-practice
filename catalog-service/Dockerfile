FROM openjdk:11-jdk

COPY ./ /catalog-service
WORKDIR /catalog-service

ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#RUN ["./gradlew", "bootJar","-x","test"]
#ENTRYPOINT ["java", "-jar", "./build/libs/ecomerce-0.0.1-SNAPSHOT.jar"]

