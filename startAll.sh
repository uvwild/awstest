#!/bin/sh
# system startscript
pwd
(cd configserver;./mvnw spring-boot:run) &
(cd eureka;./mvnw spring-boot:run) &
(cd dataservice;./mvnw spring-boot:run) &
#(cd gateway;./mvnw spring-boot:run) &
#(cd datasource;./mvnw spring-boot:run) &
