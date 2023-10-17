package pl.healthylifestyle.userservice;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo: config server
//todo: create docker for application
//todo: observability https://www.youtube.com/watch?v=PT2yZTBnUwQ
//todo: observability https://www.youtube.com/watch?v=mPPhcU7oWDU
//todo: spring doc
@SpringBootApplication
@OpenAPIDefinition
public class AdditionalUserDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdditionalUserDataServiceApplication.class, args);
    }
}