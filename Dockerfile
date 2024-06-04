# Usa una imagen base oficial de OpenJDK
# Usar una imagen base con JDK 11 y Maven
FROM openjdk:17
VOLUME /tmp
ENV IMG_PATH=/img
EXPOSE 8080
COPY /target/crediBancoCard-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/crediBancoCard-0.0.1-SNAPSHOT.jar"]