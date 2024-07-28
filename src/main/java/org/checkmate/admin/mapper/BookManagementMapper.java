package org.checkmate.admin.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookCreateResponseDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;
import org.checkmate.database.DBConnector;
import org.checkmate.server.util.TypeFormatter;

public class BookManagementMapper {

    private final Properties prop = new Properties();

    public BookManagementMapper() {
        try {
            InputStream adminInput = new FileInputStream(
                    "target/classes/org/checkmate/sql/adminQuery.xml");
            prop.loadFromXML(adminInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 관리자가 SQL에 접근하여 도서테이블의 책 정보를 모두 조회하는 기능
     * @return ObservableList<BookReadLoanStatusResponseDto> 응답 책 정보를 담은 리스트 컬랙션
     * @throws SQLException
     */
    public ObservableList<BookReadLoanStatusResponseDto> findAllBookAdmin() throws SQLException {
        ObservableList<BookReadLoanStatusResponseDto> books = FXCollections.observableArrayList();
        String query = prop.getProperty("findAllBookAdmin");
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                BookReadLoanStatusResponseDto book = BookReadLoanStatusResponseDto.builder()
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
     * @return BookCreateResponseDto 객체 반환
     * @throws SQLException SQL 서버 에러
     */
    public BookCreateResponseDto addNewBook(BookCreateRequestDto requestDto) throws SQLException {
        String query = prop.getProperty("addBook");
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

            BookCreateResponseDto bookCreateResponseDto = new BookCreateResponseDto(true, requestDto.getBookTitle() + "이 등록되었습니다.");
            return bookCreateResponseDto;
        }
    }

    /**
     * 책 수정 정보를 입력받아 책 정보 수정하는 UPDATE 기능
     * @Param BookUpdateRequestDto 책 수정 정보 객체
     * @return BookUpdateResponseDto 객체 반환
     * @throws SQLException SQL 서버 에러
     */
    public BookUpdateResponseDto editBook(BookUpdateRequestDto requestDto) throws SQLException {
        String query = prop.getProperty("editBook");
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

            BookUpdateResponseDto responseDto = new BookUpdateResponseDto(true, requestDto.getBookTitle() + "이 수정되었습니다.");
            return responseDto;
        }
    }

    /**
     * 관리자가 선택된 도서를 수정할때 해당 도서의 정보를 받아오는 기능
     * @param bookId 책 아이디
     * @return BookReadInformationResponseDto 응답객체
     * @throws SQLException SQL 서버 에러
     */
    public BookReadInformationResponseDto findBook(Long bookId) throws SQLException {
        String query = prop.getProperty("findSelectedBookAdmin");
        BookReadInformationResponseDto book = null;
        try (
                Connection connection = DBConnector.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, bookId);

            // 쿼리 실행 및 결과 집합 받기
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // ResultSet에서 데이터 추출
                    book = BookReadInformationResponseDto.builder()
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
        String query = prop.getProperty("deleteSelectedBook");
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
