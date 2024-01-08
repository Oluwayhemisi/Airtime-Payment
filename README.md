# Airtime-Payment

Airtime payment is a platform that integrates JWT-based authentication and also used the Xpress Payment VTU API to purchase airtime.
Upon user login, the platform generates a JWT token, which serves as a secure credential for subsequent airtime transactions


The application uses the following development tools:

* Spring Boot: Used as web server framework [<https://spring.io/projects/spring-boot>]
* Maven: app building and management
* Java: SE 17 or later versions are required for development


**Configure the app:**
Navigate to the **application.properties** file, edit the properties in the file with your credentials

## Start the application

To start the application:

1. Navigate to airtime-payment
2. Open the terminal and run the following command to install dependencies:
   `mvn install`
3. Execute the following command to run the spring-boot server:
   `mvn spring-boot:run`

## Authorization and Airtime Payment Processing

To complete a payment, follow these steps:

1. Register on the application by making a request to the endpoint `/api/v1/auth/register`.
2. Log in using the endpoint `/api/v1/auth/login`.
3. Utilize the endpoint `/api/v1/purchase` for the payment transaction. A token is generated upon login, and it should be included as a valid JWT token in the request header.


## Unit tests