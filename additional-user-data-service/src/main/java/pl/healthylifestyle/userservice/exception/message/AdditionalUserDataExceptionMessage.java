package pl.healthylifestyle.userservice.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AdditionalUserDataExceptionMessage {

    USER_DATA_ALREADY_EXISTS_ERROR("User additional data %s already exists");

    private final String message;
}
