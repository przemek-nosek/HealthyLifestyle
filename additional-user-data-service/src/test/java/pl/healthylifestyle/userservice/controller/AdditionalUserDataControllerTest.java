package pl.healthylifestyle.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.healthylifestyle.userservice.config.SecurityConfig;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.dto.CreateAdditionalUserDataRequest;
import pl.healthylifestyle.userservice.dto.UpdateAdditionalUserDataRequest;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapper;
import pl.healthylifestyle.userservice.service.AdditionalUserDataService;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.healthylifestyle.userservice.entity.Allergen.*;
import static pl.healthylifestyle.userservice.entity.Gender.MALE;

@WebMvcTest(AdditionalUserDataController.class)
@Import(SecurityConfig.class)
class AdditionalUserDataControllerTest {

    private static final String BASE_URL = "/users";
    private static final String SPECIFIC_USER_UUID_URL = BASE_URL + "/{uuid}";
    private static final String UUID = "uuid";
    private static final String DIFFERENT_UUID = "different_uuid";
    private static final String PHONE_NUMBER = "123321321";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AdditionalUserDataService additionalUserDataService;
    @MockBean
    private AdditionalUserDataMapper additionalUserDataMapper;

    @Test
    void getAdditionalUserData_shouldGetUserData_whenAuthenticated() throws Exception {
        //given
        given(additionalUserDataService.getAdditionalUserData(anyString())).willReturn(buildExpectedResponse());

        //when
        MvcResult result = mockMvc.perform(get(SPECIFIC_USER_UUID_URL, UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, "uuid"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        AdditionalUserDataResponse actual = objectMapper.readValue(result.getResponse().getContentAsString(), AdditionalUserDataResponse.class);

        then(additionalUserDataService).should().getAdditionalUserData(anyString());
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildExpectedResponse());
    }

    @Test
    void getAdditionalUserData_shouldNotGetUserData_whenNotAuthenticated() throws Exception {
        //given
        given(additionalUserDataService.getAdditionalUserData(anyString())).willReturn(buildExpectedResponse());

        //when
        //then
        mockMvc.perform(get(SPECIFIC_USER_UUID_URL, UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DIFFERENT_UUID))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void createAdditionalUserData_shouldReturnCreatedData_whenAuthenticatedAndValidInput() throws Exception {
        //given
        given(additionalUserDataMapper.toAdditionalUserDataDto(any(CreateAdditionalUserDataRequest.class))).willReturn(buildAdditionalUserDataDto());
        given(additionalUserDataService.createAdditionalUserData(any(AdditionalUserDataDto.class))).willReturn(buildExpectedResponse());

        //when
        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(buildCreateAdditionalUserDataRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim(StandardClaimNames.SUB, "uuid"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        AdditionalUserDataResponse actual = objectMapper.readValue(result.getResponse().getContentAsString(), AdditionalUserDataResponse.class);

        then(additionalUserDataMapper).should().toAdditionalUserDataDto(any(CreateAdditionalUserDataRequest.class));
        then(additionalUserDataService).should().createAdditionalUserData(any(AdditionalUserDataDto.class));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildExpectedResponse());
    }

    @Test
    void createAdditionalUserData_shouldNotBeAbleToCreateData_whenNotAuthenticated() throws Exception {
        //given
        given(additionalUserDataService.getAdditionalUserData(anyString())).willReturn(buildExpectedResponse());

        //when
        //then
        mockMvc.perform(post(BASE_URL, UUID)
                        .content(objectMapper.writeValueAsString(buildCreateAdditionalUserDataRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DIFFERENT_UUID))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource
    void createAdditionalUserData_shouldNotBeAbleToCreateData_whenInvalidInput(
            CreateAdditionalUserDataRequest request,
            String expectedErrorMessage) throws Exception {
        //given
        given(additionalUserDataService.getAdditionalUserData(anyString())).willReturn(buildExpectedResponse());

        //when
        //then
        mockMvc.perform(post(BASE_URL, UUID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim(StandardClaimNames.SUB, UUID))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(result -> assertThat((MethodArgumentNotValidException) result.getResolvedException()).hasMessageContaining(expectedErrorMessage));

    }

    @Test
    void updateAdditionalUserData_shouldReturnUpdatedData_whenAuthenticatedAndValidInput() throws Exception {
        //given
        given(additionalUserDataMapper.toAdditionalUserDataDto(any(UpdateAdditionalUserDataRequest.class), anyString())).willReturn(buildAdditionalUserDataDto());
        given(additionalUserDataService.updateAdditionalUserData(any(AdditionalUserDataDto.class))).willReturn(buildExpectedResponse());

        //when
        MvcResult result = mockMvc.perform(put(SPECIFIC_USER_UUID_URL, UUID)
                        .content(objectMapper.writeValueAsString(buildUpdateAdditionalUserDataRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim(StandardClaimNames.SUB, "uuid"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        AdditionalUserDataResponse actual = objectMapper.readValue(result.getResponse().getContentAsString(), AdditionalUserDataResponse.class);

        then(additionalUserDataMapper).should().toAdditionalUserDataDto(any(UpdateAdditionalUserDataRequest.class), anyString());
        then(additionalUserDataService).should().updateAdditionalUserData(any(AdditionalUserDataDto.class));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildExpectedResponse());
    }

    @Test
    void updateAdditionalUserData_shouldNotBeAbleToUpdateData_whenNotAuthenticated() throws Exception {
        //given
        given(additionalUserDataService.getAdditionalUserData(anyString())).willReturn(buildExpectedResponse());

        //when
        //then
        mockMvc.perform(put(SPECIFIC_USER_UUID_URL, UUID)
                        .content(objectMapper.writeValueAsString(buildUpdateAdditionalUserDataRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DIFFERENT_UUID))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource
    void updateAdditionalUserData_shouldNotBeAbleToUpdateData_whenInvalidInput(
            UpdateAdditionalUserDataRequest request,
            String expectedErrorMessage) throws Exception {
        //given
        given(additionalUserDataService.getAdditionalUserData(anyString())).willReturn(buildExpectedResponse());

        //when
        //then
        mockMvc.perform(post(BASE_URL, UUID)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().jwt(jwt -> jwt.claim(StandardClaimNames.SUB, UUID))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(result -> assertThat((MethodArgumentNotValidException) result.getResolvedException()).hasMessageContaining(expectedErrorMessage));

    }

    private static Stream<Arguments> createAdditionalUserData_shouldNotBeAbleToCreateData_whenInvalidInput() {
        return Stream.of(
                Arguments.of(
                        CreateAdditionalUserDataRequest.builder()
                                .uuid(null)
                                .phoneNumber(PHONE_NUMBER)
                                .age(15)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(MALE)
                                .build(),
                        "UUID can't be empty or null."
                ),
                Arguments.of(
                        CreateAdditionalUserDataRequest.builder()
                                .uuid(UUID)
                                .phoneNumber(PHONE_NUMBER)
                                .age(12)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(MALE)
                                .build(),
                        "Age can't be lower than 13."
                ),
                Arguments.of(
                        CreateAdditionalUserDataRequest.builder()
                                .uuid(UUID)
                                .phoneNumber(PHONE_NUMBER)
                                .age(150)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(MALE)
                                .build(),
                        "Age can't be higher than 100."
                ),
                Arguments.of(
                        CreateAdditionalUserDataRequest.builder()
                                .uuid(UUID)
                                .phoneNumber(PHONE_NUMBER)
                                .age(15)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(null)
                                .build(),
                        "Gender can't be null."
                )
        );
    }

    private static Stream<Arguments> updateAdditionalUserData_shouldNotBeAbleToUpdateData_whenInvalidInput() {
        return Stream.of(
                Arguments.of(
                        UpdateAdditionalUserDataRequest.builder()
                                .phoneNumber(PHONE_NUMBER)
                                .age(12)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(MALE)
                                .build(),
                        "Age can't be lower than 13."
                ),
                Arguments.of(
                        UpdateAdditionalUserDataRequest.builder()
                                .phoneNumber(PHONE_NUMBER)
                                .age(150)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(MALE)
                                .build(),
                        "Age can't be higher than 100."
                ),
                Arguments.of(
                        UpdateAdditionalUserDataRequest.builder()
                                .phoneNumber(PHONE_NUMBER)
                                .age(15)
                                .allergens(Set.of(LACTOSE, SESAME))
                                .gender(null)
                                .build(),
                        "Gender can't be null."
                )
        );
    }

    private AdditionalUserDataResponse buildExpectedResponse() {
        return AdditionalUserDataResponse.builder()
                .uuid(UUID)
                .age(15)
                .phoneNumber(PHONE_NUMBER)
                .gender(MALE)
                .allergens(Set.of(LACTOSE, GLUTEN))
                .build();
    }

    private AdditionalUserDataDto buildAdditionalUserDataDto() {
        return AdditionalUserDataDto.builder()
                .uuid(UUID)
                .age(15)
                .phoneNumber(PHONE_NUMBER)
                .gender(MALE)
                .allergens(Set.of(LACTOSE, GLUTEN))
                .build();
    }

    private CreateAdditionalUserDataRequest buildCreateAdditionalUserDataRequest() {
        return CreateAdditionalUserDataRequest.builder()
                .uuid(UUID)
                .phoneNumber(PHONE_NUMBER)
                .age(15)
                .allergens(Set.of(LACTOSE, SESAME))
                .gender(MALE)
                .build();
    }

    private UpdateAdditionalUserDataRequest buildUpdateAdditionalUserDataRequest() {
        return UpdateAdditionalUserDataRequest.builder()
                .phoneNumber(PHONE_NUMBER)
                .age(15)
                .allergens(Set.of(LACTOSE, SESAME))
                .gender(MALE)
                .build();
    }
}