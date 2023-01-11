cd Server
mvn package
java -jar ./target/quarkus-app/quarkus-run.jar &
cd ../Test
mvn test
cd ..
kill $(lsof -t -i:8080)
