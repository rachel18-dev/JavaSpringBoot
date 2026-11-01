# Course project: Product API · Day 1 start

Where the course project begins. Every file of the finished project is already here, in its package,
but the code is still to write: each file has a TODO that names its day, its slide and what to write.
The project compiles as it is (`mvn compile`). Ctrl+Shift+F in VS Code finds every `TODO Day 1`.

## The Day 1 lab: Set up and start the app

Under `src/main/java/com/example/productapi/` unless the path says otherwise:

| File | What to write |
|---|---|
| `ProductApiApplication.java` | @SpringBootApplication, @ConfigurationPropertiesScan, @EnableSpringDataWebSupport and main() |
| `resources/application.yml` | the app's name, the default profile dev, Actuator's health and info, DEBUG logging |
| `aop/TimingAspect.java` | a @Pointcut on ProductService and an @Around advice that times each call |
| `test/…/ProductApiApplicationTests.java` | contextLoads(): passes when the whole app starts |

Done for you: `pom.xml` (every dependency the course needs), the Maven Wrapper, `config/BrowserLauncher.java`
(opens the bonus UI in your browser on Day 4), the Postman suite in `postman/` and, until Day 4, the open `security/SecurityConfig.java`.

## Run it

```
cd course-project-product-day-1-start
mvn spring-boot:run
```

No Maven? Use the wrapper: `.\mvnw.cmd spring-boot:run` (Windows) or `./mvnw spring-boot:run` (Mac, Linux).
Run the tests with `mvn test`. For the Postman suite, import `postman/product-acceptance.postman_collection.json`,
start the app, and choose **Run collection**.

## Done when (the real results of day-1-finish)

- `mvn spring-boot:run`: `Started ProductApiApplication in 3.336 seconds`. Before the lab it stops with
  `Unable to find a suitable main class`.
- `GET /actuator/health`: 200, `"status":"UP"`, with db, diskSpace, livenessState, ping, readinessState and ssl
- `GET /actuator/info`: 200, `"artifact":"product-api"` and Java `21.0.12.1`
- `mvn test`: `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- Postman: **1 of 17** checks pass, the health check. Every other request gets 404: there is no API yet.
