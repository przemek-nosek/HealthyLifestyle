package pl.healthylifestyle.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.CreateAdditionalUserDataRequest;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.dto.UpdateAdditionalUserDataRequest;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapper;
import pl.healthylifestyle.userservice.service.AdditionalUserDataService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class AdditionalUserDataEndpoint {
    private final  AdditionalUserDataService additionalUserDataService;
    private final  AdditionalUserDataMapper additionalUserDataMapper;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin', 'ceo') or #uuid == #jwt.name")
    ResponseEntity<AdditionalUserDataResponse> getAdditionalUserData(
            @PathVariable String uuid,
            JwtAuthenticationToken jwt
    ) {
        return ResponseEntity.ok(additionalUserDataService.getAdditionalUserData(uuid));
    }

    @PostMapping
    @PreAuthorize("#request.uuid == #jwt.name")
    ResponseEntity<AdditionalUserDataResponse> createAdditionalUserData(
            @Valid @RequestBody CreateAdditionalUserDataRequest request,
            JwtAuthenticationToken jwt
    ) {
        AdditionalUserDataDto additionalUserDataDto = additionalUserDataMapper.toAdditionalUserDataDto(request);
        return ResponseEntity.ok(additionalUserDataService.createAdditionalUserData(additionalUserDataDto));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("#uuid == #jwt.name")
    ResponseEntity<AdditionalUserDataResponse> updateAdditionalUserData(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateAdditionalUserDataRequest request,
            JwtAuthenticationToken jwt
    ) {
        AdditionalUserDataDto additionalUserDataDto = additionalUserDataMapper.toAdditionalUserDataDto(request, uuid);
        return ResponseEntity.ok(additionalUserDataService.updateAdditionalUserData(additionalUserDataDto));
    }
}
