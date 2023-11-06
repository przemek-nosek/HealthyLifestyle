package pl.healthylifestyle.measurement.service;

import org.springframework.stereotype.Service;
import pl.healthylifestyle.measurement.dto.MeasurementDto;
import pl.healthylifestyle.measurement.dto.MeasurementLastModificationResponse;
import pl.healthylifestyle.measurement.dto.MeasurementResponse;
import pl.healthylifestyle.measurement.entity.Measurement;
import pl.healthylifestyle.measurement.exception.MeasurementAlreadyExistsException;
import pl.healthylifestyle.measurement.exception.MeasurementNotFoundException;
import pl.healthylifestyle.measurement.mapper.MeasurementMapper;
import pl.healthylifestyle.measurement.repository.MeasurementRepository;

import java.util.List;

import static pl.healthylifestyle.measurement.exception.message.AdditionalUserDataExceptionMessage.MEASUREMENT_ALREADY_EXISTS_ERROR;
import static pl.healthylifestyle.measurement.exception.message.AdditionalUserDataExceptionMessage.MEASUREMENT_NOT_FOUND_ERROR;

@Service
public record MeasurementService(
        MeasurementRepository measurementRepository,
        MeasurementMapper measurementMapper
) {

    public List<MeasurementResponse> getMeasurements(String userUuid) {
        return measurementRepository.findAllByUserUuid(userUuid)
                .stream()
                .map(measurementMapper::toMeasurementResponse)
                .toList();
    }

    public MeasurementResponse getMeasurement(String uuid) {
        return measurementRepository.findByUuid(uuid)
                .map(measurementMapper::toMeasurementResponse)
                .orElseThrow(() -> handleMeasurementNotFound(uuid));
    }

    public MeasurementLastModificationResponse getLastMeasurementDate(String userUuid) {
        return measurementRepository.findTopByUserUuidOrderByMeasurementDateDesc(userUuid)
                .map(measurement -> MeasurementLastModificationResponse.builder()
                        .lastModification(measurement.getMeasurementDate())
                        .build())
                .orElse(MeasurementLastModificationResponse.empty());

    }

    public MeasurementResponse createMeasurement(MeasurementDto measurementDto) {
        measurementRepository.findByUuid(measurementDto.uuid())
                .ifPresent(this::handleMeasurementExist);
        Measurement measurement = measurementRepository.save(measurementMapper.toMeasurement(measurementDto));
        return measurementMapper.toMeasurementResponse(measurement);
    }

    public MeasurementResponse updateMeasurement(MeasurementDto measurementDto) {
        return measurementRepository.findByUuid(measurementDto.uuid())
                .map(measurement -> measurementRepository.save(
                        measurementMapper.updateMeasurement(measurementDto, measurement)))
                .map(measurementMapper::toMeasurementResponse)
                .orElseThrow(() -> handleMeasurementNotFound(measurementDto.uuid()));
    }

    private MeasurementNotFoundException handleMeasurementNotFound(String uuid) {
        return new MeasurementNotFoundException(
                String.format(MEASUREMENT_NOT_FOUND_ERROR.getMessage(), uuid), MEASUREMENT_NOT_FOUND_ERROR.name());
    }

    private void handleMeasurementExist(Measurement measurement) {
        throw new MeasurementAlreadyExistsException(
                String.format(MEASUREMENT_ALREADY_EXISTS_ERROR.getMessage(), measurement.getUuid()), MEASUREMENT_ALREADY_EXISTS_ERROR.name());
    }
}
