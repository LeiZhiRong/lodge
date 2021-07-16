package com.shags.lodge.dto;

import java.util.List;

/**
 * 权限认证-角色组
 */
public class Role {

    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private List<Permissions> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Role() {
        super();
    }

    public Role(String id, String roleName, List<Permissions> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.permissions = permissions;
    }
}
