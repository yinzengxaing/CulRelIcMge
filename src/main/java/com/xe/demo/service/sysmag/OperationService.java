package com.xe.demo.service.sysmag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xe.demo.common.pojo.AjaxResult;
import com.xe.demo.common.utils.AppUtil;
import com.xe.demo.mapper.sysmag.AuthOperationMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xe.demo.common.Constant;
import com.xe.demo.common.MapperKey;
import com.xe.demo.common.annotation.Authority;
import com.xe.demo.common.utils.AnnotationUtil;
import com.xe.demo.common.utils.JsonConvertUtil;
import com.xe.demo.common.utils.MD5Util;
import com.xe.demo.model.po.sysmag.AuthOperation;
import com.xe.demo.model.po.sysmag.AuthRole;
import com.xe.demo.model.po.sysmag.AuthUser;

/**
 * 权限管理业务层
 * @author CZH
 */
@Service
public class OperationService extends AbstratService<AuthOperation> {
	// 扫描包
	private static final String PERMISS_PACKAGE = "com.xe.demo.controller";
	private static final String ADMIN = "admin";


	@Autowired
	private AuthOperationMapper authOperationMapper;

	//添加一个权限
	public AjaxResult save(AuthOperation authOperation){
		authOperationMapper.saveOper(authOperation);
		return AppUtil.returnMsg(1,"添加成功");
	}


	/**
	 * 初始化权限
	 */
	@Transactional // 开启事务
	@SuppressWarnings("unchecked")
	public void initAuthority() {
		List<AuthOperation> operationList = (List<AuthOperation>) dao.findForList(MapperKey.OPERATION_queryAllOpers, null);
		List<String> opIdList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(operationList)) {
			for (AuthOperation operation : operationList) {
				opIdList.add(operation.getOpid() + "");
			}
		}

		// 要保存的列表
		List<AuthOperation> saveList = new ArrayList<AuthOperation>();
		// 要修改的列表
		List<AuthOperation> updateList = new ArrayList<AuthOperation>();

		// 同步json文件(左侧菜单栏权限)里面的权限
		List<AuthOperation> operationJsonList = JsonConvertUtil.json2List("operations.json", AuthOperation.class);
		if (operationJsonList != null) {
			for (AuthOperation operation : operationJsonList) {
				AuthOperation $operation = dao.findForObject(MapperKey.OPERATION_queryOperation, operation);
				if ($operation != null) {
					$operation.setOpname(operation.getOpname());
					$operation.setOphref(operation.getOphref());
					$operation.setOpseq(operation.getOpseq());
					updateList.add(operation);
					// 剔除已经更新的数据
					opIdList.remove($operation.getOpid() + "");
				} else {
					$operation = new AuthOperation();
					$operation.setOpcode(operation.getOpcode());
					$operation.setOpname(operation.getOpname());
					$operation.setOphref(operation.getOphref());
					$operation.setOpseq(operation.getOpseq());
					saveList.add(operation);
				}
			}
		}
		
		// 同步注解里面的权限
		Map<String, Authority> permissionMap = AnnotationUtil.getMethodAnnotaionByPackage(PERMISS_PACKAGE, Authority.class);
		Set<Map.Entry<String, Authority>> entrySet = permissionMap.entrySet();
		for (Map.Entry<String, Authority> entry : entrySet) {
			Authority permission = entry.getValue();
			String href = entry.getKey();
			String code = permission.opCode();
			if (StringUtils.isEmpty(code)) {
				continue;
			}
			AuthOperation $operation = new AuthOperation();
			$operation.setOpcode(code);
			$operation.setOphref(href);
			AuthOperation operation = dao.findForObject(MapperKey.OPERATION_queryOperation, $operation);
			if (operation != null) {
				operation.setOpname(permission.opName());
				operation.setOphref(href);
				operation.setOpseq(permission.opSeq());
				updateList.add(operation);
				// 剔除已经更新的数据
				opIdList.remove(operation.getOpid() + "");
			} else {
				operation = new AuthOperation();
				operation.setOpname(permission.opName());
				operation.setOphref(href);
				operation.setOpseq(permission.opSeq());
				operation.setOpcode(code);
				saveList.add(operation);
			}
		}

		// 批量保存新增的权限
		if (CollectionUtils.isNotEmpty(saveList)) {
			dao.batchSave(MapperKey.OPERATION_batchSaveOper, saveList);
		}
		// 批量更新修改的权限
		if (CollectionUtils.isNotEmpty(updateList)) {
			dao.batchUpdate(MapperKey.OPERATION_batchUpdateOper, updateList);
		}
		// 删除的权限值
		if (CollectionUtils.isNotEmpty(opIdList)) {
			dao.batchDelete(MapperKey.OPERATION_batchDelOper, opIdList);
		}

		AuthRole role = dao.findForObject(MapperKey.ROLE_queryByRolename, Constant.ROLE_ANONYMOUS);
		// 创建匿名用户组
		
		if (null == role) {
			role = new AuthRole();
			role.setRolename(Constant.ROLE_ANONYMOUS);
			role.setCname("匿名用户组");
			dao.save(MapperKey.ROLE_addAdmin, role);
		}
		role = dao.findForObject(MapperKey.ROLE_queryByRolename, ADMIN);
		
		// 创建超级管理员
		if (null == role) {
			role = new AuthRole();
			role.setRolename(ADMIN);
			role.setCname("超级管理员");
			dao.save(MapperKey.ROLE_addAdmin, role);
		}
		int roleid = role.getRoleid();
		AuthUser user = dao.findForObject(MapperKey.USER_queryByUsername, ADMIN);
		// 创建超级用户
		if (null == user) {
			user = new AuthUser();
			user.setUsername(ADMIN);
			try {
				String password = MD5Util.encrypt(ADMIN);
				user.setPassword(password);
			} catch (Exception e) {
			}
			user.setRoleid(roleid);
			dao.save(MapperKey.USER_addAdmin, user);
		}
		// 更新超级管理员权限
		dao.save(MapperKey.OPERATION_bindOpers, roleid);
//		operationList = (List<AuthOperation>) dao.findForList(MapperKey.OPERATION_queryAllOpers, null);
//		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
//		Map<String, Integer> map = null;
//		for (AuthOperation operation : operationList) {
//			map = new HashMap<String, Integer>();
//			map.put("roleid", roleid);
//			map.put("opid", operation.getOpid());
//			int count = dao.findForObject(MapperKey.OPERATION_queryOperCount, map);
//			if (count <= 0) {
//				list.add(map);
//			}
//		}
//		if (CollectionUtils.isNotEmpty(list)) {
//			dao.batchSave(MapperKey.OPERATION_batchSaveGroupOper, list);
//		}
	}

}
