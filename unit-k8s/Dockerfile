FROM maven:3.6.3-jdk-11-slim AS builder

WORKDIR /app

COPY app/pom.xml /app/pom.xml
RUN mvn -e -B dependency:resolve

COPY app/src ./src
RUN mvn -e -B package

FROM openjdk:11-jre-slim-buster
EXPOSE 8000
COPY --from=builder /app/target/app.jar .

CMD ["java", "-jar", "app.jar"]