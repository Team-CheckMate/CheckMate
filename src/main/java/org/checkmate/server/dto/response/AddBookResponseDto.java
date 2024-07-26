package org.checkmate.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddBookResponseDto {
    private final boolean success;
    private final String message;
}
