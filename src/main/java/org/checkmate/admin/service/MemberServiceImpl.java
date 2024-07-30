package org.checkmate.admin.service;

import javafx.collections.ObservableList;
import org.checkmate.admin.mapper.UserManagementMapper;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;
import java.sql.SQLException;

public class MemberServiceImpl implements MemberService {
    private  final UserManagementMapper userManagementMapper ;

    public MemberServiceImpl( ) {
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

    @Override
    public int updatePw(String loginId) throws SQLException{
        return userManagementMapper.updatePw(loginId);
    }

    @Override
    public ObservableList<AdminMemberResponseDto> searchMember(String str) throws SQLException{
        return userManagementMapper.searchMember(str);
    }

}
