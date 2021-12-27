# Carbon Metric Demo App

This codebase was created to demonstrate a backend application built with Spring boot + InfluxDB including metric collection and alerting.

# How it works

The application uses Spring Boot and InfluxDB.

* Use the idea timeseries DB to store the important metrics
* Use [Finite State Machine](https://en.wikipedia.org/wiki/Finite-state_machine) for tracking the current state of sensors.

And the code is organized as this:

1. `controller` is the web layer implemented by Spring MVC
2. `service` is the business model and logic
3. `repository` is the DB operations
4. `model` is the DB operation related entities
5. `dto` is the high-level objects for data transfer.
6. `configuration`  contains all the necessary properties and general configuration.


# Database

As the application is very write heavy it was decided to use a time series DB. InfluxDB offers a reasonable benchmark scoring. For local testing a `docker-compose` file is included. The properties are currently hard-coded in `docker-compose` file and  `application.properties`.

# Getting started

You'll need Docker installed to get the DB up and running.

	docker-compose up -d

To test that the DB works, open a browser tab at http://localhost:8086/. Username: `username`, Password: `password`

You'll need Java 17 installed to compile and run the application.

    ./mvnw spring-boot:run

# Improvements Possible


* [ ] Integrate Redis