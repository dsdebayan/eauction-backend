git remote add origin https://github.com/dsdebayan/demo.git
git config --global user.email "dsdebayan@gmail.com"
git add *
git commit -m ""
git branch -M main
git push -u origin main

http://localhost:8761/

http://localhost:8000/swagger-ui/index.html
http://localhost:8000/h2-console
http://localhost:8000/actuator

spring-boot:build-image -DskipTests

docker login
docker-compose up
docker container ps
docker image ps
docker container kill pid
docker container prune
docker network create eauction-network
docker container run --detach --env MYSQL_ROOT_PASSWORD=admin --env MYSQL_USER=admin --env MYSQL_PASSWORD=admin --env MYSQL_DATABASE=eauction --name mysql --publish 3306:3306 --network=eauction-network --volume mysql-database-volume:/var/lib/mysql mysql:5.7

apis ->
usecase 1 :
Post - http://localhost:8000/e-auction/api/v1/seller/add-product
{
    "seller" : {
        "firstName" : "Debayan",
        "lastName" : "Saha",
        "address" : "",
        "city" : "",
        "state" : "",
        "pin" : 742101,
        "phone" : 1234567890,
        "email" : "abcde@gmail.com"
    },
    "name" : "Berger paint",
    "shortDesc" : "",
    "detailedDesc" : "",
    "category" : "Painting",
    "startPrice" : "300",
    "bidEndDate" : "2022-12-12"
    }
 
Usecase 2 :
Delete - http://localhost:8000/e-auction/api/v1/seller/delete/2

Usecase 3:
Post - http://localhost:8000/e-auction/api/v1/buyer/place-bid
{
    "buyer" : {
        "firstName" : "Debayan",
        "lastName" : "Saha",
        "address" : "",
        "city" : "",
        "state" : "",
        "pin" : 742101,
        "phone" : 1234567890,
        "email" : "abc@gmail.com"
    },
    "product" : {
	    "id" : 1
    },
    "bidAmount" : "350"
}

Usecase 4:
get - http://localhost:8000/e-auction/api/v1/seller/show-bids/1

Usecase 5:
Put - http://localhost:8000/e-auction/api/v1/buyer/updatebid/1/abcde@gmail.com/500

