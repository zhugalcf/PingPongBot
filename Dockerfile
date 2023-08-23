FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY build/libs/PingPongBot-1.0.jar PingPongBot.jar
ENTRYPOINT ["java","-jar","/PingPongBot.jar"]