package pl.healthylifestyle.userservice.service;

import org.springframework.stereotype.Service;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;
import pl.healthylifestyle.userservice.exception.UserDataAlreadyExistsException;
import pl.healthylifestyle.userservice.exception.UserDataNotFoundException;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapper;
import pl.healthylifestyle.userservice.repository.AdditionalUserDataRepository;

import static pl.healthylifestyle.userservice.exception.message.AdditionalUserDataExceptionMessage.USER_DATA_ALREADY_EXISTS_ERROR;
import static pl.healthylifestyle.userservice.exception.message.AdditionalUserDataExceptionMessage.USER_DATA_NOT_FOUND_ERROR;

@Service
public record AdditionalUserDataService(
        AdditionalUserDataRepository additionalUserDataRepository,
        AdditionalUserDataMapper additionalUserDataMapper) {

    public AdditionalUserDataResponse getAdditionalUserData(String uuid) {
        return additionalUserDataRepository.findByUuid(uuid)
                .map(additionalUserDataMapper::toAdditionalUserDataResponse)
                .orElseThrow(() -> handleUserNotFound(uuid));
    }

    public AdditionalUserDataResponse createAdditionalUserData(AdditionalUserDataDto additionalUserDataDto) {
        additionalUserDataRepository.findByUuid(additionalUserDataDto.uuid())
                .ifPresent(this::handleUserExists);
        AdditionalUserData savedEntity = additionalUserDataRepository.save(additionalUserDataMapper.toEntity(additionalUserDataDto));
        return additionalUserDataMapper.toAdditionalUserDataResponse(savedEntity);
    }

    public AdditionalUserDataResponse updateAdditionalUserData(AdditionalUserDataDto additionalUserDataDto) {
        return additionalUserDataRepository.findByUuid(additionalUserDataDto.uuid())
                .map(user -> additionalUserDataMapper.updateAdditionalUserData(additionalUserDataDto, user))
                .map(additionalUserDataMapper::toAdditionalUserDataResponse)
                .orElseThrow(() -> handleUserNotFound(additionalUserDataDto.uuid()));
    }

    private UserDataNotFoundException handleUserNotFound(String uuid) {
        return new UserDataNotFoundException(
                String.format(USER_DATA_NOT_FOUND_ERROR.getMessage(), uuid), USER_DATA_NOT_FOUND_ERROR.name());
    }

    private void handleUserExists(AdditionalUserData user) {
        throw new UserDataAlreadyExistsException(
                String.format(USER_DATA_ALREADY_EXISTS_ERROR.getMessage(), user.getUuid()), USER_DATA_ALREADY_EXISTS_ERROR.name());
    }
}
