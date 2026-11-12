Sistema de Biblioteca


- Nombre: Fernando Josué Vargas Gonzales
- Número de cuenta: 202530110097
- Asignatura: Programación Orientada a Objetos

Descripción del proyecto

Sistema de gestión de biblioteca desarrollado en Java, utilizando Maven y SQLite.

El sistema permite administrar autores y libros mediante operaciones CRUD (crear, consultar, actualizar y eliminar).

Tecnologías utilizadas

- Java
- Maven
- SQLite
- JDBC
- NetBeans

Estructura del proyecto

- `app`: contiene la clase principal.
- `dao`: contiene las interfaces de acceso a datos y conexión.
- `dao/impl`: contiene las implementaciones de acceso a datos.
- `modelo`: contiene las clases Autor y Libro.
- `servicio`: contiene la lógica del sistema.
- `excepciones`: contiene las excepciones personalizadas.
- `src/main/resources`: contiene el archivo SQL de la base de datos.


El proyecto utiliza SQLite mediante JDBC.

El archivo `biblioteca.sql` contiene las instrucciones SQL para crear las tablas necesarias.


Proyecto disponible en GitHub:
https://github.com/fernandojvargas08-dotcom/Biblioteca
