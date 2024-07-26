package org.checkmate.server.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.server.entity.Book;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.mapper.BookMapper;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 도서 관리
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class admin_bookManagementPageController implements Initializable  {

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private TableView<BookLoanStatus> table_book;

    @FXML
    private TableColumn<BookLoanStatus, String> bName;

    @FXML
    private TableColumn<BookLoanStatus, String> publisher;

    @FXML
    private TableColumn<BookLoanStatus, String> author;

    @FXML
    private TableColumn<BookLoanStatus, Boolean> lStatus;

    @FXML
    private TableColumn<BookLoanStatus, Date> date;

    @FXML
    private TableColumn<BookLoanStatus, CheckBox> select;

    ObservableList<BookLoanStatus> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }

    private void loadDate() throws SQLException {
        select.setCellValueFactory(new PropertyValueFactory<BookLoanStatus, CheckBox>("select"));
        bName.setCellValueFactory(new PropertyValueFactory<BookLoanStatus, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<BookLoanStatus, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<BookLoanStatus, String>("author"));
        lStatus.setCellValueFactory(new PropertyValueFactory<BookLoanStatus, Boolean>("lStatus"));
        date.setCellValueFactory(new PropertyValueFactory<BookLoanStatus, Date>("returnPreDate"));
        BookMapper bm = new BookMapper();
        //bookList = bm.findAllBookLoanStatus();
        table_book.setItems(bookList);
    }

    @FXML
    public void moveToAddBook(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookAddPage.fxml");
    }
}
