# Products-Task
## Description
Multi-module app to allow users to store and read from a catalog of products through a web UI.

## Overview
![Products_Task drawio](https://github.com/said-mendez/products-task/assets/123189912/60e2b203-a14d-4029-a577-381d6c1abbb7)

## How to install and run?
Following instructions are considering MacOS.
First make sure you have [Docker](https://docs.docker.com/engine/install/) installed.
Install JDK 1.8.
### Install DB
From a terminal run the following:
<br/>
`docker run -d -p 33060:3306 --name products-db  -e MYSQL_ROOT_PASSWORD=password --mount src=product-db-data,dst=/var/lib/mysql mysql`
### Install RabbitMQ
`docker pull rabbitmq:3-management`
<br/>
`docker run -d --hostname products-rabbit --name products-rabbit -p 15673:15672 -p 5673:5672 rabbitmq:3-management`
### Install project
Run `mvn clean install`
## Modules
### Management App: 

&nbsp;&nbsp; a. 2 JSP one to create the products and another one to list them. 

&nbsp;&nbsp; b. Controller to handle the requests.l 

&nbsp;&nbsp; c. Service to send the message through a queue (using 4) to the microservice (3). 

### Common Library:

&nbsp;&nbsp; a. Contains the POJO that will be used to serialize and deserialize between 1 and 3. 

&nbsp;&nbsp; b. Serializer and deserializer if required.	 

### Microservice:  

&nbsp;&nbsp; a. Connects to the DB to save and get the data (stored procedure (SP) required). 

&nbsp;&nbsp; b. Consume the message from queue (using 2) 

&nbsp;&nbsp; c. Send the list of products that will be shown in the view list on 1  to a queue (using 2) 

The queue or topic depending on if is using RabbitMQ or Kafka  
The DB that will save the data

&nbsp;&nbsp; a. The insert and select will be through  a Stored Procedure 
