package org.checkmate.admin.service;

import javafx.collections.ObservableList;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;

import java.sql.SQLException;

public interface AdminMemberService {
    ObservableList<AdminMemberResponseDto> findByMember() throws SQLException;
    int deleteUser(String loginId) throws SQLException;
    int createUser (String loginId, String eName) throws SQLException;
}
