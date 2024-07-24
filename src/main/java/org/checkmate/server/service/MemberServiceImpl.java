package org.checkmate.server.service;

import java.sql.SQLException;
import org.checkmate.server.dto.request.LoginRequestDto;
import org.checkmate.server.dto.response.LoginResponseDto;
import org.checkmate.server.entity.Member;
import org.checkmate.server.mapper.MemberMapper;

/**
 * 회원 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 */
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl() {
        this.memberMapper = new MemberMapper();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        try {
            Member member = memberMapper.findByLoginIdAndPassword(requestDto.getLoginId(),
                    requestDto.getPassword());
            if (member == null) {
                throw new SQLException("조회된 회원이 없습니다.");
            }
            return new LoginResponseDto(true, member, "Login successful");
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResponseDto(false, null, "An error occurred during login");
        }
    }
}
