# Etapa de construcción
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar solo lo necesario (wrapper y proyecto)
COPY inventario/inventario ./

# Ejecutar Maven Wrapper desde el proyecto correcto
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR desde la etapa de build (ajusta si el nombre es fijo)
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]
