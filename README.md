**incoming calls service**
---

## Configuración

Para realizar la construccion del proyecto, seguir los siguientes pasos:

1. Ejecutar comando $mvn clean
2. Ejecutar comando $mvn install
3. Ejecutar commando $ mvn spring-boot:run para iniciar el proyecto

---

## Solución

1. Cuando llega una llamada y no hay empleados disponibles, esta continua en estado **PENDING** y se le asigna un mensaje indicando que aún se ha respondido. La llamada ingresa de nuevo a la cola de llamadas.
2. Los empleados se organizan en una cola tipo PriorityBlockingQueue, organizados por el tipo de prioridad que se les asigne:
	1. Operador: **HIGH** 
	2. Supervisor: **MEDIUM**
	3. Director: **LOW**
3. La cantidad de ejecución de hilos se controla mediante el uso de la interface ExcecutorService, con un número fijo de hilos (10). Si llega una llamada y no hay hilos disponibles, la instancia del ExecutorService la ejecutará cuando se encuentre disponible algún hilo de ejecución.
4.En la llamada se almacena la respuesta del empleado que respondió y el nombre del hilo de ejecución.
---
