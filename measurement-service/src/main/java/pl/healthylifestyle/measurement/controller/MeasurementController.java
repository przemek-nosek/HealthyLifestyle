package pl.healthylifestyle.measurement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.healthylifestyle.measurement.dto.*;
import pl.healthylifestyle.measurement.mapper.MeasurementMapper;
import pl.healthylifestyle.measurement.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementMapper measurementMapper;


    @GetMapping("/users/{userUuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<List<MeasurementResponse>> getMeasurements(
            @PathVariable String userUuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(measurementService.getMeasurements(userUuid));
    }

    @GetMapping("/{uuid}/users/{userUuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementResponse> getMeasurement(
            @PathVariable String uuid,
            @PathVariable String userUuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(measurementService.getMeasurement(uuid));
    }

    @GetMapping("/latest/users/{userUuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementLastModificationResponse> getLastMeasurementDate(
            @PathVariable String userUuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(measurementService.getLastMeasurementDate(userUuid));
    }

    @PostMapping("/users/{userUuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementResponse> createMeasurement(
            @PathVariable String userUuid,
            @RequestBody CreateMeasurementRequest request,
            JwtAuthenticationToken jwt) {
        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(request, userUuid);
        return new ResponseEntity<>(measurementService.createMeasurement(measurementDto), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/users/{userUuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementResponse> updateMeasurement(
            @PathVariable String uuid,
            @PathVariable String userUuid,
            @RequestBody UpdateMeasurementRequest request,
            JwtAuthenticationToken jwt) {
        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(request, uuid, userUuid);
        return ResponseEntity.ok(measurementService.updateMeasurement(measurementDto));
    }
}
