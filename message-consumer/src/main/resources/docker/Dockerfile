FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD dc-vehicle-synchronization.jar app.jar
ENV TZ Asia/Shanghai
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]