FROM maven:3.9.12-amazoncorretto-25-debian AS build
WORKDIR /app

COPY pom.xml .
COPY manager-app/pom.xml manager-app/
COPY member-nkso-api/pom.xml member-nkso-api/
RUN mvn dependency:go-offline -B

COPY member-nkso-api/src ./member-nkso-api/src
RUN mvn clean package -DskipTests -pl member-nkso-api -am

FROM amazoncorretto:25-al2023-headless
WORKDIR /app
COPY --from=build /app/member-nkso-api/target/*.jar app.jar

RUN mkdir ./data
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]