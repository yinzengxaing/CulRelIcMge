package com.xe.demo.mapper.sysmag;

import org.apache.ibatis.annotations.Param;

import com.xe.demo.common.dao.MyMapper;
import com.xe.demo.model.po.sysmag.AuthRole;

public interface AuthRoleMapper extends MyMapper<AuthRole> {

	AuthRole queryRoleById(@Param("roleid")Integer roleid);
	
	AuthRole queryByRolename(@Param("rolename") String rolename);
}