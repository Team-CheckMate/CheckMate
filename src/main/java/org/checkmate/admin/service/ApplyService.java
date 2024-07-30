package org.checkmate.admin.service;

import javafx.collections.ObservableList;
import org.checkmate.admin.dto.response.ApplyStatusResponseDto;

import java.sql.SQLException;

public interface ApplyService {
    ObservableList<ApplyStatusResponseDto> readApplyStatus() throws Exception;
int updateRequestDate(Long brId) throws SQLException;
int updateReturnDate(Long brId) throws SQLException;
}
