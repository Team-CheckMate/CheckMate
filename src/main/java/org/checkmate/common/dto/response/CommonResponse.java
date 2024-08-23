package org.checkmate.common.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 공통 응답 DTO
 */
@Getter
@ToString
public class CommonResponse<T> {

    private final Boolean status;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Builder
    public CommonResponse(Boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 성공 응답 메서드(1)
     * @param <T> 타입
     * @param message 성공 응답 메세지
     * @param data 성공 데이터
     * @return status: true, message: "", data: responseDto
     */
    public static <T> CommonResponse<T> success(
            final String message,
            final T data
    ) {
        return CommonResponse.<T>builder()
                .status(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 성공 응답 메서드(2)
     * @param <T> 타입
     * @param message 성공 응답 메세지
     * @return status: true, message: ""
     */
    public static <T> CommonResponse<T> success(
            final String message
    ) {
        return CommonResponse.<T>builder()
                .status(true)
                .message(message)
                .build();
    }

    /**
     * 실패 응답 메서드
     * @param <T> 타입
     * @param message 실패 응답 메세지
     * @return status: false, message: ""
     */
    public static <T> CommonResponse<T> fail(
            final String message
    ) {
        return CommonResponse.<T>builder()
                .status(false)
                .message(message)
                .build();
    }

}
