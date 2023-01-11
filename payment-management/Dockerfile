FROM adoptopenjdk:11-jre-hotspot
COPY target/lib /usr/src/lib
COPY target/payment-management-1.0.0-runner.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx64m -jar payment-management-1.0.0-runner.jar
