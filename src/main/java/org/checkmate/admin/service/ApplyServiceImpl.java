package org.checkmate.admin.service;

import javafx.collections.ObservableList;
import org.checkmate.admin.dto.response.ApplyStatusResponseDto;
import org.checkmate.admin.mapper.ApplyManagementMapper;

import java.sql.SQLException;


public class ApplyServiceImpl implements ApplyService {
    private final ApplyManagementMapper applyManagementMapper;
    public ApplyServiceImpl() {
         this.applyManagementMapper = new ApplyManagementMapper();
    }
    // 신청 현황 조회
    @Override
    public ObservableList<ApplyStatusResponseDto> readApplyStatus() throws Exception {
        return  applyManagementMapper.readApplyStatus();
    }

    // 신청 반려
    @Override
    public int updateRequestDate(Long brId) throws SQLException {
        return applyManagementMapper.updateRequestDate(brId);
    }
    //신청 승인
    @Override
    public int updateReturnDate(Long brId) throws SQLException {
        return applyManagementMapper.updateReturnDate(brId);
    }
}
