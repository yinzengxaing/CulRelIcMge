package com.xe.demo.service.sysmag;

import com.xe.demo.common.pojo.AjaxResult;
import com.xe.demo.common.utils.AppUtil;
import com.xe.demo.mapper.sysmag.AuthMenuMapper;
import com.xe.demo.model.po.sysmag.AuthMenu;
import com.xe.demo.model.vo.sysmag.TreeNode;
import com.xe.demo.model.vo.sysmag.TreeNodeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthMenuService {

    @Autowired
    private AuthMenuMapper authMenuMapper;

    public  List<AuthMenu>  getMenuList(AuthMenu authMenu){
        List<AuthMenu> list = authMenuMapper.getMenuList(authMenu);
        return list;
    }

    public  List<TreeNode> getMenus(){
        List<TreeNode> list = new ArrayList<TreeNode>();

        List<AuthMenu> menuList = authMenuMapper.getMenuList(new AuthMenu());
        if (menuList == null ){
            return  null;
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
                        state2.setSelected(false);
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
    public AjaxResult saveMenu(AuthMenu authMenu){
        if (authMenu.getMenuType() == 0){
            authMenu.setParentId(0);
        }
        authMenuMapper.saveMenu(authMenu);
        return AppUtil.returnMsg(1,"添加成功");
    }


    public AuthMenu getMenu(Integer menuId) {
        AuthMenu menu = authMenuMapper.getMenuById(menuId);
        return menu;
    }

    public AjaxResult deleteMenu(Integer menuId){
        authMenuMapper.deleteMenById(menuId);
        authMenuMapper.deleteMenByParentId(menuId);
        return AppUtil.returnMsg(1,"删除成功");
    }

    public  AjaxResult updatemenu(AuthMenu authMenu){
        authMenuMapper.updateMenu(authMenu);
        return AppUtil.returnMsg(1,"更新成功");
    }
}
