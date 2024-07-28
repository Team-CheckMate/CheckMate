module org.checkmate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.jfoenix;
    requires java.desktop;

    // 패키지를 FXML과 다른 모듈에 내보내기
    exports org.checkmate;
    opens org.checkmate to javafx.fxml;

    // admin.controller 패키지를 내보내고 열기
    exports org.checkmate.admin.controller.server;
    opens org.checkmate.admin.controller.server to javafx.fxml;
    exports org.checkmate.admin.controller.view;
    opens org.checkmate.admin.controller.view to javafx.fxml;

    // admin.dto.request 및 admin.dto.response 패키지를 내보내고 열기
    exports org.checkmate.admin.dto.request;
    exports org.checkmate.admin.dto.response;
    opens org.checkmate.admin.dto.response to javafx.base;

    // 필요한 경우 추가로 내보낼 패키지 추가
    exports org.checkmate.admin.mapper;
    opens org.checkmate.admin.mapper to javafx.fxml;

    exports org.checkmate.admin.service;
    opens org.checkmate.admin.service to javafx.fxml;
}
