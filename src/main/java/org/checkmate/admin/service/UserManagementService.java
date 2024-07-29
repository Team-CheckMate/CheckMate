package org.checkmate.admin.service;

import org.checkmate.admin.dto.response.AdminMemberResponseDto;

import java.sql.SQLException;

public interface UserManagementService {

    // ObservableList<AdminMember> userDelete(String login_id) throws SQLException;
    AdminMemberResponseDto userDelete(String loginId) throws SQLException;
}
