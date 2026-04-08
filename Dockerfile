FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean install -Dmaven.test.skip=true

EXPOSE 8082

CMD ["java", "-jar", "crate-management/target/crate-management-0.0.1-SNAPSHOT.jar"]
