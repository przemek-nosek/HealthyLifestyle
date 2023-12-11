package pl.healthylifestyle.userservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo: config server/bootstrap properties
//todo: observability https://www.youtube.com/watch?v=PT2yZTBnUwQ
//todo: zipkin
//todo: measurement add validation for draft & ability to modification
@SpringBootApplication
public class AdditionalUserDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdditionalUserDataServiceApplication.class, args);
    }
}