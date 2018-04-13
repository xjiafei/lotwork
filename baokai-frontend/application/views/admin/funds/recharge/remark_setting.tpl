	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/index">资金中心</a> &gt; <span id="titleName">唯一附言管理</span></div></div>
			<div class="col-content">
				<div class="col-main">
						<div class="main">
						<div class="ui-tab-title clearfix">
							<ul>
								<!-- {if "FUND_RECHARGE_REMARK_MANAGER"|in_array:$smarty.session.datas.info.acls} -->
									<li>唯一附言管理</li>
								<!-- {/if} -->
								<!-- {if "FUND_RECHARGE_REMARK_RECYCLE"|in_array:$smarty.session.datas.info.acls} -->
									<li>唯一附言回收管理</li>
								<!-- {/if} -->
								<!-- {if "FUND_RECHARGE_REMARK_SETTING"|in_array:$smarty.session.datas.info.acls} -->
									<li class="current">配置管理</li>
								<!-- {/if} -->
							</ul>
						</div>
						<div class="ui-area">
							<table class="table table-info table-function info-list">
								<tbody>
									<tr>
										<td class="control-desc">修改绑定与用户解除后再次绑定</td>
										<td class="control-panl">每 <input id="J-edit-days" class="input w-2" type="text" value="{$days}" /> 天，可修改一次唯一附言；（用户解绑后可再次绑定时间）</td>
									</tr>
									<!-- <tr>
										<td>可用用户类型</td>
										<td class="info-area">
											按用户类型：
											<label for="all-user"><input id="all-user" name="user-type" class="user-type checkbox" type="checkbox"> 全部</label>
											<label for="vip-user"><input id="vip-user" name="user-type" class="user-type checkbox" type="checkbox"> VIP</label>
											<label for="normal-user"><input id="normal-user" name="user-type" class="user-type checkbox" type="checkbox"> 普通用户</label>
											<div>按总代： </div>
											<ul data-type="none" id="zongdai-show" class="porxyname"><li>无</li></ul>
											<div class="clearfix"></div>
											<input id="J-input-name" class="input w-2" type="text" value="" /> <a id="J-add-proxy" href="javascript:void(0);" class="btn clearfix">添加总代<b class="btn-inner"></b></a>
											<div class="clearfix"></div>
											<div>导入Excel： </div>
											<ul data-type="none" id="excel-show" class="porxyname"><li>无</li></ul>
											<div class="clearfix input-area"></div>
										</td>
									</tr> -->
									<!-- {if "FUND_RECHARGE_REMARK_SETTING_SAVE"|in_array:$smarty.session.datas.info.acls} -->
									<tr>
										<td colspan="2">
											<a href="javascript:void(0);" id="save_btn" class="btn w-1 btn-important clearfix">保存<b class="btn-inner"></b></a>
											<a href="javascript:void(0);" class="btn clearfix">返回<b class="btn-inner"></b></a>
										</td>
									</tr>
									<!-- {/if} -->
								</tbody>
							</table>
							<!-- {if "FUND_RECHARGE_REMARK_SETTING_IMPORT"|in_array:$smarty.session.datas.info.acls} -->
							<!-- <form id="form1" name="form1" enctype="multipart/form-data" method="post" action="/admin/Remark/index?parma=sv10" target="check_file_frame">
								<input name="file" type="file" id="file" size="40" hidefocus="true" value="导入Excel" class="btn" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;position:absolute;bottom: 85px; left: 335px; width:107px; height:30px;z-index:1;background:#000" />
								<a style="outline:none;position:absolute;bottom: 85px; left:335px; width:107px; height:30px;z-index:0" href="javascript:void(0);" class="btn clearfix">导入Excel<b class="btn-inner"></b></a>
								<a style="outline:none;position:absolute;bottom: 80px; left:490px; width:107px; height:30px;z-index:0" href="#" class="">模板下载</a>
								<input type="reset" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;width:0px; height:0px;z-index:1;background:#000" />
								<iframe src="/admin/Remark/index?parma=sv10" name="check_file_frame" style="display:none;"></iframe>
							</form> -->
							<!-- {/if} -->
						</div>
					   </div>
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
<script src="{$path_js}/js/ajaxfileupload.js" type="text/javascript"></script>
{literal}
<script>
(function($){
	var message = new phoenix.Message;
	var dateDom = $('#J-edit-days');
	var uploadButton = $('#file');
	
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRemark');
	
	$('.ui-tab-title ul li').click(function(){
		var indexs = $(this).index();
		if($(this).attr('class') !='current'){
			window.location.href="/admin/Remark/index?parma=sv"+(parseInt(indexs)+1);
		}
	});

	dateDom.on('input propertychange', function() {
		var v = this.value;
		this.value= v = v.replace(/[^\d|^\.]/g, '');
		if(v.length>0){
			if(v.substr(0,1) =='0'){
				v=v.substr(1,v.length);
			}
			this.value = v.replace(/[^\d]/g, '');
		}	
	});


	var proxyNameDom = $('#J-input-name');
	var addBtnDom = $('#J-add-proxy');
	var zongdaiDom = $('#zongdai-show');
	var excelDom = $('#excel-show');

	//绑定用户上传按钮
	uploadButton.bind('change', function(){
		var form = $(this).parent();

		checkFile(this, form);
	});

	//检测上传
	checkFile= function(dom, form){
		var result = dom.value,
			fileext=result.substring(result.lastIndexOf("."),result.length),
			fileext=fileext.toLowerCase();

		//form[0].submit();
        $.ajaxFileUpload
           (
             {
                  url:'/admin/Remark/index?parma=sv10', //你处理上传文件的服务端
                  secureuri:false,
                  fileElementId:'file',
                  dataType: 'json',
                  success: function (data) {
	                     if(data['isSuccess'] == '1'){
		                    	 message.show({
		         					mask: true,
		         					content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+data['msg']+'</h4></div></div>',
		         					confirmIsShow: true,
		         					confirmFun: function() {
		         						this.hide();
		         						window.location.reload();
		         					}
		         				});
	                     } else {
		                    	 message.show({
		         					mask: true,
		         					content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+data['msg']+'</h4></div></div>',
		         					confirmIsShow: true,
		         					confirmFun: function() {
		         						this.hide();
		         						window.location.reload();
		         					}
		         				});
	                     }
                   }
             }
          )
	}
	
	getFile= function(result) {
		var data = result['data'];

		for (var i = 0; i < data.length; i++) {
			var content = data[i];
			var tag = excelDom.attr('data-type');

			if(tag == 'none') {
				excelDom.html('<li data-user="' + content + '">' + content + '<input type="hidden" value="'+content+'" name="user-name" /><i class="close" title="删除">×</i></li>');
				excelDom.attr('data-type', 'fill');
			}else{
				excelDom.html(excelDom.html() +' '+ '<li>' + content + '<input type="hidden" value="'+content+'" name="user-name" /><i class="close" title="删除">×</i></li>');	
			}
		};
	}

	//关闭总代
	$('.info-area').on('click', '.close', function() {
		var dom = $(this).parent();
		var parentDom = $(this).parent().parent();

		dom.remove();

		if($.trim(parentDom.html()) == '') {
			parentDom.text('无').attr('data-type', 'none');
		}

	});


	addBtnDom.on('click', function() {
		var content = $.trim(proxyNameDom.val());

		if(content) {

			$.ajax({
				url: '/admin/Remark/index?parma=sv10',
				type: 'POST',
				dataType: 'json',
				data: {name: content},
				success: function(r) {

					if(r['isSuccess'] == 1) {
						
						if(r['type'] == 1) {
							var tag = zongdaiDom.attr('data-type');

							if(tag == 'none') {
								zongdaiDom.html('<li data-user="' + content + '">' + content + '<input type="hidden" value="'+content+'" name="user-name" /><i class="close" title="删除">×</i></li>');
								zongdaiDom.attr('data-type', 'fill');
							}else{
								zongdaiDom.html(zongdaiDom.html() +' '+ '<li>' + content + '<input type="hidden" value="'+content+'" name="user-name" /><i class="close" title="删除">×</i></li>');	
							}

							proxyNameDom.val('');
						}else{
							message.show({
								mask: true,
								content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+r['msg']+'</h4></div></div>',
								cancelIsShow: true,
								cancelFun: function() {
									this.hide();
									proxyNameDom.val('').focus();
								}
							});
						}
					}
				}
			});
		}else {
			message.show({
				mask: true,
				content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">代理名称不能为空</h4></div></div>',
				cancelIsShow: true,
				cancelFun: function() {
					this.hide();
					proxyNameDom.focus();
				}
			});
		}
	});
	
	$('#save_btn').on('click', function() {
		var days = dateDom.val();
		$.ajax({
			url: '/admin/remark/index?parma=sv8',
			type: 'POST',
			dataType: 'json',
			data: {days: days},
			success: function(r) {
				message.show({
					mask: true,
					content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+r['msg']+'</h4></div></div>',
					confirmIsShow: true,
					confirmFun: function() {
						this.hide();
						window.location.reload();
					}
				});
				
			}
		});
	});


})(jQuery);
</script>
{/literal}
</body>
</html>