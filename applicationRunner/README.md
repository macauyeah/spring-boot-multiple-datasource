# sample test command
mvn spring-boot:run -Dspring-boot.run.arguments="--op=8999 --op=9000"
mvn clean compile package
java -jar target/applicationRunner-0.0.1-SNAPSHOT.jar --op=9001 --op=9002