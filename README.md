# cruce de ganado

Rama principal (main)

- Gestión básica de la entidad Ganadería
- Creación de un CRUD básico usando clase DAO
- No se presta atención a las vistas (funcionales)

Rama v2servicios

- Se mejora al acceso a datos a través de una InterfazDao que hereda CRUDRepository
- Se elimina la clase DAO
- Se crea una clase Servicio y se inyecta en el controlador
- Se añaden validaciones básicas

Rama v3imagenes

- Se añade la posibilidad de subir imágenes a cada ganadería
- Se añade manejo de imágenes (eliminar / modificar)
- Se muestran las imágenes en el listado de ganadería
- Se conecta con una base de datos MySQL
- Se elimina import.sql y se mantiene la persistencia de datos en la base
- Se añaden mensajes de redireccionamiento (flash)

Rama v4ganado

- Se crea la entidad animal
- Se relacionan ganaderías y animales
- Listado de animales
- Creación de búsqueda de ganaderías y animales para la relación
- Solución de problemas con integridad de la base de datos MySQL


Rama v5pag

- Se crea la clase paginadora
- Se modifica el CRUDrepository para que pagine
- Se asigna paginación en los listados de animales y ganaderías
- Se crea el esqueleto del paginador en las vistas de los listados de animales y ganaderías


Rama v6pdf

- Se añade una opción Ver que visualiza las características de cada animal y sus ascendientes
- Se incluye una vista PDF que permite ver el arbol de ascendencia de un animal en un fichero PDF





