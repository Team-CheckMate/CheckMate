module org.checkmate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.jfoenix;
    requires java.desktop;

    // 기본 패키지를 내보내고 열기
    exports org.checkmate;
    opens org.checkmate to javafx.fxml;

    // admin.controller 패키지를 내보내고 열기
    exports org.checkmate.admin.controller.server;
    opens org.checkmate.admin.controller.server to javafx.fxml;
    exports org.checkmate.admin.controller.view;
    opens org.checkmate.admin.controller.view to javafx.fxml;

    // admin.dto 패키지를 내보내고 필요에 따라 열기
    exports org.checkmate.admin.dto.request;
    exports org.checkmate.admin.dto.response;
    opens org.checkmate.admin.dto.response to javafx.base;

    // admin.mapper 패키지를 내보내고 열기
    exports org.checkmate.admin.mapper;
    opens org.checkmate.admin.mapper to javafx.fxml;

    // admin.service 패키지를 내보내고 열기
    exports org.checkmate.admin.service;
    opens org.checkmate.admin.service to javafx.fxml;

    // admin.util 패키지를 내보내고 열기
    exports org.checkmate.admin.util;
    opens org.checkmate.admin.util to javafx.fxml;

    // common.controller 패키지를 내보내고 열기
    exports org.checkmate.common.controller.view;
    opens org.checkmate.common.controller.view to javafx.fxml;

    // common.database 패키지를 내보내고 열기
    exports org.checkmate.common.database;
    opens org.checkmate.common.database to javafx.fxml;

    // common.dto 패키지를 내보내고 필요에 따라 열기
    exports org.checkmate.common.dto.request;
    exports org.checkmate.common.dto.response;
    opens org.checkmate.common.dto.response to javafx.base;

    // common.entity 패키지를 내보내고 열기
    exports org.checkmate.common.entity;
    opens org.checkmate.common.entity to javafx.fxml;

    // common.exception 패키지를 내보내고 열기
    exports org.checkmate.common.exception;
    opens org.checkmate.common.exception to javafx.fxml;

    // common.service 패키지를 내보내고 열기
    exports org.checkmate.common.service;
    opens org.checkmate.common.service to javafx.fxml;

    // common.util 패키지를 내보내고 열기
    exports org.checkmate.common.util;
    opens org.checkmate.common.util to javafx.fxml;

    // user.controller 패키지를 내보내고 열기
    exports org.checkmate.user.controller.server;
    opens org.checkmate.user.controller.server to javafx.fxml;
    exports org.checkmate.user.controller.view;
    opens org.checkmate.user.controller.view to javafx.fxml;

    // user.dto 패키지를 내보내고 필요에 따라 열기
    exports org.checkmate.user.dto.request;
    exports org.checkmate.user.dto.response;
    opens org.checkmate.user.dto.response to javafx.base;

    // user.mapper 패키지를 내보내고 열기
    exports org.checkmate.user.mapper;
    opens org.checkmate.user.mapper to javafx.fxml;

    // user.service 패키지를 내보내고 열기
    exports org.checkmate.user.service;
    opens org.checkmate.user.service to javafx.fxml;

    // user.util 패키지를 내보내고 열기
    exports org.checkmate.user.util;
    opens org.checkmate.user.util to javafx.fxml;
}
