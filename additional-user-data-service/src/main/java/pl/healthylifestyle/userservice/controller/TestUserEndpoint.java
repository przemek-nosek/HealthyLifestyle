package pl.healthylifestyle.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;


@RestController
@RequestMapping("/users")
class TestUserEndpoint {

    @GetMapping("/a")
    @PreAuthorize("hasRole('user')")
    public Map<String, Object> a(Principal principal) {
        return Map.of("principal", principal);
    }

    @GetMapping("/b")
    public Map<String, Object> b(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getTokenValue());
        Map<String, Object> claims = jwt.getClaims();
        String givenName = (String) claims.get("given_name");
        String familyName = (String) claims.get("family_name");
        String email = (String) claims.get("email");
        String preferredUsername = (String) claims.get("preferred_username");
        return Map.of("principal", jwt);
    }
}
