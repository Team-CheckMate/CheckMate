package org.checkmate.user.dto.response;

import lombok.*;

import java.util.Objects;

/**
 * 도서 신청 목록 응답 객체
 * HISTORY1: 최초 생성                                         [권혁규  2024.07.31]
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadBookRequestResponseDto {
    private long rownum;
    private String loginId;     //로그인ID(사원 번호)
    private String eName;       //사원이름
    private String bName;       //책 제목
    private String author;      //저자
    private String publisher;   //출판사
    private String status;      //진행상태

    @Builder
    public ReadBookRequestResponseDto(long rownum, String loginId, String eName, String bName, String author, String publisher, String status) {
        this.rownum = rownum;
        this.loginId = loginId;
        this.eName = eName;
        this.bName = bName;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadBookRequestResponseDto that = (ReadBookRequestResponseDto) o;
        return rownum == that.rownum && Objects.equals(loginId, that.loginId) && Objects.equals(bName, that.bName) && Objects.equals(author, that.author) && Objects.equals(publisher, that.publisher) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rownum, loginId, bName, author, publisher, status);
    }
}
