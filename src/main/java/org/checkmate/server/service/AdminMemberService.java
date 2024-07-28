package org.checkmate.server.service;

import javafx.collections.ObservableList;
import org.checkmate.server.dto.response.AdminMemberResponseDto;
import org.checkmate.server.entity.AdminMember;
import org.checkmate.server.entity.BookLoanStatus;

import java.sql.SQLException;

public interface AdminMemberService {

//    ObservableList<AdminMember> userDelete(String login_id) throws SQLException;
    AdminMemberResponseDto userDelete(String loginId) throws SQLException;
}
