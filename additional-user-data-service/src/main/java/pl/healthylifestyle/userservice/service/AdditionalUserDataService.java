package pl.healthylifestyle.userservice.service;

import org.springframework.stereotype.Service;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;
import pl.healthylifestyle.userservice.exception.UserDataAlreadyExistsException;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapper;
import pl.healthylifestyle.userservice.repository.AdditionalUserDataRepository;

import static pl.healthylifestyle.userservice.exception.message.AdditionalUserDataExceptionMessage.USER_DATA_ALREADY_EXISTS_ERROR;

@Service
public record AdditionalUserDataService(
        AdditionalUserDataRepository additionalUserDataRepository,
        AdditionalUserDataMapper additionalUserDataMapper) {

    public AdditionalUserDataResponse createAdditionalUserData(AdditionalUserDataDto additionalUserDataDto) {
        additionalUserDataRepository.findByUuid(additionalUserDataDto.uuid())
                .ifPresent(this::handleFailure);
        AdditionalUserData savedEntity = additionalUserDataRepository.save(additionalUserDataMapper.toEntity(additionalUserDataDto));
        return additionalUserDataMapper.toAdditionalUserDataResponse(savedEntity);
    }

    private void handleFailure(AdditionalUserData user) {
        throw new UserDataAlreadyExistsException
                (String.format(USER_DATA_ALREADY_EXISTS_ERROR.getMessage(), user.getUuid()), USER_DATA_ALREADY_EXISTS_ERROR.name());
    }
}
