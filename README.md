# Getting Started

### General Considerations ###

* It's a Java Spring Boot application
* The api uses MY-SQL database for production and embbeded in-memory database H2
* The database model is auto generated when the application up and run with flyway database migrator
* For tests purposes there two files in tests/resources: schema.sql and data.sql, to create and populate the database for some unit tests.

### Set Up and Running With Docker ###

* Execute the script run.bat or run.sh

or in the root project folder:

* Execute docker build -t mysql-api-db ./mysql
* Execute docker build -t startwarsapi . 
* docker-compose up

### Set Up and Running Manually ###

* Create a new schema in MY-SQL named **starwars-api-db**

* Change the application properties to point to the correct database URI

* Set the property spring.flyway.enabled to true

* This a maven based application. To compile, test and run de application we can use maven 'mvn' commands.

* To Compile (without test) : Run the follow command line
```
mvn clean install -DskipTests
```

* To Compile with test : Run the follow command line
```
mvn clean install
```

* To run just automated tests: Run the follow command line
```
mvn test
```

* To run application with spring-boot maven plugin:  Run the follow command line
```
mvn spring-boot:run
```

* To run application as a packaged application:  Run the follow command line
```
java -jar ${target}/starwars-api-0.0.1-SNAPSHOT.jar
```

### Using The API ###
* We can use the swagger-ui interface to test API: http://localhost:8080/starwars/api/swagger-ui.html

### Resources and Methods ###

