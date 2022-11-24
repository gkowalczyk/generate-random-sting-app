#                              Recruitment task 
https://docs.google.com/document/d/1iEbP3ZS2FiNZeio8IXXh6NQLLnTCLNBGh8u7aYv9xq8/edit#

**Technology Backend:**

- Spring Boot
- Hibernate
- REST API
- JDBC
- JUnit5
- Mockito
- Lombok


###  Run project (IntelliJ IDEA)

After download repository:

```bash
gradle build
```
```bash
java -jar ./build/libs/generate-number-app-0.0.1-SNAPSHOT.jar
```
Run tests:
```bash
gradle test
```


###  Run project - Docker (temporary problem creating container, try to run the project as above)

After download repositories:

```bash
docker-compose up -d
```
```bash
java -jar generate-number-app-0.0.1-SNAPSHOT.jar
```


### Postman Endpoints tests:

- GET /v1/getall   : list of all tasks
![alt text for image](https://github.com/gkowalczyk/generate-random-sting-app/blob/main/src/main/resources/getall.bmp)

- GET /v1/run   : list of task with status "in progress"


- POST /v1/   : add task 


