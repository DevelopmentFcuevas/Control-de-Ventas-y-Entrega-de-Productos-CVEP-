# Usa una imagen base de OpenJDK (Java)
FROM openjdk:21-jdk-slim

# Configura un directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo .jar generado a la imagen
COPY target/servicio-ubicaciones-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto en el que tu microservicio corre
EXPOSE 8080

# Comando para ejecutar tu microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]