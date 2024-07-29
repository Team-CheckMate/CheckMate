package org.checkmate.admin.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookCreateResponseDto {
    private final boolean success;
    private final String message;
}
