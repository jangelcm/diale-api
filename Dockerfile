# Usa la imagen oficial de Java 17
FROM eclipse-temurin:17-jdk

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia todo tu proyecto al contenedor
COPY . /app

# Da permisos de ejecución al wrapper
RUN chmod +x mvnw

# Compila el proyecto
RUN mvn clean install

# Expone el puerto (el mismo que usa tu app, usualmente 8080)
EXPOSE 8080

# Comando para iniciar la aplicación
CMD ["java", "-jar", "target/app-0.0.1-SNAPSHOT.jar"]
