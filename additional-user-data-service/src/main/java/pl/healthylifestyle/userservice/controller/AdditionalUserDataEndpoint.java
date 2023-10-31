package pl.healthylifestyle.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataRequest;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapper;
import pl.healthylifestyle.userservice.service.AdditionalUserDataService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class AdditionalUserDataEndpoint {
    private final  AdditionalUserDataService additionalUserDataService;
    private final  AdditionalUserDataMapper additionalUserDataMapper;

    @PostMapping("/{uuid}")
    @PreAuthorize("#uuid == #jwt.name")
    ResponseEntity<AdditionalUserDataResponse> createAdditionalUserData(
            @PathVariable String uuid,
            @Valid @RequestBody AdditionalUserDataRequest request,
            JwtAuthenticationToken jwt) {

        AdditionalUserDataDto additionalUserDataDto = additionalUserDataMapper.toAdditionalUserDataDto(request, uuid);
        return ResponseEntity.ok(additionalUserDataService.createAdditionalUserData(additionalUserDataDto));
    }
}
