FROM openjdk:17

ADD ./api/target/api-0.2.0.jar /api-0.2.0.jar

EXPOSE 8087

RUN sh -c 'touch /api-0.2.0.jar'

ENTRYPOINT ["java","-jar","/api-0.2.0.jar"]
