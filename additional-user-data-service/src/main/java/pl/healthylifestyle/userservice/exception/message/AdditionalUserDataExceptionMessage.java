package pl.healthylifestyle.userservice.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AdditionalUserDataExceptionMessage {

    USER_DATA_ALREADY_EXISTS_ERROR("User additional data %s already exists"),
    USER_DATA_NOT_FOUND_ERROR("User additional data %s not found");

    private final String message;
}
