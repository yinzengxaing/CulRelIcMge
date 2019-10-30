package com.xe.demo.mapper.sysmag;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xe.demo.common.dao.MyMapper;
import com.xe.demo.model.po.sysmag.AuthUser;

public interface AuthUserMapper extends MyMapper<AuthUser> {

	AuthUser queryByUsername(@Param("username") String username);

	List<AuthUser> queryList(@Param("user")AuthUser user);

	List<Integer> queryRoleUids(@Param("roleid")int roleid);
}