FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} auth-0.0.1-SNAPSHOT.jar

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

ENTRYPOINT ["java","-jar","/auth-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]



