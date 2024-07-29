package org.checkmate.user.dto.response;

import java.util.Date;
import java.util.Objects;
import javafx.scene.control.CheckBox;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 도서 대여 정보 응답 객체
 * HISTORY1: 최초 생성                                         [송헌욱  2024.07.25]
 * HISTORY2: 필드 추가                                         [권혁규  2024.07.29]
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadLoanStatusResponseDto {

    private Long bookId; // 고유 식별자
    private String ISBN; // ISBN
    private String bName; // 책이름
    private String author; // 저자
    private String publisher; // 출판사
    private Boolean lStatus; // 대출 가능 상태
    private Date loanDate; //대출 날짜
    private Date returnPreDate; // 반납 예정 날짜
    private Date returnDate; //반납 날짜
    private CheckBox select; //checkbox

    @Builder
    public ReadLoanStatusResponseDto(Long bookId, String ISBN, String bName, String author, String publisher,
                                     Boolean lStatus, Date loanDate, Date returnPreDate, Date returnDate, CheckBox select) {
        this.bookId = bookId;
        this.ISBN = ISBN;
        this.bName = bName;
        this.author = author;
        this.publisher = publisher;
        this.lStatus = lStatus;
        this.loanDate = loanDate;
        this.returnPreDate = returnPreDate;
        this.returnDate = returnDate;
        this.select = select;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ReadLoanStatusResponseDto that = (ReadLoanStatusResponseDto) object;
        return Objects.equals(bookId, that.bookId) && Objects.equals(ISBN,
                that.ISBN) && Objects.equals(bName, that.bName) && Objects.equals(
                author, that.author) && Objects.equals(publisher, that.publisher)
                && Objects.equals(lStatus, that.lStatus) && Objects.equals(
                loanDate, that.loanDate)
                && Objects.equals(returnPreDate, that.returnPreDate) &&
                Objects.equals(select, that.select);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, ISBN, bName, author, publisher, lStatus, loanDate, returnPreDate, returnDate, select);
    }

}
