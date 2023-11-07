package pl.healthylifestyle.measurement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.healthylifestyle.measurement.config.SecurityConfig;
import pl.healthylifestyle.measurement.dto.MeasurementResponse;
import pl.healthylifestyle.measurement.mapper.MeasurementMapper;
import pl.healthylifestyle.measurement.service.MeasurementService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeasurementController.class)
@Import(SecurityConfig.class)
class MeasurementControllerTest {

    private static final String BASE_URL = "/measurements";
    private static final String LIST_OF_MEASUREMENTS_URL = BASE_URL + "/users/{userUuid}";
    private static final String GET_SINGLE_MEASUREMENT_URL = BASE_URL + "/{uuid}/users/{userUuid}";
    private static final String UUID = "measurement_uuid";
    private static final String USER_UUID = "user_uuid";
    private static final String DIFFERENT_USER_UUID = "different_user_uuid";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MeasurementService measurementService;
    @MockBean
    private MeasurementMapper measurementMapper;

    @Test
    void getMeasurements_shouldGetListOfMeasurements_whenAuthenticated() throws Exception {
        //given
        given(measurementService.getMeasurements(anyString())).willReturn(buildListOfMeasurementResponse());

        //when
        MvcResult result = mockMvc.perform(get(LIST_OF_MEASUREMENTS_URL, USER_UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, USER_UUID))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        List<MeasurementResponse> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        then(measurementService).should().getMeasurements(anyString());
        assertThat(actual).hasSize(1);
    }

    @Test
    void getMeasurements_shouldNotGetListOfMeasurements_whenNotAuthenticated() throws Exception {
        //given
        given(measurementService.getMeasurements(anyString())).willReturn(buildListOfMeasurementResponse());

        //when
        //then
        mockMvc.perform(get(LIST_OF_MEASUREMENTS_URL, USER_UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DIFFERENT_USER_UUID))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getMeasurement_shouldGetMeasurement_whenAuthenticated() throws Exception {
        //given
        given(measurementService.getMeasurement(anyString())).willReturn(buildMeasurementResponse());

        //when
        MvcResult result = mockMvc.perform(get(GET_SINGLE_MEASUREMENT_URL, UUID, USER_UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, USER_UUID))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        MeasurementResponse actual = objectMapper.readValue(result.getResponse().getContentAsString(), MeasurementResponse.class);

        then(measurementService).should().getMeasurement(anyString());
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildMeasurementResponse());
    }

    @Test
    void getMeasurement_shouldNotGetMeasurement_whenNotAuthenticated() throws Exception {
        //given
        given(measurementService.getMeasurement(anyString())).willReturn(buildMeasurementResponse());

        //when
        //then
        mockMvc.perform(get(GET_SINGLE_MEASUREMENT_URL, UUID, USER_UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DIFFERENT_USER_UUID))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    private List<MeasurementResponse> buildListOfMeasurementResponse() {
        return List.of(
                buildMeasurementResponse()
        );
    }

    private MeasurementResponse buildMeasurementResponse() {
        return MeasurementResponse.builder()
                .uuid(UUID)
                .userUuid(UUID)
                .weight(80)
                .bicepsSize(35)
                .build();
    }
}