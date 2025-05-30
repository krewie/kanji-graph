FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]
