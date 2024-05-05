## Running the app
Application uses Java 11, Spring Boot 2.7, Maven.

To create executable JAR archive run:
```console
mvn clean package
```

Then run docker containers with the following command:
```console
docker-compose up
```

Alternatively you can specify your database connection configuration in [application.properties](src/main/resources/application.properties).

Then run the executable jar with `java -jar <file_name>` command.

The serer will be accessible at http://localhost:8080.

## Authentication

Creating new users is not implemented. 

After running the app database is initialized with test data containing following users:

| Username | Password | Roles |
| --- | --- | --- |
| admin | admin | ROLE_ADMIN |
| operadmin | operadmin | ROLE_ADMIN, ROLE_OPERATOR |
| oper | oper | ROLE_OPERATOR |
| userone | userone | ROLE_USER |
| usertwo | usertwo | ROLE_USER |

## Working with the app

After running the app you can see existing endpoints and data models for them at http://localhost:8080/swagger-ui/index.html.

Swagger also allows you to send test requests to every endpoint.
