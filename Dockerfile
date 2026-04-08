FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean install -Dmaven.test.skip=true

EXPOSE 8081

CMD ["java", "-jar", "inventory-service/target/inventory-service-0.0.1-SNAPSHOT.jar"]
