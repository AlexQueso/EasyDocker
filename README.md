# EasyDocker
### Temática de la web: 
Ofrecer una capa de abstracción en la creación de arquitectura de microservicios con docker.
Los usuarios registrados podrán crear y guardar arquitecturas.

### Nombre y descripción de las entidades principales
- User: usuarios registrados en la aplicación  
- Template: estructura de los dockers y sus interacciones (conexiones, seguridad...)
- Proyect: conjunto de templates de un mismo Team o User
- Service: conjunto de dockers con sus propiedades
- Network: conjunto de networks dentro de un Service con sus propiedades
- Volumes: conjunto de volumes dentro de un Service con sus propiedades

### Descripción de las funcionalidades del servicio interno
Se va a encargar de generar y construir los dockerfile y docker-compose mediante las especificaciones del user a través de la interfaz.

### Integrantes del equipo
Alejandro Quesada Mendo - a.quesada.2016@alumnos.urjc.es - AlexQueso   
David Correas Oliver - d.correas.2016@alumnos.urjc.es - DavidCorreas  
Jose Miguel Zamora Batista - jm.zamora.2016@alumnos.urjc.es - JoseZamora97  
