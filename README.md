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

Acceso con ADMIN (Usuario: admin  / Password:  1234)  
Acceso con USER (Usuario: user  /  Password:  1234)



![Cruce de Ganado: Pantalla de Login](https://ejerciciosmesa.com/images/cruce-ganado/login.png)


### Listado de animales

Listado con los distintos animales dados de alta. Posibilidad de modificación y eliminación.

![Cruce de Ganado: Listado de animales](https://ejerciciosmesa.com/images/cruce-ganado/listado_animal.png)


### Alta de animales

Formulario para el alta de animales. Incluye validación y búsqueda rápida de la ganadería a la que pertenece el animal. Es posible añadir una foto del animal.


![Cruce de Ganado: Formulario de alta de animales](https://ejerciciosmesa.com/images/cruce-ganado/alta_animal.png)


### Ascendencia

Pantalla con la ascendencia del animal elegido. Permite visualizar a los padres, abuelos y bisabuelos del animal.
Si algún animal en la ascendencia no estuviera en la base de datos, entonces se muestra un icono genérico y tres interrogantes (???)
Es posible hacer clic en esta pantalla sobre la foto de un animal para ver la ascendencia de dicho animal.


![Cruce de Ganado: Ascendencia animal](https://ejerciciosmesa.com/images/cruce-ganado/ascendencia.png)


A través del botón Descargar PDF es posible obtener la información de la pantalla anterior en formato PDF, visualizándose directamente en el navegador.

![Cruce de Ganado: Ascendencia animal en PDF](https://ejerciciosmesa.com/images/cruce-ganado/ascendencia_pdf.png)

### Listado de Ganaderías

Listado con las ganaderías almacenadas en la base de datos.

![Cruce de Ganado: Listado de animales](https://ejerciciosmesa.com/images/cruce-ganado/listado_ganaderias.png)

### Alta de ganaderías

Formulario de alta. Permite añadir el logotipo correspondiente al hierro de la ganadería.

![Cruce de Ganado: Formulario de alta de animales](https://ejerciciosmesa.com/images/cruce-ganado/alta_ganaderias.png)

### Configuración Base de Datos

## Configuración Hibernate H2

El proyecto está preparado inicialmente para trabajar con persistencia en memoria con Hibernate. La base de datos se carga con una serie
de datos iniciales de prueba a través de un archivo import.sql dentro de la carpeta resources.

La configuración usada para la base de datos de Hibernate en el archivo application.properties es:

```
#Configuración para h2
spring.datasource.url=jdbc:h2:mem:ganado
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
```

Ya que el proyecto usa String Security, se permite el acceso a la consola de Hibernate a través del siguiente código en la 
clase de configuración SecurityConfig:

```
http.csrf().disable();
http.headers().frameOptions().disable();
```

Es necesario igualmente permitir el acceso a la url de la consola, añadiendo "/h2-console/**" a la línea:

```
.antMatchers("/css/**","/js/**","/images/**","/h2-console/**").permitAll()
```

Este código debería ser anulado antes de pasar a producción, y solo debe ser usado durante la fase de desarrollo con Hibernate.

## Configuración MySQL

Se incluye también la configuración para MySQL. Para ello, se añade (comentadas) en el application.properties las siguientes líneas
de configuración:

```
#Configuracion para mysql
spring.datasource.url=jdbc:mysql://localhost/cruce_ganado
spring.datasource.username=root
spring.datasource.password=sasa
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
spring.jpa.hibernate.ddl-auto=create
```

Estas líneas tendrán que ser des-comentadas para su uso, al mismo tiempo que se comentan las líneas de configuración de Hibernate.  

La configuración de MySQL supone la existencia de una base de datos llamada cruce_ganado en localhost, con acceso de usuario "root" y
password "sasa".  

La línea spring.jpa.hibernate.ddl-auto está activada al valor 'create', lo que creará automáticamente las tablas de la aplicación en la 
base de datos al ejecutar la aplicación, para luego ser alimentadas con los datos de prueba provenientes del archivo import.sql.  

Las imágenes de los distintos registros vienen ya almacenadas en la carpeta upload dentro de la carpeta raíz de la aplicación.


## Desarrollador

Programado por José Manuel Rosado.  

Más aplicaciones, recursos formativos, ejercicios y otros contenidos en [ejerciciosmesa.com](https://ejerciciosmesa.com)


