package org.checkmate.admin.controller.view;

import static javafx.scene.control.Alert.AlertType.*;
import static org.checkmate.admin.util.FilePath.BOOK_APPLY_FX;
import static org.checkmate.admin.util.FilePath.BOOK_LOAN_STATUS_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.USER_MANAGEMENT_FX;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.StringSplit;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 관리자 도서 등록 HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class BookCreatePageController implements Initializable {

  private final BookController bookController;

  public BookCreatePageController() {
    bookController = new BookController();
  }


  @FXML private TextField bookTitle;
  @FXML private TextField publisher;
  @FXML private TextField isbn;
  @FXML private TextField author;
  @FXML private TextField translator;
  @FXML private ChoiceBox categories;

  //시스템 종료
  @FXML private void exit(ActionEvent event) {
    Platform.exit();
  }

  //사이드바 이동
  //사이드바 이동
  @FXML
  private void goToBookManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
  }

  @FXML
  private void goToLoanStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_LOAN_STATUS_FX.getFilePath()); //변경
  }

  @FXML
  private void goToUserManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(USER_MANAGEMENT_FX.getFilePath());
  }

  @FXML
  private void goToApplyStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_APPLY_FX.getFilePath());
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }


  @FXML
  public void register(ActionEvent actionEvent) {
    System.out.println("등록버튼실행됨");
    try {
      String title = bookTitle.getText();
      String isbnText = isbn.getText();
      String authorText = author.getText();
      String translatorText = translator.getText();
      String publisherText = publisher.getText();
      String category = String.valueOf(categories.getValue());
      int categoryNum = StringSplit.getCategoryNum(category, ".");
      validateUserFields();
      bookController.createBook(title, isbnText, authorText, translatorText, publisherText,
          category, categoryNum);
    } catch (ValidationException | SQLException e) {
      showAlert(e.getMessage());
    }

  }

  public void showAlert(String msg) {
    Alert alert = new Alert(WARNING);
    alert.setTitle("경고!");
    alert.setHeaderText("도서 등록");
    alert.setContentText(msg);
    alert.show();
  }


  public void validateUserFields() {
    isNotEmpty(bookTitle, "책 제목을 입력해주세요.");
    isNotEmpty(isbn, "책의 ISBN을 입력해주세요.");
    isNotEmpty(author, "저자를 입력해주세요.");
    isNotEmpty(translator, "옮긴이를 입력해주세요.");
    isNotEmpty(publisher, "출판사를 입력해주세요.");
  }

  private void isNotEmpty(TextField field, String message) {
    if (field.getText().trim().isEmpty()) {
      throw new ValidationException(message);
    }
  }

}
