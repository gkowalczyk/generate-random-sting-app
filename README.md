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


###  Run project - Docker 

After download repositories:

```bash
docker-compose up 
```



### Postman Endpoints tests:

- GET /v1/getall   : list of all tasks
![alt text for image](https://github.com/gkowalczyk/generate-random-sting-app/blob/main/src/main/resources/getall.bmp)

- GET /v1/run   : list of task with status "in progress"
  ![alt text for image](https://github.com/gkowalczyk/generate-random-sting-app/blob/main/src/main/resources/getrun.bmp)

- POST /v1/   : add task (without exception while strings are being generated)
  ![alt text for image](https://github.com/gkowalczyk/generate-random-sting-app/blob/main/src/main/resources/post%20without%20exception.bmp)


- POST /v1/   : add task (with exception if the user wants more strings than we can)
  ![alt text for image](https://github.com/gkowalczyk/generate-random-sting-app/blob/main/src/main/resources/post%20with%20exception.bmp)
