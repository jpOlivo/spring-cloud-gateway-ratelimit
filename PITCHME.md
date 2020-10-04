# Rate Limit

Server-Side Implementation on [API Gateway](https://spring.io/projects/spring-cloud-gateway)

---

## Rate Limit Types 

- Request Rate Limit
- Concurrent Request Rate Limit
- Fleet Usage Load Shedder
- Worker Utilization Load Shedder

Note:  
- Concepto Rate Limit(control the maximum incoming traffic)
- Mitigar DDoS (alternativa load balancing)
- Request Rate Limit: N requests per second
- Concurrent Request Rate Limit: N requests concurrent (API CPU-intensive)
- Load Shedder: Categoriza y prioriza requests y va borrando los request en base a alguna policy


---

## Rate Limit Algorithms 

### Leaky Bucket

![leaky-bucket algorithm](https://miro.medium.com/max/1070/1*rPG_LOU8SOrmS6jsgkPTyA.png) 

Note:  
- Balde pinchado (Leaky Bucket)
- Ejemplo NGNIX implementa ambos

---

## Rate Limit Algorithms 

### Token Bucket

![token-bucket algorithm](https://miro.medium.com/max/1400/1*srub224zkrjcOCETkfHHTw.jpeg) 

Note:
- Balde con cospeles para pasar molinete (Token Bucket)
- Ejemplo NGNIX implementa ambos

---

## Rate Limit on Spring Cloud Gateway

- Built-in GatewayFilter Factory for **Request Rate Limit**
- [RequestRateLimiterGatewayFilterFactory](https://www.javadoc.io/static/org.springframework.cloud/spring-cloud-gateway-core/2.2.3.RELEASE/org/springframework/cloud/gateway/filter/factory/RequestRateLimiterGatewayFilterFactory.html) use **RateLimiter** and **KeyResolver** interfaces

---

## Redis Rate Limiter

- Based [work done at Stripe](https://stripe.com/blog/rate-limiters)
- Implements [Token bucket algorithm](https://en.wikipedia.org/wiki/Token_bucket) on Redis

```yml
spring:
  cloud:
    gateway:  
      routes:
      - id: account-service-mvc
        uri: http://localhost:8091
        predicates:
        - Path=/account/**
        filters:
        - name: RequestRateLimiter
          args:
            key-resolver: "#{@simpleKeyResolver}"
            redis-rate-limiter.replenishRate: 10 
            redis-rate-limiter.burstCapacity: 10
            redis-rate-limiter.requestedTokens: 1
```

---

## Principal Name Key Resolver

- Resolves based on [Principal](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-authentication) Spring Security
- Creates Redis Keys based in value returned by [resolve](https://www.javadoc.io/static/org.springframework.cloud/spring-cloud-gateway-core/2.2.3.RELEASE/org/springframework/cloud/gateway/filter/ratelimit/KeyResolver.html#resolve-org.springframework.web.server.ServerWebExchange-) method

```java
public class PrincipalNameKeyResolver implements KeyResolver {
    @Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		return exchange.getPrincipal().map(Principal::getName)
				.switchIfEmpty(Mono.empty());
	}
}    
```
```
redis:6379> KEYS *

"request_rate_limiter.{juan}.timestamp"
"request_rate_limiter.{juan}.tokens"
```

---

<!-- .slide: data-background="https://media.giphy.com/media/3o6MbfihCsqYtqD0xW/giphy.gif" -->
# Demo

Note:
- Levantar Gateway
- Levantar Service API
- Levantar Prometheus
- Levantar Grafana
- Levantar Redis
- Revisar Cliente y Config gateway
- Levantar Client 10 req/s - ver Grafana
- Levantar Client 5 req/s - ver Grafana
- Levantar Client 1 req/s - ver Grafana
- Configurar 1req/3sec - TestUnit
- Configurar rafagas - TestUnit
- Mostrar KeyResolver (levantar Gw + Api)

---

## Much More

* [Rate Limits on Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/#the-requestratelimiter-gatewayfilter-factory)
* [Security basics: DDoS attacks](https://medium.com/internshala-tech/security-basics-ddos-attacks-57856de95821)
* [Token Bucket Algorithm](https://en.wikipedia.org/wiki/Token_bucket)
* [Leaky Bucket Algorithm](https://en.wikipedia.org/wiki/Leaky_bucket)
* [Implementing Rate Limiting with Resilience4j](https://reflectoring.io/rate-limiting-with-resilience4j/)

---

<!-- .slide: style="text-align: left;" -->
# The End 

* [Implementing Request Rate Limit on Spring Cloud Gateway](https://gitlab.naranja.dev/nx/arquitectura/api-gateway/ratelimit-gw)
