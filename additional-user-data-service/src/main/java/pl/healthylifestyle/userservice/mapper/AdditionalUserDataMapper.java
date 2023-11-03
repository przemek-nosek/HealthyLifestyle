package pl.healthylifestyle.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataDto;
import pl.healthylifestyle.userservice.dto.AdditionalUserDataResponse;
import pl.healthylifestyle.userservice.dto.CreateAdditionalUserDataRequest;
import pl.healthylifestyle.userservice.dto.UpdateAdditionalUserDataRequest;
import pl.healthylifestyle.userservice.entity.AdditionalUserData;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface AdditionalUserDataMapper {

    AdditionalUserDataDto toAdditionalUserDataDto(CreateAdditionalUserDataRequest source);

    AdditionalUserData toEntity(AdditionalUserDataDto source);

    AdditionalUserDataResponse toAdditionalUserDataResponse(AdditionalUserData source);

    AdditionalUserDataDto toAdditionalUserDataDto(UpdateAdditionalUserDataRequest request, String uuid);

    AdditionalUserData updateAdditionalUserData(AdditionalUserDataDto dto, @MappingTarget AdditionalUserData user);
}
