FROM khipu/openjdk17-alpine

EXPOSE 8080
ADD target/first-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]