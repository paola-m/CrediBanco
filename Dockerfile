# Usa una imagen base oficial de OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR en el contenedor
COPY target/crediBancoCard-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto que utiliza la aplicación (ajusta si es diferente)
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/app.jar"]
