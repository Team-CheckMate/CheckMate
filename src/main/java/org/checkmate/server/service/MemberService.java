package org.checkmate.server.service;

import java.sql.SQLException;
import org.checkmate.server.dto.request.LoginRequestDto;
import org.checkmate.server.dto.response.MemberInfoResponseDto;

/**
 * 회원 서비스 인터페이스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 */
public interface MemberService {

    MemberInfoResponseDto login(LoginRequestDto requestDto) throws SQLException;

}
