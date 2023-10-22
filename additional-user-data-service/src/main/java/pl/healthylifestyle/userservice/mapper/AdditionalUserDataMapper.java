package pl.healthylifestyle.userservice.mapper;

import org.mapstruct.Mapper;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataRequest;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface AdditionalUserDataMapper {

    AdditionalUserDataDto toAdditionalUserDataDto(AdditionalUserDataRequest source, String uuid);

    AdditionalUserData toEntity(AdditionalUserDataDto source);

    AdditionalUserDataResponse toAdditionalUserDataResponse(AdditionalUserData source);
}
