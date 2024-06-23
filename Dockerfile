FROM openjdk:17
ADD target/*.jar invoicegenerator.jar
ENTRYPOINT ["java","-jar","/invoicegenerator.jar"]
