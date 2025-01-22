# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim AS build

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY . /app

# Instalar Maven para construir el proyecto (si es necesario)
RUN apt-get update && apt-get install -y maven

# Ejecutar Maven para construir el proyecto y empaquetar el archivo .jar
RUN mvn clean package -DskipTests

# Usar una imagen base más ligera para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Copiar el archivo JAR desde el contenedor de construcción
COPY --from=build /app/target/WebAppAlmacenes-1.0-SNAPSHOT.jar /app/WebAppAlmacenes.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/WebAppAlmacenes.jar"]
