package pl.healthylifestyle.userservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo: config server/bootstrap properties
//todo: create docker for application
//todo: observability https://www.youtube.com/watch?v=PT2yZTBnUwQ
//todo: observability https://www.youtube.com/watch?v=mPPhcU7oWDU
//todo: zipkin
//todo: measurement properties/move measurement to separate microservices
//todo: measurement add validation for draft & ability to modification
//todo: postman collection
//todo: auditing
@SpringBootApplication
public class AdditionalUserDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdditionalUserDataServiceApplication.class, args);
    }
}