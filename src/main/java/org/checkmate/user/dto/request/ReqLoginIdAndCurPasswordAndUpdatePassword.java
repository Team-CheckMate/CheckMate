package org.checkmate.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqLoginIdAndCurPasswordAndUpdatePassword {

    private String loginId;
    private String curPassword;
    private String updatePassword;

}
