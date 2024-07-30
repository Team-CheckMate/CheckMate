package org.checkmate.admin.dto.response;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Getter
@ToString
@Builder
public class ReadBookLoanRecordsResponseDto {
    private Long blrId;
    private String loginId; // 사원 ID
    private String eName; // 사원 이름
    private String dName; // 부서 이름
    private String tName; // 팀 이름
    private String bName; // 대여 책 제목
    private Date loanDate; // 대여 날짜
    private Date returnPreDate; // 반납 예정일
    private Date returnDate; // 반납 날짜
    private String status;

    public ReadBookLoanRecordsResponseDto(Long blrId, String loginId, String eName, String dName,
        String tName, String bName, Date loanDate, Date returnPreDate, Date returnDate,
        String status) {
        this.blrId = blrId;
        this.loginId = loginId;
        this.eName = eName;
        this.dName = dName;
        this.tName = tName;
        this.bName = bName;
        this.loanDate = loanDate;
        this.returnPreDate = returnPreDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public static String checkStatusMethod(Date returnDate,Float delay){
        if(returnDate==null){
            if(delay<0){
                return "대출중";
            }else {
                return "연체중";
            }
        }else return "반납완료";
    }

    public static ReadBookLoanRecordsResponseDto of(Long blrId,String loginId,String eName,String dName,String tName,String bName,Date loanDate,Date returnPreDate,Date returnDate, Float delay){
        System.out.println("check");
        return new ReadBookLoanRecordsResponseDto(blrId,loginId,eName,dName,tName,bName,loanDate,returnPreDate,returnDate,checkStatusMethod(returnDate,delay));
    }
}
