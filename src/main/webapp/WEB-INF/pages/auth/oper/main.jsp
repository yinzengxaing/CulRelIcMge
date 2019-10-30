<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="page-header" style="padding:10px 20px;margin:-18px 0px 0px">
	<div class="col-md-3" style="text-align: left;padding-bottom: unset">
		<button id="addBtn" class="btn btn-labeled btn-primary" onclick="javascript:showModal('添加权限', 'admin/oper/addPage');"><span class="btn-label icon fa fa-plus"></span>添加权限</button>
	</div>
</div>

<div class="openAppGrid">
	<div id="openAppGrid"></div>
</div>

<script type="text/javascript">
    $(function (){
        $("#openAppGrid").sgrid({
            columns:[
                {field:"opid",text:"权限ID", sortColumn:"opid"},
                {field:"opcode", text:"权限码"},
                {field:"opname", text:"权限名称"},
                {field:"ophref", text:"链接地址"},
                {field:"opseq", text:"排序"}
            ],
            cls: "",
            url: _urlPath + "admin/oper/queryPage",
            sort:"id",
            order:"desc",
            pagination:true,
            onLoad:function(){
                $(".add-tooltip").tooltip();
            }
        });
    });
</script>