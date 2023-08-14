# bci-test

- Para poder ejecutar este proyecto, levantarlo en localhost y realizar las pruebas con postman.
- En la carpeta resources hay dos archivos:
                - data.sql, que es una inserción de un usuario ya registrado(requerido para iniciar sesión en la aplicacion), con el cual será posible ejecutar todos los demás servicios disponibles.
                - api.yml, es la API de este proyecto, lista para ser desplegada en Swagger.editor.

- hice unos pequeños cambios en el requerimiento del proyecto, por ejemplo:
     - El campo last login solo será visible cuando un usuario inicie sesión. Para usuario creado, que no ha hecho login aún, este campo no será visible hasta el momento que inicie sesión efectivamente. Lo mismo para el campo modified, que solo será visible cuando un usuario haya sido modificado.
     -  Las respuestas de los métodos GET son distintas, pero solo para efectos de manipulación de información de distintas maneras, en uno se ve el token y en otro no. 

# Importante
- El token tiene una vigencia de 3 minutos.

