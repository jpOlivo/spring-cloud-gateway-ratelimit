# Server-Side Rate Limit on Spring Cloud Gateway

Una simple demo que implementa Rate Limit sobre un inbound-gateway.

El gateway implementado en esta demo esta basado sobre Spring Cloud Gateway y hace uso de las *built-in* GatewayFilter Factories provistas por Spring.

## Components

- [Gateway](./gateway): Punto de entrada a los servicios, encargado de proveer enrutamiento a los servicios y manejar *cross-concerns* como Rate Limit, Authorization, Caching, etc.
- [Account-Service](./account-service-mvc): Servicio Fake que simula una API de Negocio
- [Client](./client): Un ejemplo de cliente que consume Account-Service a traves del Gateway de manera planificada


## Run

`$ docker-compose up -d`

El endpoint `/account/{id}` sobre account-service puede ser [directamente](http://localhost:8091/account/1) o a traves del [gateway](http://localhost:8080/mvc/account/1)


Example on **account-service**:
```
❯ curl -i --location --request GET 'http://localhost:8091/account/1'
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 02 Sep 2020 19:10:58 GMT

{"id":1,"number":"1234567890"}
```

Example through **gateway**:
```
❯ curl -i --location --request GET 'http://localhost:8080/mvc/account/1'
HTTP/1.1 200 OK
transfer-encoding: chunked
X-RateLimit-Remaining: 9
X-RateLimit-Requested-Tokens: 1
X-RateLimit-Burst-Capacity: 10
X-RateLimit-Replenish-Rate: 5
Content-Type: application/json
Date: Wed, 02 Sep 2020 19:13:27 GMT

{"id":1,"number":"1234567890"}
```

Notar que el HTTP Headers con sufijo `X-RateLimit-Remaining` nos informa acerca de los tokens disponibles para un bucket en particular (Ver [Token-Bucket](https://en.wikipedia.org/wiki/Token_bucket))

## Test (requires Redis running locally)

La clase [RatelimitGwApplicationTests](./gateway/src/test/java/com/demo/ratelimit/gateway/RatelimitGwApplicationTests.java) contiene algunos tests para probar diferentes configuraciones de Rate Limit.

```
$ mvn test
```

Output

```
16:21:02.915 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[9]
16:21:02.941 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[8]
16:21:02.959 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[7]
16:21:02.980 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[6]
16:21:02.996 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[5]
16:21:03.017 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[9]
16:21:03.032 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[8]
16:21:03.050 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[7]
16:21:03.064 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[6]
16:21:03.078 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[5]
16:21:03.092 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[4]
16:21:03.107 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[3]
16:21:03.122 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[2]
16:21:03.136 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[1]
16:21:03.150 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->200 OK, remaining->[0]
16:21:03.159 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->429 TOO_MANY_REQUESTS, remaining->[0]
16:21:03.168 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->429 TOO_MANY_REQUESTS, remaining->[0]
16:21:03.178 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->429 TOO_MANY_REQUESTS, remaining->[0]
16:21:03.187 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->429 TOO_MANY_REQUESTS, remaining->[0]
16:21:03.195 [main] INFO  c.d.r.g.RatelimitGwApplicationTests - Received: status->429 TOO_MANY_REQUESTS, remaining->[0]
...
```

Otra opcion es levantar el [cliente](./client) para ejecutar HTTP requests sobre el gateway

Configuration
```yaml
spring:
  application:
    name: client 
server:
  port : 8088
  
gateway:
  baseurl: http://gateway-ip:8080  

```
Install Maven and Run
```
$ ./mvnw clean install

$ ./mvnw spring-boot:run
```

Output
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.3.RELEASE)

2020-09-02 17:06:13.737  INFO 16974 --- [           main] c.d.r.gateway.client.ClientApplication   : Starting ClientApplication on SSFF-00176 with PID 16974 (/Users/juan.olivera/Dev/nx/arq/spring-cloud-gateway/ratelimit-gateway/client/target/classes started by juan.olivera in /Users/juan.olivera/Dev/nx/arq/spring-cloud-gateway/ratelimit-gateway/client)
2020-09-02 17:06:13.739  INFO 16974 --- [           main] c.d.r.gateway.client.ClientApplication   : No active profile set, falling back to default profiles: default
2020-09-02 17:06:14.284  INFO 16974 --- [           main] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService 'taskScheduler'
2020-09-02 17:06:14.373  INFO 16974 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port(s): 8088
2020-09-02 17:06:14.382  INFO 16974 --- [           main] c.d.r.gateway.client.ClientApplication   : Started ClientApplication in 0.895 seconds (JVM running for 1.155)
Account(id=1, number=1234567890)
Account(id=1, number=1234567890)
Account(id=1, number=1234567890)
Account(id=1, number=1234567890)
...
```





