<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="submitForm" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="opcode"><font color="red">*</font>权限码：</label>
        <div class="col-sm-8">
            <input class="form-control" type="text" id="opcode" name="opcode" placeholder="请填写权限码"/>
            <div id="validation-opcode" class="validate-error help-block"></div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label" for="opname"><font color="red">*</font>权限名称：</label>
        <div class="col-sm-8">
            <input class="form-control" type="text" id="opname" name="opname" placeholder="请填写权限名称" />
            <div id="validation-opname" class="validate-error help-block"></div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="ophref"><font color="red">*</font>操作链接：</label>
        <div class="col-sm-8">
            <input class="form-control" type="text" id="ophref" name="ophref" placeholder="请填写操作连接" />
            <div id="validation-ophref" class="validate-error help-block"></div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="opseq"><font color="red">*</font>排序：</label>
        <div class="col-sm-8">
            <input class="form-control" type="text" id="opseq" name="opseq" placeholder="请填写排序" />
            <div id="validation-opseq" class="validate-error help-block"></div>
        </div>
    </div>
</form>
<script type="text/javascript">
    submit = function(){
        frmValidate();
        var data = $("#submitForm").serialize();
        ajaxRequest(data, "admin/oper/add");


    }

</script>