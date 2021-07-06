package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class UserRole {

    /**
     * 关键字
     */
    private String id;

    /**
     * 角色
     */
    private RoleInfo role;

    /**
     * 用户
     */
    private UserInfo user;


    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "roleID")
    public RoleInfo getRole() {
        return role;
    }

    public void setRole(RoleInfo role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "userID")
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserRole() {
        super();
    }

    public UserRole(RoleInfo role, UserInfo user) {
        this.role = role;
        this.user = user;
    }
}
