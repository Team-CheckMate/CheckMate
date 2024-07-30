package org.checkmate.admin.service;

import javafx.collections.ObservableList;
import org.checkmate.admin.mapper.UserManagementMapper;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;


import java.sql.SQLException;

public class AdminMemberServiceImpl implements AdminMemberService {
    private  final UserManagementMapper userManagementMapper ;

    public AdminMemberServiceImpl( ) {
        this.userManagementMapper = new UserManagementMapper();
    }

    @Override
    public ObservableList<AdminMemberResponseDto> findByMember() throws SQLException {
        return userManagementMapper.findByMember();
    }
    @Override
    public int deleteUser(String loginId) throws SQLException {
        return userManagementMapper.deleteUser(loginId);
    }
    @Override
    public int createUser (String loginId, String eName) throws SQLException{
        return userManagementMapper.createUser(loginId, eName);
    }

}
