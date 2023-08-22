FROM maven:3.8.4-openjdk-17-slim

# Set the working directory
WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

ENTRYPOINT ["mvn", "spring-boot:run", "-DskipTests=true"]
