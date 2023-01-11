FROM adoptopenjdk:11-jre-hotspot
COPY target/lib /usr/src/lib
COPY target/client-dtu-pay-1.0.0-runner.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx64m -jar client-dtu-pay-1.0.0-runner.jar
