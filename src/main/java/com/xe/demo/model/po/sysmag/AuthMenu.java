package com.xe.demo.model.po.sysmag;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单表
 */
@Table(name = "auth_menu")
public class AuthMenu {

    private Integer menuId;

    private String menuName;

    private Integer parentId;

    private String icon;

    private String url;

    private Integer menuType;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    @Override
    public String toString() {
        return "AuthMenu{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", menuType=" + menuType +
                '}';
    }
}
