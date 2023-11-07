package pl.healthylifestyle.measurement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.healthylifestyle.measurement.dto.CreateMeasurementRequest;
import pl.healthylifestyle.measurement.dto.MeasurementDto;
import pl.healthylifestyle.measurement.dto.MeasurementResponse;
import pl.healthylifestyle.measurement.dto.UpdateMeasurementRequest;
import pl.healthylifestyle.measurement.entity.Measurement;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface MeasurementMapper {

    MeasurementResponse toMeasurementResponse(Measurement source);

    @Mapping(target = "uuid", expression = "java(UUID.randomUUID().toString())")
    MeasurementDto toMeasurementDto(CreateMeasurementRequest source, String userUuid);

    Measurement toMeasurement(MeasurementDto source);

    MeasurementDto toMeasurementDto(UpdateMeasurementRequest request, String uuid, String userUuid);

    Measurement updateMeasurement(MeasurementDto measurementDto, @MappingTarget Measurement measurement);
}
