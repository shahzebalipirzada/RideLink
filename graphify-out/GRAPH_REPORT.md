# Graph Report - ride-link  (2026-08-23)

## Corpus Check
- Corpus is ~6,180 words - fits in a single context window. You may not need a graph.

## Summary
- 430 nodes · 783 edges · 28 communities (25 shown, 3 thin omitted)
- Extraction: 90% EXTRACTED · 10% INFERRED · 0% AMBIGUOUS · INFERRED: 78 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Messaging Controller
- Authentication Service
- API Error Handling
- Security Filter Chain
- User REST API
- Auth REST API
- Security Configuration
- Ride Persistence Models
- Ride REST API
- Application Configuration
- Location Models
- WebSocket Configuration
- Platform and Runtime Config
- Routing Integration
- Maven Wrapper
- Ride Search Service
- Ride Creation Models
- Transaction Configuration
- User Details Service
- OSRM Route Models
- Object Mapper Config
- RestTemplate Config
- Application Tests
- User Roles
- Graphify Guidance
- JWT API Configuration
- Maven Project Metadata
- Debug Configuration

## God Nodes (most connected - your core abstractions)
1. `User` - 31 edges
2. `ApiError` - 24 edges
3. `AuthUtil` - 24 edges
4. `Ride` - 20 edges
5. `UserRepository` - 19 edges
6. `MessageService` - 18 edges
7. `Conversation` - 17 edges
8. `RideService` - 15 edges
9. `GlobalMvcExceptionHandler` - 13 edges
10. `AuthService` - 13 edges

## Surprising Connections (you probably didn't know these)
- `Project JDK 21` --conceptually_related_to--> `Ride Link Spring Application`  [INFERRED]
  qodana.yaml → src/main/resources/application.yaml
- `Qodana Sensitive Information Warning` --conceptually_related_to--> `GitHub OAuth2 Client Registration`  [INFERRED]
  qodana.yaml → src/main/resources/application.yaml
- `Qodana Sensitive Information Warning` --conceptually_related_to--> `Google OAuth2 Client Registration`  [INFERRED]
  qodana.yaml → src/main/resources/application.yaml
- `AuthController` --references--> `AuthService`  [EXTRACTED]
  src/main/java/com/mrshaikhmuhammad/ridelink/controller/AuthController.java → src/main/java/com/mrshaikhmuhammad/ridelink/security/AuthService.java
- `RideController` --references--> `RideService`  [EXTRACTED]
  src/main/java/com/mrshaikhmuhammad/ridelink/controller/RideController.java → src/main/java/com/mrshaikhmuhammad/ridelink/service/RideService.java

## Import Cycles
- None detected.

## Communities (28 total, 3 thin omitted)

### Community 0 - "Messaging Controller"
Cohesion: 0.05
Nodes (43): Controller, java.security.Principal, lombok.Builder, lombok.Data, SimpMessagingTemplate, GetMapping, MessageMapping, RequestMapping (+35 more)

### Community 1 - "Authentication Service"
Cohesion: 0.07
Nodes (31): javax.crypto.SecretKey, org.springframework.http.ResponseCookie, org.springframework.security.authentication.BadCredentialsException, org.springframework.security.core.Authentication, org.springframework.security.oauth2.core.user.OAuth2User, org.springframework.security.web.authentication.AuthenticationSuccessHandler, SignupRequestDto, ResponseCookie (+23 more)

### Community 2 - "API Error Handling"
Cohesion: 0.09
Nodes (26): Authentication, BadCredentialsException, ControllerAdvice, DataAccessException, DisabledException, ExceptionHandler, HttpStatus, JwtException (+18 more)

### Community 3 - "Security Filter Chain"
Cohesion: 0.09
Nodes (24): GrantedAuthority, jakarta.servlet.FilterChain, jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, lombok.extern.slf4j.Slf4j, lombok.RequiredArgsConstructor, org.springframework.security.config.annotation.web.builders.HttpSecurity, org.springframework.security.core.AuthenticationException (+16 more)

### Community 4 - "User REST API"
Cohesion: 0.11
Nodes (12): GetMapping, RequestMapping, RequiredArgsConstructor, ResponseEntity, RestController, UserController, ResponseCookie, LogoutResponseDto (+4 more)

### Community 5 - "Auth REST API"
Cohesion: 0.15
Nodes (10): AuthController, PostMapping, RequestMapping, RequiredArgsConstructor, ResponseEntity, RestController, LoginRequestDto, ObjectId (+2 more)

### Community 6 - "Security Configuration"
Cohesion: 0.23
Nodes (9): org.springframework.context.annotation.Bean, org.springframework.context.annotation.Configuration, org.springframework.security.authentication.AuthenticationManager, org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration, org.springframework.security.crypto.password.PasswordEncoder, org.springframework.web.cors.CorsConfigurationSource, AuthManagerConfig, CorsConfig (+1 more)

### Community 7 - "Ride Persistence Models"
Cohesion: 0.16
Nodes (10): MongoRepository, RideSuggestion, AllArgsConstructor, Data, Document, NoArgsConstructor, ObjectId, Ride (+2 more)

