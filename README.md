# Receipes ABN
### Used tecnologies
* Java 8
* OpenAPI3.0
* SpringBoot
* Mockito
* JUnit
* H2 Database
### Reference Documentation
For further reference, please consider the following sections about what was used on developemnt of this API:

* [Openapi3.0](https://swagger.io/specification/) - for documentation of the API
* [open-api-generator](https://www.baeldung.com/java-openapi-generator-server) - Used to generate Models and API contracts
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.2/maven-plugin/reference/html/)
* [H2-DB](https://www.h2database.com/html/main.html) - in memory persistence database

### Data initialization
* The application have an in memory database(H2) and when started, will persist 3 objects on it.
That information can be found on class DBDataInitializer.

### API Contract
* The api contract was developed based on OAS3 specifications.
* The contract of the API endpoints can be found on path resources/openapi/apiContract.yaml
You can copy the apiContract.yaml. After, go to swagger editor page, and pass it on: https://editor.swagger.io/.
Navigate inside the endpoints to check the examples about how to make your request.
* Postman collections (*ABN.postman_collection.json*) to make the requests, are available at root, on postman directory. 





