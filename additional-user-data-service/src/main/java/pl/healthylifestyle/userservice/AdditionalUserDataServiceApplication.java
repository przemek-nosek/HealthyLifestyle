package pl.healthylifestyle.userservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo: config server
//todo: keycloak with postgres
//todo: create docker for application
//todo: observability https://www.youtube.com/watch?v=PT2yZTBnUwQ
@SpringBootApplication
public class AdditionalUserDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdditionalUserDataServiceApplication.class, args);
    }
}