### Community 8 - "Ride REST API"
Cohesion: 0.19
Nodes (8): org.springframework.transaction.annotation.Transactional, PostMapping, RequestMapping, RequiredArgsConstructor, ResponseEntity, RestController, RideController, RideJoinRequestDto

### Community 9 - "Application Configuration"
Cohesion: 0.19
Nodes (8): ConfigurationProperties, org.springframework.boot.autoconfigure.SpringBootApplication, org.springframework.boot.context.properties.EnableConfigurationProperties, Override, Option, OsrmProperties, Route, RideLinkApplication

### Community 10 - "Location Models"
Cohesion: 0.21
Nodes (7): GeoJsonPoint, AllArgsConstructor, Data, NoArgsConstructor, Location, Override, LocationRequestDto

### Community 11 - "WebSocket Configuration"
Cohesion: 0.20
Nodes (8): EnableWebSocketMessageBroker, MessageBrokerRegistry, Configuration, Override, StompBrokerConfig, StompEndpointRegistry, WebSocketMessageBrokerConfigurer, WebSocketTransportRegistration

### Community 12 - "Platform and Runtime Config"
Cohesion: 0.20
Nodes (12): Qodana JVM Linter, Project JDK 21, Qodana Starter Inspection Profile, Qodana Sensitive Information Warning, CORS Allowed Origins, MongoDB Configuration, GitHub OAuth2 Client Registration, Google OAuth2 Client Registration (+4 more)

### Community 13 - "Routing Integration"
Cohesion: 0.24
Nodes (5): Component, RequiredArgsConstructor, RestTemplate, OsrmRouteClient, RideUtil

### Community 14 - "Maven Wrapper"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 15 - "Ride Search Service"
Cohesion: 0.33
Nodes (4): org.springframework.data.mongodb.core.MongoTemplate, RideSearchRequestDto, RideResponseDto, RideService

### Community 16 - "Ride Creation Models"
Cohesion: 0.31
Nodes (6): RideCreateRequestDto, JsonIgnoreProperties, Leg, LocationResponseDto, Route, Waypoint

### Community 17 - "Transaction Configuration"
Cohesion: 0.25
Nodes (6): EnableTransactionManagement, MongoDatabaseFactory, PlatformTransactionManager, Bean, Configuration, TransactionConfig

### Community 18 - "User Details Service"
Cohesion: 0.43
Nodes (5): org.springframework.security.core.userdetails.UserDetails, org.springframework.security.core.userdetails.UserDetailsService, org.springframework.stereotype.Service, Override, UserDetailServiceImpl

### Community 19 - "OSRM Route Models"
Cohesion: 0.57
Nodes (5): JsonIgnoreProperties, Leg, Path, Route, Waypoint

### Community 20 - "Object Mapper Config"
Cohesion: 0.33
Nodes (4): ObjectMapper, Bean, Configuration, ObjectMapperConfig

### Community 21 - "RestTemplate Config"
Cohesion: 0.33
Nodes (4): Bean, Configuration, RestTemplate, RestTemplateConfig

### Community 22 - "Application Tests"
Cohesion: 0.60
Nodes (3): org.junit.jupiter.api.Test, org.springframework.boot.test.context.SpringBootTest, RideCreateRequestDtoLinkApplicationTests

### Community 23 - "User Roles"
Cohesion: 0.50
Nodes (3): Role, DRIVER, PASSENGER

### Community 24 - "Graphify Guidance"
Cohesion: 0.67
Nodes (3): Graphify Incremental Update Requirement, Graphify Query, Path, and Explain Tools, Graphify Workflow

## Knowledge Gaps
- **18 isolated node(s):** `com.mrshaikhmuhammad:ride-link`, `GROUP`, `DIRECT`, `GOOGLE`, `GITHUB` (+13 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `User` connect `Authentication Service` to `Messaging Controller`, `Security Filter Chain`, `User REST API`, `Ride Persistence Models`, `Ride Search Service`?**
  _High betweenness centrality (0.215) - this node is a cross-community bridge._
- **Why does `Ride` connect `Ride Persistence Models` to `Messaging Controller`, `Authentication Service`, `Ride REST API`, `Location Models`, `Routing Integration`, `Ride Search Service`, `Ride Creation Models`, `User Roles`?**
  _High betweenness centrality (0.119) - this node is a cross-community bridge._
- **Why does `AuthUtil` connect `Authentication Service` to `Messaging Controller`, `Security Filter Chain`, `User REST API`, `Ride Search Service`?**
  _High betweenness centrality (0.108) - this node is a cross-community bridge._
- **What connects `com.mrshaikhmuhammad:ride-link`, `GROUP`, `DIRECT` to the rest of the system?**
  _18 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Messaging Controller` be split into smaller, more focused modules?**
  _Cohesion score 0.05010351966873706 - nodes in this community are weakly interconnected._
- **Should `Authentication Service` be split into smaller, more focused modules?**
  _Cohesion score 0.07474747474747474 - nodes in this community are weakly interconnected._
- **Should `API Error Handling` be split into smaller, more focused modules?**
  _Cohesion score 0.08705882352941176 - nodes in this community are weakly interconnected._