package pl.healthylifestyle.measurement.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.healthylifestyle.measurement.dto.MeasurementResponse;
import pl.healthylifestyle.measurement.entity.Measurement;
import pl.healthylifestyle.measurement.exception.MeasurementNotFoundException;
import pl.healthylifestyle.measurement.mapper.MeasurementMapper;
import pl.healthylifestyle.measurement.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

    private static final String UUID = "measurement_uuid";
    private static final String USER_UUID = "user_uuid";

    @Mock
    private MeasurementRepository measurementRepository;
    @Spy
    private MeasurementMapper measurementMapper;
    @InjectMocks
    private MeasurementService measurementService;


    @Test
    void getMeasurements_shouldGetMeasurementsByUserUuid() {
        //given
        given(measurementRepository.findAllByUserUuid(anyString())).willReturn(buildListOfMeasurements());
        given(measurementMapper.toMeasurementResponse(any(Measurement.class))).willReturn(buildMeasurementResponse());

        //when
        List<MeasurementResponse> actual = measurementService.getMeasurements(USER_UUID);

        //then
        then(measurementRepository).should().findAllByUserUuid(anyString());
        then(measurementMapper).should().toMeasurementResponse(any(Measurement.class));
        assertThat(actual).hasSize(1);
    }

    @Test
    void getMeasurement_shouldGetMeasurement_whenExists() {
        //given
        given(measurementRepository.findByUuid(anyString())).willReturn(Optional.of(buildMeasurement()));
        given(measurementMapper.toMeasurementResponse(any(Measurement.class))).willReturn(buildMeasurementResponse());

        //when
        MeasurementResponse actual = measurementService.getMeasurement(UUID);

        //then
        then(measurementRepository).should().findByUuid(anyString());
        then(measurementMapper).should().toMeasurementResponse(any(Measurement.class));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildMeasurementResponse());
    }

    @Test
    void getMeasurement_shouldThrowMeasurementNotFoundException_whenNotFound() {
        //given
        given(measurementRepository.findByUuid(anyString())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> measurementService.getMeasurement(UUID))
                .hasMessage("Measurement measurement_uuid not found")
                .isInstanceOf(MeasurementNotFoundException.class);
        then(measurementRepository).should().findByUuid(anyString());
        then(measurementMapper).should(never()).toMeasurementResponse(any(Measurement.class));
    }

    private MeasurementResponse buildMeasurementResponse() {
        return MeasurementResponse.builder()
                .uuid(UUID)
                .userUuid(USER_UUID)
                .weight(80)
                .build();
    }

    private List<Measurement> buildListOfMeasurements() {
        return List.of(
                buildMeasurement()
        );
    }

    private Measurement buildMeasurement() {
        return Measurement.builder()
                .uuid(UUID)
                .userUuid(USER_UUID)
                .weight(80)
                .build();
    }
}