package org.checkmate.server.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import org.checkmate.database.DBConnector;
import org.checkmate.server.dto.request.AddBookRequestDto;
import org.checkmate.server.dto.request.EditBookRequestDto;
import org.checkmate.server.dto.response.AddBookResponseDto;
import org.checkmate.server.dto.response.EditBookResponseDto;
import org.checkmate.server.dto.response.FindAllBooksAdminResponseDto;
import org.checkmate.server.dto.response.FindSelectedBookAdminResponseDto;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.util.TypeFormatter;

/**
 * SQL Query mapper 클래스
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.25]
 * HISTORY2: findAllBookLoanStatus 메서드 수정      [권혁규  2024.07.26]
 * HISTORY2: admin파일 연결, 신규 책 등록 메서드 추가   [이준희  2024.07.26]
 * HISTORY3: 관리자 도서 전체조회, 선택 조회, 수정, 삭제 메서드 추가   [이준희  2024.07.27]
 */
public class BookMapper {

    private final Properties prop = new Properties();
    private final Properties admin_prop = new Properties();

    public BookMapper() {
        try {
            InputStream input = new FileInputStream(
                    "target/classes/org/checkmate/sql/userQuery.xml");
            prop.loadFromXML(input);

            InputStream adminInput = new FileInputStream(
                    "target/classes/org/checkmate/sql/adminQuery.xml");
            admin_prop.loadFromXML(adminInput);
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
    public ObservableList<BookLoanStatus> findAllBookLoanStatus() throws SQLException {
        ObservableList<BookLoanStatus> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findAllBookLoanStatus");
        CheckBox ch = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ch = new CheckBox();
                BookLoanStatus book = BookLoanStatus.builder()
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

    public int addBookLoanRecord(long memberId, ObservableList<BookLoanStatus> bookList) {
        String query = prop.getProperty("addBookLoanRecord");
        try(Connection connection = DBConnector.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ) {
            preparedStatement.setLong(1,memberId);
            preparedStatement.setString(2, bookList.toString());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 관리자가 SQL에 접근하여 도서테이블의 책 정보를 모두 조회하는 SELECT 기능
     *
     * @return List<FindAllBooksAdminResponseDto> 응답 책 정보를 담은 리스트 컬랙션
     * @throws SQLException SQL 서버 에러
     */
    public ObservableList<FindAllBooksAdminResponseDto> findAllBookAdmin() throws SQLException {
        ObservableList<FindAllBooksAdminResponseDto> books = FXCollections.observableArrayList();
        String query = admin_prop.getProperty("findAllBookAdmin");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                FindAllBooksAdminResponseDto book = FindAllBooksAdminResponseDto.builder()
                        .bookId(resultSet.getLong("book_id"))
                        .bName(resultSet.getString("b_name"))
                        .ISBN(resultSet.getString("ISBN"))
                        .author(resultSet.getString("author"))
                        .publisher(resultSet.getString("publisher"))
                        .lStatus(TypeFormatter.BooleanToString(TypeFormatter.IntegerToBoolean(resultSet.getInt("l_status"))))
                        .addDate(resultSet.getDate("add_date"))
                        .eName(resultSet.getString("e_name"))
                        .build();
                books.add(book);
                System.out.println(book.toString());
            }
        }
        return books;
    }

    /**
     * 책 정보를 입력받아 신규 책 등록하는 INSERT 기능
     *
     * @return AddBookResponseDto 객체 반환
     * @throws SQLException SQL 서버 에러
     */

    public AddBookResponseDto addNewBook(AddBookRequestDto requestDto) throws SQLException {
        String query = admin_prop.getProperty("addBook");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, requestDto.getIsbn());
            preparedStatement.setString(2, requestDto.getBookTitle());
            preparedStatement.setString(3, requestDto.getAuthor());
            preparedStatement.setString(4, requestDto.getTranslator());
            preparedStatement.setString(5, requestDto.getPublisher());
            preparedStatement.setInt(6, requestDto.getCategory_num());
            try {
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            AddBookResponseDto addBookResponseDto = new AddBookResponseDto(true, requestDto.getBookTitle() + "이 등록되었습니다.");
            return addBookResponseDto;
        }
    }

    /**
     * 책 수정 정보를 입력받아 책 정보 수정하는 UPDATE 기능
     * @Param EditBookRequestDto 책 수정 정보 객체
     * @return EditBookResponseDto 객체 반환
     * @throws SQLException SQL 서버 에러
     */

    public EditBookResponseDto editBook(EditBookRequestDto requestDto) throws SQLException {
        String query = admin_prop.getProperty("editBook");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setLong(1, requestDto.getBookId());
            callableStatement.setString(2, requestDto.getIsbn());
            callableStatement.setString(3, requestDto.getBookTitle());
            callableStatement.setString(4, requestDto.getAuthor());
            callableStatement.setString(5, requestDto.getTranslator());
            callableStatement.setString(6, requestDto.getPublisher());
            callableStatement.setInt(7, requestDto.getCategory_num());
            callableStatement.setInt(8, requestDto.getL_status());
            try {
                callableStatement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            EditBookResponseDto responseDto = new EditBookResponseDto(true, requestDto.getBookTitle() + "이 수정되었습니다.");
            return responseDto;
        }
    }

    /**
     * 관리자가 선택된 도서를 수정할때 해당 도서의 정보를 받아오는 기능
     * @param bookId 책 아이디
     * @return FindSelectedBookAdminResponseDto 응답객체
     * @throws SQLException SQL 서버 에러
     */
    public FindSelectedBookAdminResponseDto findBook(Long bookId) throws SQLException {
        String query = admin_prop.getProperty("findSelectedBookAdmin");
        FindSelectedBookAdminResponseDto book = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, bookId);

            // 쿼리 실행 및 결과 집합 받기
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // ResultSet에서 데이터 추출
                     book = FindSelectedBookAdminResponseDto.builder()
                            .bName(resultSet.getString("b_name"))
                            .publisher(resultSet.getString("publisher"))
                            .isbn(resultSet.getString("isbn"))
                            .author(resultSet.getString("author"))
                            .translator(resultSet.getString("translator"))
                            .categoryId(resultSet.getInt("category_id"))
                            .cName(resultSet.getString("c_name"))
                             .lStatus(resultSet.getBoolean("l_status"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    /**
     * 선택된 책을 삭제하는 기능
     * @param bookId 책 아이디
     * @return 변경결과
     * @throws SQLException SQL 서버 에러
     */

    public boolean deleteSelectedBook(Long bookId) throws SQLException{
        String query = admin_prop.getProperty("deleteSelectedBook");
        int deleteRows = 0;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setLong(1,bookId);
            try {
                    deleteRows = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleteRows > 0;
    }
}
