<div class="col-side">
	<dl class="nav">
	<!-- {if "USER_MANAGE"|in_array:$smarty.session.datas.info.acls} -->
		<dt>客户管理</dt>
	<!-- {/if} -->
	<!-- {if "USER_MANAGE_LIST"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuUserlist' href="/admin/user/list?search=0">客户列表</a></dd>
	<!-- {/if} -->
	<!-- {if "USER_MANAGE_TOPAGENT"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuUsermanage' href="/admin/proxy/index?search=0">总代管理</a></dd>
	<!-- {/if} -->
       <!-- {if "USER_MANAGE_LEVELRECYCLE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuUserlevelrecycle' href="/admin/user/levelrecycle?type=list">一代回收</a></dd>
	<!-- {/if} -->
	<!-- {if "USER_EXCEPTION"|in_array:$smarty.session.datas.info.acls} -->
		<dt>客户异常</dt>
	<!-- {/if} -->
	<!-- {if "USER_EXCEPTION_FREEZEUSER"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuUserfreeze' href="/admin/user/freezeuserlist?search=0">冻结用户管理</a></dd>
	<!-- {/if} -->
		<!-- <dd><a href="#">异常用户名单</a></dd> -->
	<!-- {if "USER_EXCEPTION_APPEAL"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuUseraccomplaints' href="/admin/user/accomplaints?search=1">账号申诉管理</a></dd>
	<!-- {/if} -->
	<p class="copy">&copy; 2001-2012 591PH.<br />All right reserved.</p>
</div>