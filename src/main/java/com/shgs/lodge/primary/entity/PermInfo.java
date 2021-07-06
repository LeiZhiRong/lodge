package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "perm_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class PermInfo {
    private  String id;
    private  String role_id;
    private  MenuInfo menuInfo;

    @Id
    @GeneratedValue(generator = "uuid2" )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    @ManyToOne
    @JoinColumn(name = "menu_id")
    public MenuInfo getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(MenuInfo menuInfo) {
        this.menuInfo = menuInfo;
    }

    public PermInfo() {
        super();
    }

    public PermInfo(String role_id, MenuInfo menuInfo) {
        this.role_id = role_id;
        this.menuInfo = menuInfo;
    }
}
