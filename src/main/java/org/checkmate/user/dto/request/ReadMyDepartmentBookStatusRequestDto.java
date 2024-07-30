package org.checkmate.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadMyDepartmentBookStatusRequestDto {

    private String loginId;
    private Long teamId;

}
