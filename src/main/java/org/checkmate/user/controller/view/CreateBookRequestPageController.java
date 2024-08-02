package org.checkmate.user.controller.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.checkmate.user.util.FilePath.*;

/**
 * 도서신청 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.31]
 */
public class CreateBookRequestPageController implements Initializable {

    private final BookService bookService;

    public CreateBookRequestPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML private Hyperlink userNameLink;
    @FXML private Text tdName;
    @FXML private TextField bName;
    @FXML private TextField publisher;
    @FXML private TextField author;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginSession session = LoginSession.getInstance();
        var userInfo = session.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        tdName.setText(userInfo.getDName() + "\n" + userInfo.getTName());
    }

    @FXML
    public void createAddBtn(ActionEvent actionEvent) {
        if (bName.getText().isEmpty() ) {
            showAlert("도서명을 입력하세요.");
            return;
        } else if(publisher.getText().isEmpty()) {
            showAlert("출판사를 입력하세요.");
            return;
        } else if(author.getText().isEmpty()) {
            showAlert("저자를 입력하세요.");
            return;
        }
        String loginId = LoginSession.getInstance().getUserInfo().getLoginId();
        boolean isSuccess = bookService.createBookRequest(loginId,bName.getText(), publisher.getText(), author.getText());
        if(isSuccess) {
            showAlert("도서 신청을 완료했습니다.");
            SceneManager sm = SceneManager.getInstance();
            sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
        } else {
            showAlert("이미 신청처리된 도서가 존재하거나 등록 완료된 도서입니다.");
        }
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("도서");
        alert.setHeaderText("도서 신청");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void goHome(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MAIN_FX.getFilePath());
    }

    @FXML
    public void goToBookLoan(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_RENT_LOAN_BOOK_FX.getFilePath());
    }

    @FXML
    public void goToLoanManage(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_NOT_RENT_LOAN_BOOK_FX.getFilePath());
    }

    @FXML
    public void goToMyLoanBook(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    @FXML
    public void goToBookApply(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
