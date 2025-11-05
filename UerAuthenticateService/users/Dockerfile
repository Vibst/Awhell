FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY UerAuthenticateService/users/target/*.jar app.jar
EXPOSE 9095
ENTRYPOINT ["java","-jar","app.jar"]
