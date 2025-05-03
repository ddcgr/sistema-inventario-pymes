FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR desde la ruta exacta
COPY inventario/inventario/target/inventario-1.0.0.jar app.jar

# Exponemos el puerto usado por Spring Boot
EXPOSE 8080

# Ejecutamos el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
