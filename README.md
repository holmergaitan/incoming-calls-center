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
4. En la llamada se almacena la respuesta del empleado que respondió y el nombre del hilo de ejecución.
---

## Datos adicionales

En la ruta com/callcenter/app/model, hay un diagrama de la solución.
Los test se encuentran en la ruta src/test/java

---

## Consulta y envío de nuevas llamadas

El sistema de llamadas incia con 12 empleados disponibles.

1. Para enviar una llamada, enviar una peticion POST a: **{host}:9999/calls/**
```
	{
		"name": "Marge Simpson",
		"message": "You should not eat that thing",
		"state": "PENDING",
		"duration": 5
	}
```
2. Para consultar todas las llamadas, enviar petición GET a: **{host}:9999/calls/allcalls**
```
	[
		{
			"id": 1,
			"name": "Marge Simpson",
			"message": "You should not eat that, Homer!",
			"response": "Answered by Moe Szyslak[com.callcenter.app.model.employee.Operator@7e3dbf6c]. Executed by thread: pool-2-thread-1 ",
			"state": "ANSWERED",
			"duration": 6,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:06.002+0000"
		},
		{
			"id": 2,
			"name": "Homer Simpson",
			"message": "DoH!",
			"response": "Answered by Moe Szyslak[com.callcenter.app.model.employee.Operator@7e3dbf6c]. Executed by thread: pool-3-thread-1 ",
			"state": "ANSWERED",
			"duration": 8,
			"attempts": 1,
			"answerDate": "2018-04-22T21:30:08.003+0000"
		},
		{
			"id": 3,
			"name": "Bart Simpson",
			"message": "Eat my pants!",
			"response": "Answered by Seymour Skinner[com.callcenter.app.model.employee.Supervisor@b997375]. Executed by thread: pool-2-thread-8 ",
			"state": "ANSWERED",
			"duration": 8,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:13.019+0000"
		},
		{
			"id": 4,
			"name": "Maggie Simpson",
			"message": "Hi!",
			"response": "Answered by Apu Nahasapeemapetilon[com.callcenter.app.model.employee.Operator@51ce9af5]. Executed by thread: pool-2-thread-7 ",
			"state": "ANSWERED",
			"duration": 9,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:14.019+0000"
		},
		{
			"id": 5,
			"name": "Lisa Simpson",
			"message": "If anyone wants me, i will be in my room.",
			"response": "Answered by Edna Krabappel[com.callcenter.app.model.employee.Executive@4b994964]. Executed by thread: pool-2-thread-9 ",
			"state": "ANSWERED",
			"duration": 9,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:09.006+0000"
		},
		{
			"id": 6,
			"name": "Bob Patino",
			"message": "I WILL KILL YOU, BART!",
			"response": "Answered by Ralph Wiggum[com.callcenter.app.model.employee.Executive@2b931943]. Executed by thread: pool-2-thread-10 ",
			"state": "ANSWERED",
			"duration": 10,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:10.006+0000"
		},
		{
			"id": 7,
			"name": "Ned Flanders",
			"message": "Hi dilly ho dilly!",
			"response": "Answered by Seymour Skinner[com.callcenter.app.model.employee.Supervisor@b997375]. Executed by thread: pool-2-thread-8 ",
			"state": "ANSWERED",
			"duration": 5,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:05.004+0000"
		},
		{
			"id": 8,
			"name": "Rachel Duncan",
			"message": "My name is Rachel Duncan, and we are going to come to terms.",
			"response": "Answered by Barney Gumble[com.callcenter.app.model.employee.Supervisor@702ee3a5]. Executed by thread: pool-2-thread-7 ",
			"state": "ANSWERED",
			"duration": 5,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:05.004+0000"
		},
		{
			"id": 9,
			"name": "Sarah Manning",
			"message": "She looks exactly like me!",
			"response": "Answered by Abraham Simpson[com.callcenter.app.model.employee.Supervisor@57abe228]. Executed by thread: pool-2-thread-5 ",
			"state": "ANSWERED",
			"duration": 9,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:09.005+0000"
		},
		{
			"id": 10,
			"name": "Cosima Neiehaus",
			"message": "Everything we are... belongs to them",
			"response": "Answered by Milhouse Van Houten[com.callcenter.app.model.employee.Supervisor@535d437a]. Executed by thread: pool-2-thread-6 ",
			"state": "ANSWERED",
			"duration": 8,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:08.004+0000"
		},
		{
			"id": 11,
			"name": "Allison Hendrix",
			"message": "You really have no idea, do you?!",
			"response": "Answered by Nelson Muntz[com.callcenter.app.model.employee.Operator@94c8cc9]. Executed by thread: pool-2-thread-4 ",
			"state": "ANSWERED",
			"duration": 9,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:09.005+0000"
		},
		{
			"id": 12,
			"name": "Krystal Goderich",
			"message": "Do i look stupid?",
			"response": "Answered by Clancy Wiggum[com.callcenter.app.model.employee.Operator@5faf8459]. Executed by thread: pool-2-thread-3 ",
			"state": "ANSWERED",
			"duration": 7,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:07.003+0000"
		},
		{
			"id": 13,
			"name": "Helena",
			"message": "Hello sestra...",
			"response": "Answered by Apu Nahasapeemapetilon[com.callcenter.app.model.employee.Operator@51ce9af5]. Executed by thread: pool-2-thread-2 ",
			"state": "ANSWERED",
			"duration": 5,
			"attempts": 1,
			"answerDate": "2018-04-22T21:29:05.003+0000"
		}
	]
```
3. Para consultar llamadas pendientes, enviar petición GET a: **{host}:9999/calls/pendingcalls**
```
	[
		{
			"id": 1,
			"name": "Marge Simpson",
			"message": "You should not eat that, Homer!",
			"response": null,
			"state": "PENDING",
			"duration": 8,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 2,
			"name": "Homer Simpson",
			"message": "DoH!",
			"response": null,
			"state": "PENDING",
			"duration": 5,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 3,
			"name": "Bart Simpson",
			"message": "Eat my pants!",
			"response": null,
			"state": "PENDING",
			"duration": 6,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 4,
			"name": "Maggie Simpson",
			"message": "Hi!",
			"response": null,
			"state": "PENDING",
			"duration": 8,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 5,
			"name": "Lisa Simpson",
			"message": "If anyone wants me, i will be in my room.",
			"response": null,
			"state": "PENDING",
			"duration": 6,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 6,
			"name": "Sideshow Bob",
			"message": "I WILL KILL YOU, BART!",
			"response": null,
			"state": "PENDING",
			"duration": 8,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 7,
			"name": "Julius Hibbert",
			"message": "Hi dilly ho dilly!",
			"response": null,
			"state": "PENDING",
			"duration": 9,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 8,
			"name": "Patty Bouvier",
			"message": "I hate Homer",
			"response": null,
			"state": "PENDING",
			"duration": 7,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 9,
			"name": "Selma Bouvier",
			"message": "Homer is a dirty dirty dog!",
			"response": null,
			"state": "PENDING",
			"duration": 10,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 10,
			"name": "Kent Brockman",
			"message": "You are viewing channel six.",
			"response": null,
			"state": "PENDING",
			"duration": 5,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 11,
			"name": "Willie",
			"message": "Damn scots! They ruined Scotland",
			"response": null,
			"state": "PENDING",
			"duration": 9,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 12,
			"name": "Maude Flanders",
			"message": "Lets talk about jesus.",
			"response": null,
			"state": "PENDING",
			"duration": 9,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 13,
			"name": "Philip J Fry",
			"message": "I came from 20th century.",
			"response": null,
			"state": "PENDING",
			"duration": 7,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 14,
			"name": "Turanga Leela",
			"message": "Im a ciclope.",
			"response": null,
			"state": "PENDING",
			"duration": 6,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 15,
			"name": "Bender",
			"message": "Bite my shinny a**",
			"response": null,
			"state": "PENDING",
			"duration": 9,
			"attempts": 0,
			"answerDate": null
		},
		{
			"id": 16,
			"name": "Bonder",
			"message": "I will kill you, Bender.",
			"response": null,
			"state": "PENDING",
			"duration": 6,
			"attempts": 0,
			"answerDate": null
		}
	]
```
4. Para generar multiples llamadas, enviar petición GET a: **{host}:9999/calls/sendcalls**
```
[
    {
        "name": "Rachel Duncan",
        "message": "My name is Rachel Duncan, and we are going to come to terms.",
        "state": "PENDING",
        "duration": 5
    },
    {
        "name": "Sarah Manning",
        "message": "She looks exactly like me!",
        "state": "PENDING",
        "duration": 9
    },
    {
        "name": "Cosima Neiehaus",
        "message": "Everything we are... belongs to them",
        "state": "PENDING",
        "duration": 8
    },
    {
        "name": "Allison Hendrix",
        "message": "You really have no idea, do you?!",
        "state": "PENDING",
        "duration": 9
    },
    {
        "name": "Krystal Goderich",
        "message": "Do i look stupid?",
        "state": "PENDING",
        "duration": 7
    },
    {
        "name": "Helena",
        "message": "Hello sestra...",
        "state": "PENDING",
        "duration": 5
    }
]
```
