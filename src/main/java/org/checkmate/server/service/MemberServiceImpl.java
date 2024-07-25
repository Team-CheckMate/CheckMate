package org.checkmate.server.service;

import org.checkmate.server.dto.request.ChangePwRequestDto;
import org.checkmate.server.dto.request.LoginRequestDto;
import org.checkmate.server.dto.response.ChangePwResponseDto;
import org.checkmate.server.dto.response.MemberInfoResponseDto;
import org.checkmate.server.entity.Member;
import org.checkmate.server.mapper.MemberMapper;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 회원 서비스 구현 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.24]
 * HISTORY2: Optional 타입 선언                     [송헌욱  2024.07.25]
 * HISTORY3: pw update 기능 추가                    [이준희  2024.07.25]
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

    @Override
    public ChangePwResponseDto changePw(ChangePwRequestDto changePwRequestDto) {
        try{
            System.out.println(changePwRequestDto.toString());
           int result = memberMapper.updateMemberPassword(changePwRequestDto.getMemberId(),changePwRequestDto.getChangePw());
           if(result>0){
               System.out.println("비번 변경 성공");
               return new ChangePwResponseDto(true,"비밀번호 변경이 완료되었습니다.");
           }else{
               System.out.println("비번 변경 실패");
               return new ChangePwResponseDto(false,"비밀번호 변경에 실패하였습니다.");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
