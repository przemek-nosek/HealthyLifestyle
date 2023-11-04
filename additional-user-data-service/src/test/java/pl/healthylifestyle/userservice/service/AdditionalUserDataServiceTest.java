package pl.healthylifestyle.userservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;
import pl.healthylifestyle.userservice.exception.UserDataAlreadyExistsException;
import pl.healthylifestyle.userservice.exception.UserDataNotFoundException;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapperImpl;
import pl.healthylifestyle.userservice.repository.AdditionalUserDataRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static pl.healthylifestyle.userservice.entity.Allergen.GLUTEN;
import static pl.healthylifestyle.userservice.entity.Allergen.LACTOSE;
import static pl.healthylifestyle.userservice.entity.Gender.MALE;


@ExtendWith(MockitoExtension.class)
class AdditionalUserDataServiceTest {

    private static final String UUID = "uuid";
    private static final String PHONE_NUMBER = "123321321";

    @Mock
    private AdditionalUserDataRepository additionalUserDataRepository;
    @Spy
    private AdditionalUserDataMapperImpl additionalUserDataMapper;
    @InjectMocks
    private AdditionalUserDataService additionalUserDataService;

    @Test
    void getAdditionalUserData_shouldGetUserData_whenExists() {
        //given
        given(additionalUserDataRepository.findByUuid(anyString())).willReturn(Optional.of(buildAdditionalUserData()));

        //when
        AdditionalUserDataResponse actual = additionalUserDataService.getAdditionalUserData(UUID);

        //then
        then(additionalUserDataRepository).should().findByUuid(anyString());
        then(additionalUserDataMapper).should().toAdditionalUserDataResponse(any(AdditionalUserData.class));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildExpectedResponse());

    }

    @Test
    void getAdditionalUserData_shouldThrowUserDataNotFoundException_whenNotFound() {
        //given
        given(additionalUserDataRepository.findByUuid(anyString())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> additionalUserDataService.getAdditionalUserData(UUID))
                .hasMessage("User additional data uuid not found")
                .isInstanceOf(UserDataNotFoundException.class);
        then(additionalUserDataRepository).should().findByUuid(anyString());
        then(additionalUserDataMapper).should(never()).toAdditionalUserDataResponse(any(AdditionalUserData.class));
    }

    @Test
    void createAdditionalUserData_shouldBeSuccessful_whenDoesNotExist() {
        //given
        given(additionalUserDataRepository.findByUuid(anyString())).willReturn(Optional.empty());
        given(additionalUserDataRepository.save(any(AdditionalUserData.class))).willReturn(buildAdditionalUserData());

        //when
        AdditionalUserDataResponse actual = additionalUserDataService.createAdditionalUserData(buildAdditionalUserDataDto());

        //then
        then(additionalUserDataRepository).should().findByUuid(anyString());
        then(additionalUserDataRepository).should().save(any(AdditionalUserData.class));
        then(additionalUserDataMapper).should().toEntity(any(AdditionalUserDataDto.class));
        then(additionalUserDataMapper).should().toAdditionalUserDataResponse(any(AdditionalUserData.class));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildExpectedResponse());

    }

    @Test
    void createAdditionalUserData_shouldThrowUserDataAlreadyExistsException_whenDoesNotExist() {
        //given
        given(additionalUserDataRepository.findByUuid(anyString())).willReturn(Optional.of(buildAdditionalUserData()));

        //when
        //then
        assertThatThrownBy(() -> additionalUserDataService.createAdditionalUserData(buildAdditionalUserDataDto()))
                .hasMessage("User additional data uuid already exists")
                .isInstanceOf(UserDataAlreadyExistsException.class);
        then(additionalUserDataRepository).should().findByUuid(anyString());
        then(additionalUserDataRepository).should(never()).save(any(AdditionalUserData.class));
        then(additionalUserDataMapper).should(never()).toEntity(any(AdditionalUserDataDto.class));
        then(additionalUserDataMapper).should(never()).toAdditionalUserDataResponse(any(AdditionalUserData.class));
    }

    @Test
    void updateAdditionalUserData_shouldBeSuccessful_whenExists() {
        //given
        given(additionalUserDataRepository.findByUuid(anyString())).willReturn(Optional.of(buildAdditionalUserData()));
        given(additionalUserDataRepository.save(any(AdditionalUserData.class))).willReturn(buildAdditionalUserData());

        //when
        AdditionalUserDataResponse actual = additionalUserDataService.updateAdditionalUserData(buildAdditionalUserDataDto());

        //then
        then(additionalUserDataRepository).should().findByUuid(anyString());
        then(additionalUserDataRepository).should().save(any(AdditionalUserData.class));
        then(additionalUserDataMapper).should().updateAdditionalUserData(any(AdditionalUserDataDto.class), any(AdditionalUserData.class));
        then(additionalUserDataMapper).should().toAdditionalUserDataResponse(any(AdditionalUserData.class));
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(buildExpectedResponse());
    }

    @Test
    void createAdditionalUserData_shouldThrowUserDataNotFoundException_whenDoesNotExist() {
        //given
        given(additionalUserDataRepository.findByUuid(anyString())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> additionalUserDataService.updateAdditionalUserData(buildAdditionalUserDataDto()))
                .hasMessage("User additional data uuid not found")
                .isInstanceOf(UserDataNotFoundException.class);
        then(additionalUserDataRepository).should().findByUuid(anyString());
        then(additionalUserDataRepository).should(never()).save(any(AdditionalUserData.class));
        then(additionalUserDataMapper).should(never()).updateAdditionalUserData(any(AdditionalUserDataDto.class), any(AdditionalUserData.class));
        then(additionalUserDataMapper).should(never()).toAdditionalUserDataResponse(any(AdditionalUserData.class));
    }

    private AdditionalUserData buildAdditionalUserData() {
        return AdditionalUserData.builder()
                .id(1L)
                .uuid(UUID)
                .age(15)
                .phoneNumber(PHONE_NUMBER)
                .gender(MALE)
                .allergens(new HashSet<>(Set.of(LACTOSE, GLUTEN)))
                .build();
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
}