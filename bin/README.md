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








