FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY PostService/postservice/target/*.jar app.jar
EXPOSE 9098
ENTRYPOINT ["java","-jar","app.jar"]
