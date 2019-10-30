<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="main-menu" role="navigation">
	<div id="main-menu-inner">
		<div class="menu-content top" id="menu-content-demo">
		    <div>

		        <div class="text-bg"><span class="text-semibold">${loginUser.username}</span></div>
		        <img src="${ctx}/assets/images/user-head-default.png" alt="" class="">
		        <div class="btn-group">
		            <a href="javascript:showModal('修改密码', 'admin/user/updatePasswdPage?id=${loginUser.id}');" data-original-title="修改密码" class="btn btn-xs btn-primary btn-outline dark add-tooltip"><i class="fa fa-cog"></i></a>
		            <a href="javascript:logout()" data-original-title="退出" class="btn btn-xs btn-danger btn-outline dark add-tooltip"><i class="fa fa-power-off"></i></a>
		            <a href="javascript:index()" data-original-title="Druid监控" class="btn btn-xs btn-danger btn-outline dark add-tooltip"><i class="fa fa-home fa-fw"></i></a>
		        </div>
		        <a href="#" class="close">&times;</a>
		    </div>
		</div>
		<ul class="navigation">
			<c:forEach items="${loginUserMenu}" var="menu">
				<li class="mm-dropdown">
					<a href="#"><i class="${menu.parentNode.icon}"></i><span class="mm-text">${menu.parentNode.menuName}</span></a>
					<ul>
				<c:forEach items="${menu.childsNode}" var="menu2">
					<li >
						<a tabindex="-1" href="javascript:goPage('${menu2.url}')"><i class="${menu2.icon}"></i><span class="mm-text">${menu2.menuName}</span></a>
					</li>
				</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<script type=""></script>
