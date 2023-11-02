FROM khipu/openjdk17-alpine

EXPOSE 8081
ADD builds/first-0.0.2-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]