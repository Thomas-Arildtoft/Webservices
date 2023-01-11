FROM adoptopenjdk:11-jre-hotspot
COPY target/lib /usr/src/lib
COPY target/server-dtu-pay-1.0.0-runner.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx64m -jar server-dtu-pay-1.0.0-runner.jar
