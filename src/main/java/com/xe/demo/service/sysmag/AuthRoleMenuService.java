package com.xe.demo.service.sysmag;

import com.xe.demo.common.pojo.AjaxResult;
import com.xe.demo.common.utils.AppUtil;
import com.xe.demo.mapper.sysmag.AuthMenuMapper;
import com.xe.demo.mapper.sysmag.AuthRoleMenuMapper;
import com.xe.demo.model.po.sysmag.AuthMenu;
import com.xe.demo.model.po.sysmag.AuthRoleMenu;
import com.xe.demo.model.vo.sysmag.TreeNode;
import com.xe.demo.model.vo.sysmag.TreeNodeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthRoleMenuService {
    @Autowired
    private AuthRoleMenuMapper authRoleMenuMapper;
    @Autowired
    private AuthMenuMapper authMenuMapper;



    public   List<TreeNode> getMenuByRole(Integer roleId){

        //获取当前角色下的所有菜单
        List<AuthRoleMenu> roleMenuList1 = authRoleMenuMapper.getRoleMenuList(roleId);

        List<TreeNode> list = new ArrayList<TreeNode>();

        List<AuthMenu> menuList = authMenuMapper.getMenuList(new AuthMenu());
        if (menuList == null ){
            return null;
        }
        //菜单不为空时
        for (AuthMenu menu:menuList) {
            if (menu.getMenuType() == 0){ //父节点
                TreeNode treeNode = new TreeNode();
                treeNode.setId(menu.getMenuId());
                treeNode.setText(menu.getMenuName());
                treeNode.setIcon(menu.getIcon());
                treeNode.setType(menu.getMenuType());
                treeNode.setDepth(1);
                TreeNodeState state = new TreeNodeState();
                state.setSelected(false);
                state.setOpened(true);
                state.setDisabled(false);
                treeNode.setState(state);
                //查询当前父id下的所有的子节点
                AuthMenu authMenu = new AuthMenu();
                authMenu.setParentId(menu.getMenuId());
                authMenu.setMenuType(1);
                //获取该父节点下的子节点
                List<AuthMenu> menuList1 = authMenuMapper.getMenuList(authMenu);
                if (menuList1 != null){
                    List<TreeNode>  list2 = new ArrayList<>();
                    for (AuthMenu amenu: menuList1) {
                        TreeNode treeNode2 = new TreeNode();
                        treeNode2.setId(amenu.getMenuId());
                        treeNode2.setText(amenu.getMenuName());
                        treeNode2.setIcon(amenu.getIcon());
                        treeNode2.setType(amenu.getMenuType());
                        treeNode2.setDepth(1);
                        TreeNodeState state2 = new TreeNodeState();
                        state2.setSelected(isMenuSelect(roleMenuList1,amenu.getMenuId()));
                        state2.setOpened(true);
                        state2.setDisabled(false);
                        treeNode2.setState(state2);
                        treeNode2.setChildren(null);
                        list2.add(treeNode2);
                    }
                    treeNode.setChildren(list2);
                }else{
                    treeNode.setChildren(null);
                }
                list.add(treeNode);
            }
        }
        return  list;
    }



    @Transactional
    public  AjaxResult updateRoleMenu(Integer roleId,String[] menusStr){
        //删除原来的
        authRoleMenuMapper.deleteMenu(roleId);
        //添加新的
        List<AuthRoleMenu > list = new ArrayList<>();
        if (menusStr != null ){
            for (String menuIdStr:menusStr) {
                AuthRoleMenu authRoleMenu = new AuthRoleMenu();
                authRoleMenu.setRoleId(roleId);
                Integer menuId = Integer.parseInt(menuIdStr);
                authRoleMenu.setMenuId(menuId);
                list.add(authRoleMenu);
          }
        }
        authRoleMenuMapper.saveMenu(list);
        return AppUtil.returnMsg(1,"更新成功");
    }

    /**
     * 查询当前节点是是否已经被选择了
     * @param menuList
     * @param menuId
     * @return
     */
    public  boolean isMenuSelect(List<AuthRoleMenu> menuList,Integer menuId){
        for (AuthRoleMenu authRoleMenu: menuList) {
        if (authRoleMenu.getMenuId() == menuId)
            return  true;
        }
        return  false;
    }

}
