# Products-Task
## Description
Multi-module app to allow users to store and read from a catalog of products through a web UI.

## Overview
![Products_Task drawio](https://github.com/said-mendez/products-task/assets/123189912/60e2b203-a14d-4029-a577-381d6c1abbb7)

## How to install and run?
Following instructions are considering MacOS.
First make sure you have [Docker](https://docs.docker.com/engine/install/) installed.
### Install DB
From a terminal run the following:
<br/>
`docker run -d -p 33060:3306 --name products-db  -e MYSQL_ROOT_PASSWORD=password --mount src=product-db-data,dst=/var/lib/mysql mysql`
### Install RabbitMQ
`docker pull rabbitmq:3-management`
<br/>
`docker run -d --hostname products-rabbit --name products-rabbit -p 15673:15672 -p 5673:5672 rabbitmq:3-management`

## Requirements
