#!/bin/bash

docker run --name mysql-hogar360 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=hogar360 -p 3306:3306 -d mysql:8

sleep 30s

docker exec -i mysql-hogar360 mysql -u root -proot hogar360 < /path/to/hogar360_categoriesdb.sql

docker stop mysql-hogar360
