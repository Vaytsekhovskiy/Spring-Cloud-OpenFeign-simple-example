package by.egor.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SetStatusDto(
        @JsonProperty("token")
        String token,
        @JsonProperty("status")
        String status
) {
}