<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-header" style="padding:10px 20px;margin:-18px 0px 0px">
    <h3 style="color: #00b7ee">系统功能</h3>
    <div style="float: right">
        <button id="addBtn" class="btn btn-labeled btn-primary" onclick=addEvent()><span class="btn-label icon fa fa-plus"></span>添加菜单</button>
        <button id="edtBtn" class="btn btn-labeled btn-success" onclick=editEvent()><span class="btn-label icon fa fa-edit"></span>编辑菜单</button>
        <button id="deletBtn" class="btn btn-labeled btn-danger" onclick=deleteEvent()><span class="btn-label icon fa fa-times-circle "></span>删除菜单</button>
        <button id="detailBtn" class="btn btn-labeled btn-info" onclick=detailEvent()><span class="btn-label icon fa fa-search"></span>菜单详情</button>
    </div>
</div>
<div class="openAppGrid">
    <div id="MenuTree">
    </div>
</div>


<script type="text/javascript">
    var currentNode = null ;
    $("#MenuTree").jstree({
        "core": {
            'data': {
                'url': _urlPath+'/admin/menu/getMenus',
                'dataType': 'json',
                'data': function (node) { // 传给服务端点击的节点
                    return { clickId: node.id };
                },
                success: function () {

                }
            }
        }
    });

    $('#MenuTree').bind("activate_node.jstree", function (obj, e) {
        // 处理代码
        currentNode = e.node;
    });

    /**
     * 添加按钮点击事件
     */
    function addEvent() {
        javascript:showModal('添加菜单',  'admin/menu/addPage');
    }

    /**
     * 编辑按钮点击事件
     */
    function editEvent() {
        //判断当前是否有节点进行点击
        if (currentNode == null){
            $.Mod.Close("必须要选中一个节点","warning");
            return;
        }
        javascript:showModal('编辑菜单',  'admin/menu/updatePage?menuId='+currentNode.id);
    }
    /**
     * 删除按钮点击事件
     */
    function deleteEvent() {
        //判断当前是否有节点进行点击
        if (currentNode == null){
            $.Mod.Close("必须要选中一个节点","warning");
            return;
        }
        showCfm("确定删除该菜单",'admin/menu/delete?menuId='+currentNode.id)


    }
    /**
     * 详情按钮点击事件
     */
    function detailEvent() {
     //判断当前是否有节点进行点击
        if (currentNode == null){
            $.Mod.Close("必须要选中一个节点","warning");
            return;
        }
        javascript:showModal2('菜单详情',  'admin/menu/detailPage?menuId='+currentNode.id);
    }
</script>
