FROM maven:3.9.12-amazoncorretto-25-debian AS build
WORKDIR /app

COPY pom.xml .
COPY manager-app/pom.xml manager-app/
COPY member-nkso-api/pom.xml member-nkso-api/
RUN mvn dependency:go-offline -B

COPY manager-app/src ./manager-app/src
RUN mvn clean package -DskipTests -pl manager-app -am

FROM amazoncorretto:25-al2023-headless
WORKDIR /app
COPY --from=build /app/manager-app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]