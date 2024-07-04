package by.egor.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserDto(
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("email")
        String email,
        @JsonProperty("role")
        String role
) {
}
