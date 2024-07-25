package org.checkmate.server.service;

import java.sql.SQLException;
import java.util.Optional;
import org.checkmate.server.dto.request.LoginRequestDto;
import org.checkmate.server.dto.response.MemberInfoResponseDto;
import org.checkmate.server.entity.Member;
import org.checkmate.server.mapper.MemberMapper;

/**
 * 회원 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: Optional 타입 선언                     [송헌욱  2024.07.25]
 */
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl() {
        this.memberMapper = new MemberMapper();
    }

    @Override
    public MemberInfoResponseDto login(LoginRequestDto requestDto) throws SQLException {
        Optional<Member> member = memberMapper.findByLoginIdAndPassword(
                requestDto.getLoginId(),
                requestDto.getPassword()
        );

        if (member.isEmpty()) {
            throw new SQLException("조회된 회원이 없습니다.");
        }

        Member foundMember = member.get();
        return MemberInfoResponseDto.from(foundMember);
    }
}
