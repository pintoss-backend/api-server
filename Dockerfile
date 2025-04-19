FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/auth-0.0.1-SNAPSHOT.jar"]

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
