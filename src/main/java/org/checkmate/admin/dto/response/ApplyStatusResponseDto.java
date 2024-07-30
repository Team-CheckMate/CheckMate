package org.checkmate.admin.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyStatusResponseDto {
    private Long brId;
    private String loginId; // 로그인 아이디
    private String eName; // 사용자 이름
    private String bName; // 책 이름
    private String publisher; // 출판사 이름
    private String author; //작가 이름

}

