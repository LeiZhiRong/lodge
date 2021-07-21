package com.shags.lodge.util;

public enum RoleType {

  ROLE_MENU("模块管理"),ROLE_USER("用户信息"), ROLE_ROLE("角色管理"), ROLE_DEPT("部门信息"),ROLE_CUSTOM("数据字典")
  ,ROLE_CORP("客商信息"),ROLE_GRANT("角色授权"),ROLE_ACCOUN("单据设置"),ROLE_CASHBANK("会计科目"),ROLE_APPLY("开票申请")
  ,ROLE_CONFIRM("开票确认"),ROLE_BOOKSET("账套管理"),ROLE_REVE_EXPE_ITEM("收支项目"),ROLE_PROJECTS("辅助项目")
  ,ROLE_PAYMENT("结算方式"),ROLE_ACCOUN_PERIOD("会计期间"),ROLE_STATION("岗位信息"),ROLE_MANAGEPOINT("管理处"),ROLE_AssEto("资产分类");

  private String name;

  RoleType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}

