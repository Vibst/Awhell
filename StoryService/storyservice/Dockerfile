FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY /StoryService/storyservice/target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]
