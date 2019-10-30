package com.xe.demo.mapper.sysmag;

import com.xe.demo.model.po.sysmag.AuthRoleMenu;

import java.util.List;

/**
 * 菜单角色关联表查询语句
 */
public interface AuthRoleMenuMapper {

    /**
     *获取一个角色下的所有菜单
     * @param roleId
     * @return
     */
    List<AuthRoleMenu> getRoleMenuList(Integer roleId);

    /**
     * 根据角色id 删除该角色下的菜单
     * @param roleId
     */
    void deleteMenu(Integer roleId);

    /**
     * 批量添加菜单
     * @param list
     */
    void saveMenu(List<AuthRoleMenu> list);


}
