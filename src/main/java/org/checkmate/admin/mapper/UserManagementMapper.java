package org.checkmate.admin.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.checkmate.common.database.DBConnector;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;


/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [황희정  2024.07.27]

 */
public class UserManagementMapper {

    private final Properties prop = new Properties();

    public UserManagementMapper() {

        try {
            InputStream input = new FileInputStream("target/classes/org/checkmate/sql/adminQuery.xml");
            prop.loadFromXML(input);
            System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL에 접근하여 사용자를 모두 조회하는 기능
     * @return List<Member>  사용자 정보를 담은 리스트 컬렉션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<AdminMemberResponseDto> findByMember() throws SQLException {
        ObservableList<AdminMemberResponseDto> members = FXCollections.observableArrayList();
        String query = prop.getProperty("findByMember");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                AdminMemberResponseDto member = AdminMemberResponseDto.builder()
                        .loginId(resultSet.getString("login_id"))
                        .eName(resultSet.getString("e_name"))
                        .tName(resultSet.getString("t_name"))
                        .dName(resultSet.getString("d_name"))
                        .build();
                members.add(member);
            }
        }
        return members;
    }
    /**
     * SQL에 접근하여 선택한 사용자를 삭제하는 기능
     * @throws SQLException SQL 서버 에러
     */
    public int deleteUser(String loginId) throws SQLException {
        String query = prop.getProperty("deleteUser");

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
                preparedStatement.setString(1,loginId);
            return preparedStatement.executeUpdate();
        }

    }
    /**
     * SQL에 접근하여 사용자를 추가하는 기능
     * @throws SQLException SQL 서버 에러
     */
    public int createUser(String loginId,String eName){
        String query = prop.getProperty("createUser");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1,loginId);
            preparedStatement.setString(2,eName);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    /**
     * SQL에 접근하여 선택한 사용자의 비밀번호를 초기화하는 기능
     * @throws SQLException SQL 서버 에러
     */
    public int updatePw(String loginId){
        String query = prop.getProperty("updatePw");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1,loginId);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    /**
     * SQL에 접근하여 입력된 검색어에 해당된 사용자를 검색하는 기능
     * @return List<Member>  사용자 정보를 담은 리스트 컬렉션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<AdminMemberResponseDto> searchMember(String str){
        ObservableList<AdminMemberResponseDto> members = FXCollections.observableArrayList();
        String query = prop.getProperty("searchMember");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        )    {
            preparedStatement.setString(1, "%" + str + "%");
            preparedStatement.setString(2, "%" + str + "%");
            preparedStatement.setString(3, "%" + str + "%");
            preparedStatement.setString(4, "%" + str + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AdminMemberResponseDto member = AdminMemberResponseDto.builder()
                        .loginId(resultSet.getString("login_id"))
                        .eName(resultSet.getString("e_name"))
                        .tName(resultSet.getString("t_name"))
                        .dName(resultSet.getString("d_name"))
                        .build();
                members.add(member);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return members;

    }


}
