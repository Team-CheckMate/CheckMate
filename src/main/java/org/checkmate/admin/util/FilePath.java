package org.checkmate.admin.util;

import lombok.Getter;

@Getter
public enum FilePath {

    MANAGEMENT_FX("/org/checkmate/view/layouts/admin/managementPage.fxml"),
    MANAGEMENT_ST("/org/checkmate/view/style/admin/managementPage.css"),

    USER_MANAGEMENT_FX("/org/checkmate/view/layouts/admin/userManagementPage.fxml"),
    USER_MANAGEMENT_ST("/org/checkmate/view/style/admin/userManagementPage.css"),

    BOOK_CREATE_FX("/org/checkmate/view/layouts/admin/bookCreatePage.fxml"),
    BOOK_CREATE_ST("/org/checkmate/view/style/admin/bookCreatePage.css"),

    BOOK_UPDATE_FX("/org/checkmate/view/layouts/admin/bookUpdatePage.fxml"),
    BOOK_UPDATE_ST("/org/checkmate/view/style/admin/bookUpdatePage.css"),

    BOOK_MANAGEMENT_FX("/org/checkmate/view/layouts/admin/bookManagementPage.fxml"),
    BOOK_MANAGEMENT_ST("/org/checkmate/view/style/admin/bookManagementPage.css"),

    RENT_STATUS_CHART_DEPARTMENTS_FX("/org/checkmate/view/layouts/admin/bookRentChartPageForDepartments.fxml"),
//    RENT_STATUS_CHART_DEPARTMENTS_ST
    RENT_STATUS_CHART_TEAMS_FX("/org/checkmate/view/layouts/admin/bookRentChartPageForTeams.fxml");


    private final String filePath;

    FilePath(String filePath) {
        this.filePath = filePath;
    }

}
