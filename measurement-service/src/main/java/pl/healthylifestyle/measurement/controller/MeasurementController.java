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
@RequestMapping("/measurements/users/{userUuid}")
@RequiredArgsConstructor
class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementMapper measurementMapper;

    @GetMapping("/measurements")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<List<MeasurementResponse>> getMeasurements(
            @PathVariable String userUuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(measurementService.getMeasurements(userUuid));
    }

    @GetMapping("/measurements/{uuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementResponse> getMeasurement(
            @PathVariable String userUuid,
            @PathVariable String uuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(measurementService.getMeasurement(uuid));
    }

    @GetMapping("/measurements/latest")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementLastModificationResponse> getLastMeasurementDate(
            @PathVariable String userUuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(measurementService.getLastMeasurementDate(userUuid));
    }

    @PostMapping("/measurements")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementResponse> createMeasurement(
            @PathVariable String userUuid,
            @RequestBody CreateMeasurementRequest request,
            JwtAuthenticationToken jwt) {
        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(request);
        return new ResponseEntity<>(measurementService.createMeasurement(measurementDto), HttpStatus.CREATED);
    }

    @PutMapping("/measurements/{uuid}")
    @PreAuthorize("#userUuid == #jwt.name")
    ResponseEntity<MeasurementResponse> updateMeasurement(
            @PathVariable String userUuid,
            @PathVariable String uuid,
            @RequestBody UpdateMeasurementRequest request,
            JwtAuthenticationToken jwt) {
        MeasurementDto measurementDto = measurementMapper.toMeasurementDto(request);
        return ResponseEntity.ok(measurementService.updateMeasurement(measurementDto));
    }
}
