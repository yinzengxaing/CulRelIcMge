package com.xe.demo.controller.sysmag;

import com.xe.demo.common.pojo.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xe.demo.common.annotation.Authority;
import com.xe.demo.common.annotation.ControllerLog;
import com.xe.demo.common.pojo.PageAjax;
import com.xe.demo.model.po.sysmag.AuthOperation;
import com.xe.demo.service.sysmag.OperationService;

@Controller
@RequestMapping("/admin/oper/")
public class OperationController extends BaseController {

	@Autowired
	private OperationService opService;

	@Authority(opCode = "0103", opName = "权限管理界面")
	@RequestMapping("mainPage")
	public String mainPage(){
		return "auth/oper/main";
	}

	@ControllerLog("查询权限列表")
    @RequestMapping("queryPage")
    @ResponseBody
    @Authority(opCode = "0103", opName = "查询权限列表")
    public PageAjax<AuthOperation> queryPage(PageAjax<AuthOperation> page, AuthOperation oper) {
        return opService.queryPage(page, oper);
    }
	@RequestMapping("addPage")
	public String queryPage() {
		return"auth/oper/add";
	}


	@RequestMapping("add")
    @Authority(opCode = "040101", opName = "查询权限列表")
	public AjaxResult add(AuthOperation authOperation) {
        AjaxResult result = opService.save(authOperation);
        return  result;
    }


}
