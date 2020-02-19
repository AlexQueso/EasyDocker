# EasyDocker
### Temática de la web
Se trata de una apliación que ofrece una capa de abstracción en la creación de arquitecturas de microservicios con 
docker. Los **usuarios registrados** podrán crear y guardar arquitecturas.

## Páginas principales
#### Página de Inicio
![alt text](/src/main/resources/static/PNG/paginaDeInicio.PNG "Página de Inicio")
#### Página de Registro/Inicio de sesión
![alt text](/src/main/resources/static/PNG/paginaDeRegistro.PNG "Página de Registro/Inicio de sesión")
#### Página de Usuario
![alt text](/src/main/resources/static/PNG/paginaDeUsuario.PNG "Página de Usuario")
#### Página de Proyecto
![alt text](/src/main/resources/static/PNG/paginaDeProyecto.PNG "Página de Proyecto")
#### Página de Template
![alt text](/src/main/resources/static/PNG/paginaDeTemplate.PNG "Página de Template")
#### Página de Service
![alt text](/src/main/resources/static/PNG/paginaDeService.PNG "Página de Service")
#### Página de Network
![alt text](/src/main/resources/static/PNG/paginaDeNetwork.PNG "Página de Network")
#### Página de Volume
![alt text](/src/main/resources/static/PNG/paginaDeVolume.PNG "Página de Volume")

## Diagrama de navegación
![alt text](src/main/resources/static/PNG/diagramaNavegacion.png "Diagrama de navegación")

## Modelo de datos
#### Entidades principales
- User: usuarios registrados en la aplicación  
- Template: estructura de los dockers y sus interacciones (conexiones, seguridad...)
- Proyect: conjunto de templates de un mismo Team o User
- Service: conjunto de dockers con sus propiedades
- Network: conjunto de networks dentro de un Service con sus propiedades
- Volumes: conjunto de volumes dentro de un Service con sus propiedades
#### Diagrama Entidad/Relación
![alt text](/src/main/resources/static/PNG/diagramaER.png "Diagrama ER")
#### Diagrama de clases UML
![alt text](/src/main/resources/static/PNG/diagramaClasesUML.png "Diagrama clases UML")

## Descripción de las funcionalidades del servicio interno
Se va a encargar de generar y construir los dockerfile y docker-compose mediante las especificaciones del usuario a 
través de la interfaz.

## Integrantes del equipo
Alejandro Quesada Mendo - a.quesada.2016@alumnos.urjc.es - AlexQueso   
David Correas Oliver - d.correas.2016@alumnos.urjc.es - DavidCorreas  
Jose Miguel Zamora Batista - jm.zamora.2016@alumnos.urjc.es - JoseZamora97  