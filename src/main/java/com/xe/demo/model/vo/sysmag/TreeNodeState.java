package com.xe.demo.model.vo.sysmag;

/**
 * 菜单状态
 */
public class TreeNodeState {
    private boolean selected;
    private boolean opened;
    private boolean disabled;

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isOpened()
    {
        return opened;
    }

    public void setOpened(boolean opened)
    {
        this.opened = opened;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }
}
