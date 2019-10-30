package com.xe.demo.mapper.sysmag;

import java.util.List;

import com.xe.demo.common.dao.MyMapper;
import com.xe.demo.model.po.sysmag.AuthRoleOperation;

public interface AuthRoleOperationMapper extends MyMapper<AuthRoleOperation> {

	void batchInsert(List<AuthRoleOperation> list);

	void delRoleOpers(List<AuthRoleOperation> list);
}