FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY ApigatewayService/apigateway/target/*.jar app.jar
EXPOSE 9096
ENTRYPOINT ["java","-jar","app.jar"]
