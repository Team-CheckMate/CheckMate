package org.checkmate.server.dto.response;

import lombok.Getter;

@Getter
public class ChangePwResponseDto {
    private final boolean success;
    private final String message;

    public ChangePwResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
