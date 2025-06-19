FROM eclipse-temurin:21.0.6_7-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -B clean package -DskipTests -pl Subasta-usuario/Subasta-usuario -am -U

# ------------ runtime ------------
FROM eclipse-temurin:21.0.6_7-jre
WORKDIR /app
# cambia el path a la carpeta real del módulo
COPY --from=build /app/Subasta-usuario/Subasta-usuario/target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","app.jar"]
