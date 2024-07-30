package org.checkmate.common.util;

import lombok.Getter;

@Getter
public enum Departments {
  STRATEGY("전략사업본부", 1,0),
  SECURITY("보안사업본부",2, 0),
  ITO("ITO사업본부",3, 0),
  IDC("IDC사업본부",4, 0),
  SOLUTION("솔루션사업본부",5, 0),
  NEW_BUSINESS("신사업본부",6, 0);

  private final String departmentName;
  private final int departmentNo;
  private int count;

  Departments(String departmentName, int departmentNo, int count) {
    this.departmentName = departmentName;
    this.departmentNo = departmentNo;
    this.count = count;
  }

  public void updateCount(int count){
    this.count = count;
  }
}
