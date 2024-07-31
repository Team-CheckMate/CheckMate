package org.checkmate.user.util;

import lombok.Getter;

@Getter
public enum FilePath {

    MAIN_FX("/org/checkmate/view/layouts/user/mainPage.fxml"),
    MAIN_ST("/org/checkmate/view/style/user/mainPage.css"),

    READ_MY_INFO_FX("/org/checkmate/view/layouts/user/readMyInformationPage.fxml"),
    READ_MY_INFO_ST("/org/checkmate/view/style/user/readMyInformationPage.css"),

    READ_TM_LOAN_STATUS_FX("/org/checkmate/view/layouts/user/teamMemberLoanStatusPage.fxml"),
    READ_TM_LOAN_STATUS_ST("/org/checkmate/view/layouts/user/teamMemberLoanStatusPage.css"),

    UPDATE_PW_FX("/org/checkmate/view/layouts/user/updatePasswordPage.fxml"),
    UPDATE_PW_ST("/org/checkmate/view/layouts/user/updatePasswordPage.css");

    private final String filePath;

    FilePath(String filePath) {
        this.filePath = filePath;
    }

}
