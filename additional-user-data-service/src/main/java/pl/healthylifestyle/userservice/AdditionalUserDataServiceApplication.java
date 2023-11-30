package pl.healthylifestyle.userservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo: config server/bootstrap properties
//todo: observability https://www.youtube.com/watch?v=PT2yZTBnUwQ
//todo: observability https://www.youtube.com/watch?v=mPPhcU7oWDU
//todo: zipkin
//todo: measurement properties/move measurement to separate microservices
//todo: measurement add validation for draft & ability to modification
//todo: postman collection
//todo: rename food to produkt spozywczy
//todo: food - size - unit(slice, ML, kg...), start making sql init with foods
@SpringBootApplication
public class AdditionalUserDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdditionalUserDataServiceApplication.class, args);
    }
}