<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="submitForm" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="menuName"><font color="red">*</font>菜单名称：</label>
        <div class="col-sm-8">
            <input  value="${menu.menuName}" class="form-control" type="text" id="menuName" name="menuName" placeholder="请填写菜单名称" readonly/>
            <div id="validation-menuName" class="validate-error help-block"></div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label" for="menuType"><font color="red">*</font>菜单类别：</label>
        <div class="col-sm-8">
            <select  id="menuType" class="form-control" name="menuType" disabled="disabled">

                    <option value="0" <c:if test="${menu.menuType == 0}">selected="selected"</c:if>>父菜单</option>
                    <option value="1" <c:if test="${menu.menuType == 1}">selected="selected"</c:if>>子菜单项</option>
            </select>
            <div id="validation-menuType" class="validate-error help-block"></div>
        </div>
    </div>
    <div class="form-group" id="parentId-div" readonly>
        <label class="col-sm-3 control-label" for="parentId"><font color="red">*</font>父菜单：</label>
        <div class="col-sm-8">
            <select  id="parentId" class="form-control" name="parentId"disabled="disabled">
                <option value="">==请选择父菜单==</option>
                <c:forEach items="${parentList}" var="menu2">
                    <option value="${menu2.menuId}"<c:if test="${menu.parentId== menu2.menuId}">selected="selected"</c:if> >${menu2.menuName}</option>
                </c:forEach>
            </select>
            <div id="validation-parentId" class="validate-error help-block"></div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label" for="icon"><font color="red">*</font>图标：</label>
        <div class="col-sm-8">
            <input value="${menu.icon}" class="form-control" type="text" id="icon" name="icon" placeholder="请填写菜单图标" readonly/>
            <div id="validation-icon" class="validate-error help-block"></div>
        </div>
    </div>
    <div class="form-group" id="url-div">
        <label class="col-sm-3 control-label" for="url"><font color="red">*</font>菜单连接：</label>
        <div class="col-sm-8">
            <input value="${menu.url}" class="form-control" type="text" id="url" name="url" placeholder="请填菜单连接"  readonly/>
            <div id="validation-url" class="validate-error help-block"></div>
        </div>
    </div>
</form>

<script type="text/javascript">
    if ($("#menuType").val() == 1){
        $("#parentId-div").show();
        $("#url-div").show();
    }else{
        $("#parentId-div").hide();
        $("#url-div").hide();
    }
</script>
