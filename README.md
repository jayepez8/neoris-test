# neoris-test

Technical/practical test for admission to neoris

## Requirements

This project was developed with:

- Java 11
- Gradle 6.8.2
- SpringBoot
- Mysql
- Docker

## Build and deploy

To build the microservices you must use the command for each microservice:

    .\gradlew clean build

It is necessary to have the database running to successfully pass the integration tests, this can be done using docker and the command:

    docker-compose up -d

If you do not want to perform these tests, run:

    .\gradlew clean build -x test

To build and deploy run these commands in a terminal. It is necessary to have docker running

    docker-compose build
    docker-compose up -d

It is not necessary to run the script in the database because it is already configured to run when the database is raised

## NOTE

Postman files are attached to perform API testing
