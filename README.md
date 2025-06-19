# ⚡ Subasta Online – Arquitectura de Microservicios

> **Versión 0.1 – MVP Dockerizado**  
> Esta es una versión inicial del sistema de subastas. En próximas versiones se implementarán nuevas funcionalidades, mejoras en la seguridad y más lógica de negocio.

## 🚀 Visión general

Subasta Online es una plataforma construida con una arquitectura **modular y de microservicios** usando **Spring Boot**, que permite a los usuarios crear, participar y hacer seguimiento a subastas en tiempo real.  
El proyecto está completamente **contenedorizado con Docker** y cada microservicio corre en su propio contenedor junto a su base de datos correspondiente.  
La comunicación entre servicios se realiza mediante **Apache Kafka**, y la autenticación se gestiona con **Keycloak** usando OAuth 2.0 y Spring Security.

## 🧩 Microservicios

| Microservicio           | Funcionalidad                                                                 | Base de Datos |
|-------------------------|------------------------------------------------------------------------------|---------------|
| subasta-service         | Crear subastas y programar subastas a futuro                                | MySQL         |
| subasta-puja            | Gestión de pujas y suscripciones                                             | MySQL         |
| subasta-historial       | Historial de pujas realizadas                                                | MySQL         |
| subasta-notificacion    | Notificaciones tipo "bandeja de entrada" para los usuarios                   | MySQL         |
| subasta-producto        | CRUD de productos que se subastarán                                          | MongoDB       |
| subasta-usuario         | (En desarrollo) Gestión de usuarios, puntos y reputación                     | (pendiente)   |
| api-gateway             | Enrutamiento centralizado de las peticiones                                  | —             |
| eureka-server           | Descubrimiento de servicios (Service Registry)                               | —             |

### Infraestructura Adicional

- **Kafka + Zookeeper**: Comunicación entre servicios
- **Keycloak**: Autenticación y autorización (OAuth 2.0)
- **Base de datos de Keycloak**: MySQL
- **Bases de datos de microservicios**: MySQL y MongoDB

## 🛠️ Tecnologías usadas

- **Lenguaje**: Java 21
- **Framework**: Spring Boot 3.4, Spring WebFlux
- **Seguridad**: Keycloak 21, OAuth 2.0, Spring Security
- **Mensajería**: Apache Kafka
- **Bases de Datos**: MySQL 8, MongoDB 6
- **Contenedores**: Docker y Docker Compose
- **Descubrimiento de servicios**: Eureka
- **Orquestación de peticiones**: API Gateway (Spring Cloud Gateway)

## 🔐 Seguridad

El sistema utiliza Keycloak como proveedor de identidad (IdP), configurado con:

- Realm: 'subasta-realm`
- Soporte para flujos OAuth2 como Resource Owner Password Credentials
- Roles básicos implementados: `USER`, `ADMIN`
- Spring Security configurado como Resource Server para validar tokens JWT

## 🔄 Comunicación entre servicios

- Los microservicios se comunican mediante eventos de Apache Kafka.
- Por ejemplo: cuando un usuario hace una puja, se emite un evento que escucha el microservicio `subasta-historial` para guardar ese dato, y también `subasta-notificacion` para enviar una alerta al usuario.

## 📦 Docker y Contenedores

Cada microservicio cuenta con:

- Su **Dockerfile** en la raíz del microservicio
- Un docker-compose.yml  global  
- Un docker-compose global para bases de datos, Kafka, Zookeeper y Keycloak

> Total de contenedores: más de 13 incluyendo microservicios, bases de datos, Keycloak, Eureka, Kafka y Zookeeper.

## 🔧 Cómo ejecutar el proyecto

1. Asegúrate de tener [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado.
2. Clona este repositorio:

`bash
git clone https://github.com/LEONISPE/subasta-online.git
cd subasta-online

Levanta los contenedores necesarios (puedes hacerlo por partes o con todos los docker-compose en orden):

bash
Copy
Edit
docker compose -f docker-compose-bases-datos.yml up --build -d
docker compose -f docker-compose-keycloak.yml up --build -d
docker compose -f docker-compose-eureka.yml up --build -d
docker compose -f docker-compose-gateway.yml up --build -d
docker compose -f docker-compose-producto.yml up --build -d
docker compose -f docker-compose-puja.yml up --build -d
docker compose -f docker-compose-historial.yml up --build -d
docker compose -f docker-compose-notificacion.yml up --build -d
docker compose -f docker-compose-service.yml up --build -d
Accede a:

Keycloak: http://localhost:8181 (admin/admin)

Eureka: http://localhost:8761

Gateway/API: http://localhost:8080

📈 Próximas mejoras
Añadir lógica completa al microservicio subasta-usuario

usar web socket 
usar redis para el manejo de cache 

Implementar trazabilidad con Zipkin o Jaeger

Desplegar en Kubernetes (Helm + k3s)

CI/CD con GitHub Actions

📚 Organización del código
El proyecto fue desarrollado como un proyecto modular con un padre (multi-module Maven), lo que permite construir cada microservicio como un módulo independiente pero con dependencias centralizadas.

✅ Conclusión
Este proyecto demuestra el uso de varias tecnologías clave del ecosistema moderno de desarrollo backend:

Arquitectura basada en microservicios

Autenticación y autorización con Keycloak

Comunicación asíncrona con Kafka

Persistencia con MySQL y MongoDB

Contenedores Docker para cada componente

Seguridad, escalabilidad y mantenibilidad

📬 Contacto
Si te pareció interesante, ⭐ este repositorio y no dudes en contactarme por LinkedIn para más información o colaboración.

linkedin : www.linkedin.com/in/leo-moises-nisperuza-amaya-19568434b

