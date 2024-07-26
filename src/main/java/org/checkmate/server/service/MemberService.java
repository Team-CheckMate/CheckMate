package org.checkmate.server.service;

import org.checkmate.server.dto.request.ChangePwRequestDto;
import java.sql.SQLException;
import org.checkmate.server.dto.request.LoginRequestDto;
import org.checkmate.server.dto.response.ChangePwResponseDto;
import org.checkmate.server.dto.response.MemberInfoResponseDto;
import org.checkmate.server.dto.response.MyPageResponsedto;

/**
 * 회원 서비스 인터페이스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: pw 업데이트 추가                        [이준희  2024.07.25]
 */
public interface MemberService {

    MemberInfoResponseDto login(LoginRequestDto requestDto) throws SQLException;
    ChangePwResponseDto changePw(ChangePwRequestDto changePwRequestDto) throws SQLException;
    MyPageResponsedto getMypageInfo(String loginId) throws SQLException;
}
