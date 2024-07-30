package org.checkmate.admin.service;

import javafx.collections.ObservableList;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;
import java.sql.SQLException;

public interface MemberService {
    ObservableList<AdminMemberResponseDto> findByMember() throws SQLException;
    int deleteUser(String loginId) throws SQLException;
    int createUser (String loginId, String eName) throws SQLException;
    int updatePw(String loginId) throws SQLException;
    ObservableList<AdminMemberResponseDto> searchMember(String str) throws SQLException;
}
