FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean install -DskipTests

EXPOSE 8083

CMD ["sh", "-c", "java -jar crate-management/target/*.jar"]
