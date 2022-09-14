FROM openjdk:11-jdk

COPY ./ /gateway-app
WORKDIR /gateway-app

ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#RUN ["./gradlew", "bootJar","-x","test"]
#ENTRYPOINT ["java", "-jar", "./build/libs/gateway-0.0.1-SNAPSHOT.jar"]

