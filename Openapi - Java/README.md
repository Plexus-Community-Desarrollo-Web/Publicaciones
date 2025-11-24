# 📚 Library Management API Demo

¡Bienvenido al tutorial de OpenAPI! Este proyecto es una demostración práctica de cómo implementar una API REST utilizando la metodología **API First** con Spring Boot y OpenAPI 3.

## 🚀 ¿Qué es este proyecto?

Este repositorio muestra cómo diseñar, documentar e implementar una API robusta para la gestión de una biblioteca. El núcleo del proyecto es el contrato de la API (`openapi.yaml`), del cual generamos automáticamente el código base.

### 🌟 Características Principales

*   **API First Design**: Definición completa en OpenAPI 3.1.
*   **Generación de Código**: Interfaces y DTOs generados automáticamente con `openapi-generator-maven-plugin`.
*   **Clean Architecture**: Separación clara entre la capa de API (generada), Controladores, Dominio y Servicios.
*   **Mappers**: Uso de MapStruct para transformar entre DTOs generados y modelos de dominio.
*   **Documentación Viva**: Swagger UI integrado para probar la API interactivamente.
*   **Validación**: Reglas de validación definidas en el contrato y aplicadas automáticamente.

## 🛠️ Stack Tecnológico

*   **Java 21**: Aprovechando las últimas características LTS.
*   **Spring Boot 3.4.7**: Framework base.
*   **OpenAPI Generator**: Para generar interfaces y modelos.
*   **SpringDoc OpenAPI**: Para la integración de Swagger UI.
*   **Lombok**: Para reducir el boilerplate.
*   **MapStruct**: Para el mapeo de objetos.

## 📂 Estructura del Proyecto

```
openapi-demo/
├── src/main/resources/
│   ├── openapi-v1.yaml      # 📝 Contrato de la API (La verdad absoluta)
│   └── application.properties # ⚙️ Configuración
├── src/main/java/org/learning/
│   ├── controller/          # 🎮 Implementación de los endpoints
│   ├── domain/              # 🧠 Modelos de negocio
│   ├── service/             # 💼 Lógica de negocio
│   └── mapper/              # 🔄 Conversores DTO <-> Dominio
└── pom.xml                  # 📦 Dependencias y plugins de generación
```

## 🏁 Cómo Empezar

### Prerrequisitos
*   Java 21 SDK instalado.
*   Maven instalado.

### Ejecución

1.  **Clona el repositorio**:
    ```bash
    git clone <url-del-repo>
    cd openapi-demo
    ```

2.  **Compila y Ejecuta**:
    ```bash
    mvn spring-boot:run
    ```
    *Nota: Durante la compilación (`mvn clean compile`), el plugin de OpenAPI generará las interfaces en `target/generated-sources`.*

3.  **Explora la API**:
    Abre tu navegador y visita:
    *   👉 **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    *   📄 **Spec JSON**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## 💡 Flujo de Trabajo (API First)

1.  **Diseño**: Editamos `src/main/resources/openapi-v1.yaml`.
2.  **Generación**: Ejecutamos `mvn clean compile` para actualizar las interfaces Java.
3.  **Implementación**: Implementamos las interfaces en los controladores (`org.learning.controller`).
4.  **Prueba**: Verificamos en Swagger UI.

## 🤝 Contribuir

Si quieres mejorar este tutorial, ¡haz un fork y envía un PR!

---
*Creado para el equipo de desarrollo con ❤️*
