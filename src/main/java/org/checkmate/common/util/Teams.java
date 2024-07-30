package org.checkmate.common.util;

public enum Teams {
  VOICE_CARE("보이스케어팀",1,0),
  COMPOSITE_AUTH_CONTROL("복합인증통제팀",2,0),
  AIRPORT_BUSINESS("공항사업팀",3,0),
  IMMIGRATION_BUSINESS("출입국사업팀",4,0),
  PUBLIC_PROCUREMENT("공공/조달팀",5,0),
  SECURITY("보안사업팀",6,0),
  OPEN_SOURCE_SUPPORT("공개SW기술지원 서비스팀",7,0),
  SYSTEM_INTEGRATION_MAINTENANCE("시스템통합유지관리 서비스팀",8,0),
  MSP_INFRA_SUPPORT("MSP인프라 지원팀",9,0),
  CLOUD_BUSINESS("클라우드 사업팀",10,0),
  STORAGE_BUSINESS("스토리지 사업팀",11,0),
  AI_PLATFORM("AI/플랫폼팀",12,0),
  RESEARCH_AND_DEVELOPMENT("연구개발팀",13,0);

  private final String teamName;
  private final int teamNo;
  private int count;

  Teams(String teamName, int teamNo, int count) {
    this.teamName = teamName;
    this.teamNo = teamNo;
    this.count = count;
  }

  public void updateCount(int count){
    this.count = count;
  }
}
