package com.xe.demo.service.sysmag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xe.demo.mapper.sysmag.AuthMenuMapper;
import com.xe.demo.mapper.sysmag.AuthRoleMenuMapper;
import com.xe.demo.model.po.sysmag.AuthMenu;
import com.xe.demo.model.po.sysmag.AuthRole;
import com.xe.demo.model.po.sysmag.AuthRoleMenu;
import com.xe.demo.model.po.sysmag.AuthUser;
import com.xe.demo.model.vo.sysmag.MenuNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xe.demo.common.Constant;
import com.xe.demo.common.annotation.ServiceLog;
import com.xe.demo.common.pojo.AjaxResult;
import com.xe.demo.common.pojo.Identity;
import com.xe.demo.common.pojo.ParamData;
import com.xe.demo.common.support.DataCache;
import com.xe.demo.common.utils.AppUtil;
import com.xe.demo.common.utils.CookieUtil;
import com.xe.demo.common.utils.DateUtil;
import com.xe.demo.common.utils.IPUtil;
import com.xe.demo.common.utils.MD5Util;
import com.xe.demo.mapper.sysmag.AuthRoleMapper;
import com.xe.demo.mapper.sysmag.AuthUserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录管理业务层
 * @author CZH
 */
@Service
public class LoginService extends AbstratService<AuthUser> {
	@Autowired
	private AuthUserMapper userMapper;
	@Autowired
	private AuthRoleMapper roleMapper;
	@Autowired
	private AuthRoleMenuMapper authRoleMenuMapper;
	@Autowired
	private AuthMenuMapper authMenuMapper;

	@Autowired
	private DataCache dataCache;

	@ServiceLog("登录")
	public AjaxResult login(HttpServletRequest request, HttpServletResponse response) {
		String verifyCode = (String) request.getSession().getAttribute(Constant.VERIFY_CODE);
		String result = null;
		ParamData params = new ParamData();
		String vcode = params.getString("vcode");
		if (params.containsKey("vcode") && (StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode))) {
			result = "验证码错误";
		}else{
			String username = params.getString("username");
			String loginIp = params.getString("loginIp");
			String key = username + loginIp + Constant.LOGIN_ERROR_TIMES;
			AuthUser user = userMapper.queryByUsername(username);
			
			if (user == null || !user.getPassword().equals(params.getString("password"))) {
				int errTimes = dataCache.getInt(key);
				//记录密码错误次数,达到3次则需要输出验证码
				dataCache.setValue(key, errTimes += 1);
				result = "用户名或密码错误|" + errTimes;
			}else if(Constant.ROLE_ANONYMOUS.equals(user.getRole().getRolename())){
				result = "用户未分组,无法登录";
			}else{
				// 更新登录IP和登录时间
				user.setLoginip(loginIp);
				user.setLogintime(DateUtil.getCurDateTime());
				userMapper.updateByPrimaryKeySelective(user);
				
				Identity identity = new Identity();
				AuthRole role = roleMapper.queryRoleById(user.getRoleid());

				//查询当前角色的所的菜单
				List<MenuNode> roleMenuList = getRoleMenu(user.getRoleid());
				identity.setMenuList(roleMenuList);
				request.getSession().setAttribute("loginUserMenu",roleMenuList);

				identity.setOperationList(role.getOperations());
				identity.setLoginUser(user);
				identity.setLoginIp(loginIp);
				String sessionId = getSessionId(username, identity.getLoginIp());
				identity.setSessionId(sessionId);
				dataCache.setValue(username + loginIp, identity);
				dataCache.setValue(sessionId, username);
				dataCache.remove(key);
				CookieUtil.set(Constant.SESSION_IDENTITY_KEY, sessionId, response);
			}
		}
		return AppUtil.returnObj(result);
	}

	@ServiceLog("退出")
	public AjaxResult logout(HttpServletResponse response, HttpServletRequest request) {
		String sessionId = CookieUtil.get(Constant.SESSION_IDENTITY_KEY, request);

		if (StringUtils.isNotEmpty(sessionId)) {
			dataCache.remove(sessionId);
			String userName = (String) dataCache.getValue(sessionId);
			if (StringUtils.isNotEmpty(userName)) {
				dataCache.remove(userName + IPUtil.getIpAdd(request));
			}
			CookieUtil.delete(Constant.SESSION_IDENTITY_KEY, request, response);
		}
		return AppUtil.returnObj(null);
	}

	private String getSessionId(String userName, String ip) {
		String str = userName + "_" + System.currentTimeMillis() + "_" + ip;
		try {
			return MD5Util.encrypt(str);
		} catch (Exception e) {
			return "生成token错误";
		}
	}

	/**
	 * 获取当前角色的菜单列表
	 * @param roleId
	 * @return
	 */
	private List<MenuNode> getRoleMenu (int roleId){

		List<MenuNode> menuNodeList = new ArrayList<>();
		//获取当前角色下的所有菜单
		List<AuthRoleMenu> roleMenuList1 = authRoleMenuMapper.getRoleMenuList(roleId);

		List<AuthMenu> menuList = authMenuMapper.getMenuList(new AuthMenu());
		if (menuList == null ){
			return null;
		}
		//菜单不为空时
		for (AuthMenu menu:menuList) {
			if (menu.getMenuType() == 0) { //父节点
				MenuNode menuNode = new MenuNode();
				if (isMenuSelect(roleMenuList1, menu.getMenuId())) {
					menuNode.setParentNode(menu);
					//查询当前父id下的所有的子节点
					AuthMenu authMenu = new AuthMenu();
					authMenu.setParentId(menu.getMenuId());
					authMenu.setMenuType(1);
					//获取该父节点下的子节点
					List<AuthMenu> menuList1 = authMenuMapper.getMenuList(authMenu);
					if (menuList1 != null) {
						List<AuthMenu> list2 = new ArrayList<>();
						for (AuthMenu amenu2 : menuList1) {
							if (isMenuSelect(roleMenuList1, amenu2.getMenuId())) {
								list2.add(amenu2);
							}
						}
						menuNode.setChildsNode(list2);
					}
					menuNodeList.add(menuNode);
				}
			}
		}
		return menuNodeList;

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
