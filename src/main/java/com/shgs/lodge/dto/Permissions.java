package com.shgs.lodge.dto;

/**
 * 权限认证-操作权限
 */
public class Permissions {

    private String id;

    private String permissionsName;

    /**
     * 模块认证编号
     */
    private String roleType;

    /**
     * 菜单关联号
     */
    private String ids;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    public Permissions() {
        super();
    }

    public Permissions(String id, String permissionsName, String roleType, String ids) {
        this.id = id;
        this.permissionsName = permissionsName;
        this.roleType = roleType;
        this.ids = ids;
    }
}
