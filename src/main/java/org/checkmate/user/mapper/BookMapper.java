package org.checkmate.user.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import org.checkmate.common.database.DBConnector;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.common.util.TypeFormatter;

/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.25]
 * HISTORY2: findAllBookLoanStatus 메서드 수정      [권혁규  2024.07.26]
 * HISTORY2: admin파일 연결, 신규 책 등록 메서드 추가   [이준희  2024.07.26]
 * HISTORY3: 관리자 도서 전체조회, 선택 조회, 수정, 삭제 메서드 추가   [이준희  2024.07.27]
 * HISTORY4: 사용자 도서 전체조회, 검색 조회, 도서등록 메서드 추가   [권혁규  2024.07.29]
 */
public class BookMapper {

    private final Properties prop = new Properties();

    public BookMapper() {
        try {
            InputStream input = new FileInputStream(
                    "target/classes/org/checkmate/sql/userQuery.xml");
            prop.loadFromXML(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL에 접근하여 도서테이블의 책 정보를 모두 조회하는 SELECT 기능
     *
     * @return List<Book> 책 정보를 담은 리스트 컬랙션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<ReadLoanStatusResponseDto> findAllBookLoanStatus() throws SQLException {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findAllBookLoanStatus");
        CheckBox ch = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ch = new CheckBox();
                ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                        .bookId(resultSet.getLong("book_id"))
                        .ISBN(resultSet.getString("ISBN"))
                        .bName(resultSet.getString("b_name"))
                        .author(resultSet.getString("author"))
                        .publisher(resultSet.getString("publisher"))
                        .lStatus(TypeFormatter.IntegerToBoolean(resultSet.getInt("l_status")))
                        .returnPreDate(resultSet.getDate("return_pre_date"))
                        .select(ch)
                        .build();
                books.add(book);
            }
        }

        return books;
    }

    public void createLoanBook(CreateBookLoanRequestDto requestDto) {
        String query = prop.getProperty("addBookLoanRecord");
        try(Connection connection = DBConnector.getInstance().getConnection();
            CallableStatement callableStatement = connection.prepareCall(query);
            ) {
            for(ReadLoanStatusResponseDto bean : requestDto.getBookList()) {
                callableStatement.setLong(1, bean.getBookId());
                callableStatement.setString(2, requestDto.getLoginId());
                callableStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<ReadLoanStatusResponseDto> findByBookName(String searchName) throws SQLException {
        ObservableList<ReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findByBookName");
        CheckBox ch = null;

        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, "%" + searchName + "%");
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ch = new CheckBox();
                    ReadLoanStatusResponseDto book = ReadLoanStatusResponseDto.builder()
                            .bookId(resultSet.getLong("book_id"))
                            .ISBN(resultSet.getString("ISBN"))
                            .bName(resultSet.getString("b_name"))
                            .author(resultSet.getString("author"))
                            .publisher(resultSet.getString("publisher"))
                            .lStatus(TypeFormatter.IntegerToBoolean(resultSet.getInt("l_status")))
                            .returnPreDate(resultSet.getDate("return_pre_date"))
                            .select(ch)
                            .build();
                    books.add(book);
                }
            }

        }

        return books;
    }

}
