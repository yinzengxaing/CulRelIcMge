package com.xe.demo.mapper.sysmag;

import com.xe.demo.common.dao.MyMapper;
import com.xe.demo.model.po.sysmag.AuthOperation;

import java.util.List;

public interface AuthOperationMapper extends MyMapper<AuthOperation> {

    List<AuthOperation> queryAllOpers();

    AuthOperation queryOperation(AuthOperation authOperation);

    void saveOper(AuthOperation authOperation);

    void batchSaveOper(List<AuthOperation> authOperations);

    void batchUpdateOper(AuthOperation authOperation);

    void batchDelOper(List<Integer> list);

    Integer queryOperCount(AuthOperation authOperation);

    void batchSaveGroupOper(List<AuthOperation>list);





}