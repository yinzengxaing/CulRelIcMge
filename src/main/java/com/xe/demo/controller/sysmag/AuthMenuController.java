package com.xe.demo.controller.sysmag;

import com.xe.demo.common.annotation.ControllerLog;
import com.xe.demo.common.pojo.AjaxResult;
import com.xe.demo.common.utils.AppUtil;
import com.xe.demo.model.po.sysmag.AuthMenu;
import com.xe.demo.model.vo.sysmag.TreeNode;
import com.xe.demo.service.sysmag.AuthMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/menu/")
public class AuthMenuController  extends  BaseController{

    @Autowired
    private AuthMenuService authMenuService;

    @RequestMapping("mianPage")
    public String getAuthoutList(Map<String,Object> map,AuthMenu authMenu){
        List<AuthMenu> menuList = authMenuService.getMenuList(authMenu);
        map.put("menuList",menuList);
        return  "auth/menu/main";
    }

    @RequestMapping("getMenus")
    @ResponseBody
    public List<TreeNode>  getMenus(){
        List<TreeNode> menus = authMenuService.getMenus();
        return  menus;
    }
    @ControllerLog("添加菜单")
    @RequestMapping("add")
    @ResponseBody
    public AjaxResult addMenu(AuthMenu authMenu){
        AjaxResult ajaxResult = authMenuService.saveMenu(authMenu);
        return ajaxResult;
    }

    @RequestMapping("delete")
    @ResponseBody
    public AjaxResult deleteMenu(Integer menuId){

        AjaxResult ajaxResult = authMenuService.deleteMenu(menuId);
        return ajaxResult;
    }
    @RequestMapping("update")
    @ResponseBody
    public AjaxResult updateMenu(AuthMenu menu){
        AjaxResult ajaxResult = authMenuService.updatemenu(menu);
        return ajaxResult;
    }
    @RequestMapping("getMenuList")
    @ResponseBody
    public AjaxResult getMenuList(AuthMenu authMenu){
        List<AuthMenu> menuList = authMenuService.getMenuList(authMenu);
        return  AppUtil.returnList("菜单列表",menuList);
    }

    @RequestMapping("updatePage")
    public String toUpdatePage (Map<String,Object> map, Integer menuId){
        AuthMenu menu = authMenuService.getMenu(menuId);
        AuthMenu authMenu = new AuthMenu();
        authMenu.setMenuType(0);
        authMenu.setParentId(0);
        List<AuthMenu> parentList = authMenuService.getMenuList(authMenu);
        map.put("parentList",parentList);
        map.put("menu",menu);
        return  "auth/menu/update";
    }

    @RequestMapping("addPage")
    public String toAddPage (Map<String,Object> map){
        AuthMenu authMenu = new AuthMenu();
        authMenu.setMenuType(0);
        authMenu.setParentId(0);
        List<AuthMenu> parentList = authMenuService.getMenuList(authMenu);
        map.put("parentList",parentList);
        return  "auth/menu/add";
    }

    @RequestMapping("detailPage")
    public String toDetailPage (Map<String,Object> map, Integer menuId){
        AuthMenu menu = authMenuService.getMenu(menuId);
        AuthMenu authMenu = new AuthMenu();
        authMenu.setMenuType(0);
        authMenu.setParentId(0);
        List<AuthMenu> parentList = authMenuService.getMenuList(authMenu);
        map.put("parentList",parentList);
        map.put("menu",menu);
        return  "auth/menu/detail";
    }

}
