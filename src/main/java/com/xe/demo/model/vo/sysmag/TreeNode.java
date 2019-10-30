package com.xe.demo.model.vo.sysmag;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树形图基础类
 */
public class TreeNode implements Serializable
{
    private Integer id;
    private String text;
    private String icon;
    private Integer type;

    private Integer depth;

    private TreeNodeState state;

    private List<TreeNode> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public TreeNodeState getState() {
        return state;
    }

    public void setState(TreeNodeState state) {
        this.state = state;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}

