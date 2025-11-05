FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY /Messanger/message/target/*.jar app.jar
EXPOSE 9097
ENTRYPOINT ["java","-jar","app.jar"]
