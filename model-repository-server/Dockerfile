FROM openjdk:17

EXPOSE 8087

ADD ./api/target/api-0.2.0.jar /api-0.2.0.jar

RUN sh -c 'touch /api-0.2.0.jar'

ENTRYPOINT ["java","-jar","/api-0.2.0.jar"]
