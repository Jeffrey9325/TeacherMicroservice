FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar /TeacherMicroservice.jar
ENTRYPOINT ["java","-jar","TeacherMicroservice.jar"]
 
