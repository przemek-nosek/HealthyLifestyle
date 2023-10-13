package pl.healthylifestyle.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class TestUserEndpoint {

    @GetMapping("/a")
    @PreAuthorize("hasRole('USER')")
    public String a() {
        return "Hello ";
    }

    @GetMapping("/b")
    public String b(@AuthenticationPrincipal(errorOnInvalidType = true) UserDetails userDetails) {

        return "Hello " ;
    }
}
