# CRUCE DE GANADO

## Descripción

Aplicación web para el control de cruce de toros. Permite administrar el listado de animales así como las ganaderías a las que pertenecen. La aplicación permite visualizar de forma gráfica la ascendencia de un animal hasta tres generaciones atrás (bisabuelos). Igualmente permite obtener esta información en PDF.

## Características

- Aplicación Java Web programada con Spring Boot en Eclipse.
- Spring Security con dos tipos de usuarios en memoria: ADMIN y USER.
- Persistencia con MySQL
- Vistas PDF usando OpenPDF
- La aplicación está configurada para ejecutarse en el puerto 9000

## Pantallas

### Login

Dos tipos de usuarios: ADMIN y USER. USER puede acceder a todas las opciones pero no puede dar de alta, modificar o eliminar.


![Cruce de Ganado: Pantalla de Login](https://ejerciciosmesa.com/images/cruce-ganado/login.png)


### Listado de animales

Listado con los distintos animales dados de alta. Posibilidad de modificación y eliminación.

![Cruce de Ganado: Listado de animales](https://ejerciciosmesa.com/images/cruce-ganado/listado_animales.png)


### Alta de animales

Formulario para el alta de animales. Incluye validación y búsqueda rápida de la ganadería a la que pertenece el animal.


![Cruce de Ganado: Formulario de alta de animales](https://ejerciciosmesa.com/images/cruce-ganado/alta_animal.png)


### Ascendencia

Pantalla con la ascendencia del animal elegido. Permite visualizar a los padres, abuelos y bisabuelos del animal.
Si algún animal en la ascendencia no estuviera en la base de datos, entonces se muestra un icono genérico y tres interrogantes (???)
Es posible hacer clic en esta pantalla sobre la foto de un animal para ver la ascendencia de dicho animal.


![Cruce de Ganado: Ascendencia animal](https://ejerciciosmesa.com/images/cruce-ganado/ascendencia.png)


A través del botón 

![Cruce de Ganado: Ascendencia animal en PDF](https://ejerciciosmesa.com/images/cruce-ganado/ascendencia_pdf.png)

Prueba


