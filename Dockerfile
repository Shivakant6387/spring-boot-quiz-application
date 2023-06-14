FROM openjdk:11
COPY target/SpringBootQuizApp-0.0.1-SNAPSHOT.jar SpringBootQuizApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringBootQuizApp-0.0.1-SNAPSHOT.jar"]