# Needs to be started with parameter --privileged so docker can run network interfaces
FROM sbom-generators
ARG VERSION=0.0.1

COPY build/libs/generators-1.0-SNAPSHOT.jar /app/generators.jar

CMD java -jar /app/generators.jar
#ENTRYPOINT ["java", "-jar", "/app/generators.jar"]