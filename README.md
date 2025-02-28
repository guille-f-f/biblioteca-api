# Biblioteca Gestión API

**Biblioteca Gestión API** es un sistema de gestión de biblioteca que permite manejar libros, autores, editoriales y ejemplares de manera eficiente y organizada. Este proyecto ofrece una API robusta para interactuar con los recursos de una biblioteca, permitiendo a los administradores realizar operaciones como la creación, modificación y eliminación de libros, así como la asignación de autores y editoriales.

Construido con **Java 20** y **Spring Boot 3.4.3**, el sistema está diseñado para ser fácil de integrar con otras plataformas y ofrece una solución flexible para la gestión de una biblioteca. Además, el proyecto está completamente preparado para almacenar los datos en una base de datos **MySQL**.

## Características

- **Gestión de Libros**: CRUD (Crear, Leer, Actualizar, Eliminar) para libros, permitiendo asignar atributos como título, ejemplares disponibles, autor y editorial.
- **Manejo de Autores y Editoriales**: Permite gestionar los autores y las editoriales asociados a cada libro.
- **Validaciones y Excepciones Personalizadas**: Sistema de validación para asegurar que los datos introducidos sean correctos (ej. ISBN, ejemplares), y manejo de excepciones personalizadas para situaciones de error, mejorando la trazabilidad y comprensión de los fallos.
- **Base de Datos**: Conexión con una base de datos **MySQL** para almacenar la información de manera persistente y fiable.
- **Tecnologías Utilizadas**:
    - **Java 20**
    - **Spring Boot 3.4.3**
    - **Spring Data JPA** (para interactuar con la base de datos)
    - **Thymeleaf** (motor de plantillas)
    - **MySQL** (base de datos)

## Requisitos

- **Java 20**
- **MySQL** (o cualquier base de datos compatible con Spring Data JPA)
- **Maven** (para la construcción y gestión de dependencias)

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tuusuario/biblioteca-gestion-api.git

## Accede a la API y empieza a realizar solicitudes para gestionar los libros, autores y editoriales.

## Contribuciones:
¡Las contribuciones son bienvenidas! Si tienes mejoras o correcciones, siéntete libre de hacer un pull request.