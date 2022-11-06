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


Rama v7seguridad  (DESCARTADA!!! ERROR EN EL PROPERTIES)

Rama v7seguridad2

- (Rama de continuación del proyecto)
- Se implementa seguridad al proyecto con spring security
- Se crean en memoria dos usuarios:
	- Un usuario USER
	- Un usuario ADMIN
- La aplicación no tiene acceso público. Es necesario autenticación para usarla.
- El usuario autenticado ADMIN tiene acceso a todas las características de la aplicación.
- El usuario autenticado USER tiene acceso a todas las opciones de la aplicación excepto:
	- Eliminación
	- Efectuar altas y modificaciones
- Se restringe el acceso desde Thymeleaf a las opciones anteriores al USER.
- Se restringe el acceso a los handler correspondientes de las opciones anteriores al USER.
- Se crean pantallas de login, y error de acceso personalizadas, así como un botón de logout.



