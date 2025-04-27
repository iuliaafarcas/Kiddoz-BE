FROM openjdk:17-jdk-slim

WORKDIR /kiddoz_recommendation

COPY ./build/libs/recommendation-0.0.1-SNAPSHOT.jar /kiddoz_recommendation/myapp.jar

CMD ["java", "-jar", "myapp.jar"]
