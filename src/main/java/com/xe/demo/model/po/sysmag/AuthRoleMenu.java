package com.xe.demo.model.po.sysmag;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "auth_role_menu")
public class AuthRoleMenu {
    @Id
    private Integer id;
    @Id
    private Integer roleId;
    @Id
    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
