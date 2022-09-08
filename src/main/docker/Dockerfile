FROM adoptopenjdk/openjdk16
VOLUME /home/storage
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]