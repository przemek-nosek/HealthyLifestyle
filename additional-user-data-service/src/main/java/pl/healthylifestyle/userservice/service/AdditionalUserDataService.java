package pl.healthylifestyle.userservice.service;

import org.springframework.stereotype.Service;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;
import pl.healthylifestyle.userservice.exception.UserDataAlreadyExistsException;
import pl.healthylifestyle.userservice.mapper.AdditionalUserDataMapper;
import pl.healthylifestyle.userservice.repository.AdditionalUserDataRepository;

@Service
public record AdditionalUserDataService(
        AdditionalUserDataRepository additionalUserDataRepository,
        AdditionalUserDataMapper additionalUserDataMapper) {
    public AdditionalUserDataResponse createAdditionalUserData(AdditionalUserDataDto additionalUserDataDto) {
        additionalUserDataRepository.findByUuid(additionalUserDataDto.uuid())
                .ifPresent(user -> {
                            throw new UserDataAlreadyExistsException("User additional data " + user.getUuid() + " already exists.", "USER_AHAHHA");
                        });
        AdditionalUserData savedEntity = additionalUserDataRepository.save(additionalUserDataMapper.toEntity(additionalUserDataDto));
        return additionalUserDataMapper.toAdditionalUserDataResponse(savedEntity);
    }
}
