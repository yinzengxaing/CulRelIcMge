<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<h3 style="color: #00b7ee">系统功能</h3>
<div class="openAppGrid">
	<input id="roleId" value="${roleId}" hidden>
	<div id="MenuTree">
	</div>
</div>

<script type="text/javascript">
var roleId  =$("#roleId").val();
    $("#MenuTree").jstree({
        "core": {
            "themes": {
                "responsive": false
            },
            // so that create works
            "check_callback": true,
            'data': {
                'url': function (node)
                {
                    return _urlPath+"/admin/role/getMenus?roleId="+roleId;
                },
                'data': function (node)
                {
                    return {'parent': node.id};
                }
            }
        },
        "types": {
            "default": {
                "icon": "fa fa-folder icon-state-warning icon-lg"
            },
            "file": {
                "icon": "fa fa-file icon-state-warning icon-lg"
            }
        },
        "state": {"key": "tree_all"},
        //"plugins" : [ "dnd", "state", "types" ]
        'plugins': ["dnd", "checkbox", "types"]
    });
    submit=function () {

        var idArray = new Array();
        $("#MenuTree").find(".jstree-checked, .jstree-undetermined ").each(function () {
            idArray.push($(this).closest('li').attr("id"));
        });
        idArray = idArray.concat($.jstree.reference($("#MenuTree")).get_selected());
        console.log(idArray);




/*        var ref = $('#MenuTree').jstree(true);//获得整个树
            var menusStr  = ref.get_selected(false);//获得所有选中节点，返回值为数组
//由于jstree本身方法问题，其在获取所有选中节点时获取不到灰色方框复选框的选中状态，所以通过灰色方框的class属性jstree-undetermined获取其对应的节点id
/!*          var menusStr = sel.toString();*!/
            console.log(menusStr)

		var s = null;
           $(".jstree-undetermined").each(function(){
               s = s + '-' + $(this).parent().parent().attr('id');
            });
            console.log(s)*/
        var  data = {
              "menusStr":idArray,
			  "roleId":roleId
		  }
        submitForm("admin/role/updateRoleMenu",data);
    }
function submitForm(url, data) {
    $.ajax({
        url: _urlPath + url,
        data: data,
        type: "post",
        dataType:"json",
        traditional:true,
        success: function (req){
            if (req.retcode == 1) {

                $("#MenuTree").jstree(true).refresh();
                $.Mod.Close(req.retmsg);
                closeModal();
            } else {
                modalErr(req.retmsg);
            }
        }
    });
}
</script>