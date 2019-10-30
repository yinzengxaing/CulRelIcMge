package com.xe.demo.mapper.sysmag;

import com.xe.demo.common.dao.MyMapper;
import com.xe.demo.model.po.sysmag.AuthMenu;

import java.util.List;

public interface AuthMenuMapper extends MyMapper<AuthMenu> {

     List<AuthMenu> getMenuList(AuthMenu authMenu);

     void saveMenu(AuthMenu authMenu);

     void updateMenu(AuthMenu authMenu);

     AuthMenu getMenuById(Integer menuId);

     void deleteMenById(Integer menuId);

     void deleteMenByParentId(Integer parentId);





}
