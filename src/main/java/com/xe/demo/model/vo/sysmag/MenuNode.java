package com.xe.demo.model.vo.sysmag;

import com.xe.demo.model.po.sysmag.AuthMenu;

import java.util.List;

/**
 * 首页的菜单节点
 */
public class MenuNode {
    /**
     * 父节点
     */
    AuthMenu parentNode;
    /**
     * 子节点
     */
    List<AuthMenu> childsNode;

    public AuthMenu getParentNode() {
        return parentNode;
    }

    public void setParentNode(AuthMenu parentNode) {
        this.parentNode = parentNode;
    }

    public List<AuthMenu> getChildsNode() {
        return childsNode;
    }

    public void setChildsNode(List<AuthMenu> childsNode) {
        this.childsNode = childsNode;
    }

    @Override
    public String toString() {
        return "MenuNode{" +
                "parentNode=" + parentNode +
                ", childsNode=" + childsNode +
                '}';
    }
}
