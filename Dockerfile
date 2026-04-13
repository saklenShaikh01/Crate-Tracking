FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean install -Dmaven.test.skip=true

EXPOSE 8084

CMD ["java", "-jar", "khataBook/target/khataBook-0.0.1-SNAPSHOT.jar"]
