	(function($){
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(14)').attr("class","current");
	
	var sIndex=$("#statusBK").val();
	var sIndexConfig=$("#configBK").val();
	if(sIndex == "-1" || sIndex == "")
	{
	    $("#status").val(-1);
	}else
	{
		$("#status").val(sIndex);
	}
	if(sIndexConfig == "-1" || sIndexConfig == "")
	{
		$("#config").val(-1);
	}else
	{
		$("#config").val(sIndexConfig);
	}
	var inputStart = $('#timestart'),
		inputEnd = $('#timeend');
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
	});


	
   })(jQuery);


	$(function(){

		// ajax 请求url
		var ajaxurl = 'updateActivitySheepHongBao';
		
		// 通过/拒绝
		var minWindow = new phoenix.MiniWindow({ cls: 'pop' }), 
		    msg = new phoenix.Message(),
			mask = phoenix.Mask.getInstance();

		minWindow.addEvent('beforeShow', function() {
			mask.show();
		});
		minWindow.addEvent('afterHide', function() {
			mask.hide();
		});
		$('[data-action]').on('click', function(){
			var action = $(this).data('action');
			// console.log(action);
			if( !action ) return false;

			var $tr = $(this).parents('tr:eq(0)'),
				username = $tr.find('[data-username]').data('username'),
				id = $tr.data('id');
			if( action == 'pass' ){
				// 请开启以下注释，并去掉上一行代码
				msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">您确认通过审核？</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					$.ajax({
						url:ajaxurl,				
						dataType:'json',
						method:'post',
						data:{ids2: id, updateType: 3},
						success:function(data){
							//红包直接返回成功
							msg.hide();
						    location.reload();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
						    msg.hide();
						    location.reload();
						},
						complete: function()
						{
							msg.hide();
						    location.reload();
						}								
					});
				},
				cancelIsShow: 'true',
				cancelText: '取消',
				cancelFun: function(){
					msg.hide();
				}
				});
			}else if( action == 'refuse' ){
				minWindow.setTitle('拒绝');
				minWindow.setContent(
					'<div class="pop-title text-left"><strong>用户名：</strong>' + username + '</div>' +
					'<div class="pop-title text-left">' +
						'<p><strong>拒绝原因：</strong></p>' +
						'<textarea class="textarea w-8" id="reason" name="" placeholder="请输入拒绝原因"></textarea>' + 
					'</div>' + 
					'<div class="pop-btn">' +
						'<a href="javascript:void(0);" class="btn btn-important" id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a>' +
						'<a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a>' +
					'</div>'
				);
				minWindow.show();

				$('#J-button-pop-confirm').on('click', function(e) {
					e.preventDefault();
					//minWindow.hide();
					
					var reason = $('#reason').val();
		
					// 请开启以下注释，并去掉上一行代码
					$.post(ajaxurl, {ids2: id, updateType: 4,verifyReason:reason}).done(function(resp){
						
						minWindow.hide();
						alert('操作成功！');
						location.reload();               
						
					}).fail(function(xhr, textStatus, errorThrown){
						alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
					});

				});
				$('#J-button-pop-cancel').on('click', function() {
					minWindow.hide();
				});
			}
		});

		// 全选按钮
		var $checkboxs = $('tbody input[type="checkbox"]'),
			$selectAllCheckbox = $('#J-select-all-item');

		$selectAllCheckbox.on('click', function(){
			var checked = this.checked;
			$checkboxs.prop('checked', checked);
		});

		$checkboxs.on('click', function(){
			var $allCheckeds = $checkboxs.filter(':checked');
			$selectAllCheckbox.prop('checked', $allCheckeds.length == $checkboxs.length);
		});

		$('[data-pass-or-refuse-buttons] a').on('click', function(){
			var type = $(this).data('button-type');
			// console.log(type);
			var $allCheckeds = $checkboxs.filter(':checked');
			if( !$allCheckeds.length || (type != 'pass' && type != 'refuse') ){
				return false;
			}
			var ids ="";
			$allCheckeds.each(function(){
				var $tr = $(this).parents('tr:eq(0)'),
					id = parseInt($tr.data('id'));
				if( id ){
					ids +=","+ id;
				}
			});
			var updateTypeVar=3,updateMessage="";
			if(type == 'refuse'){
				updateTypeVar=4;
				updateMessage="拒绝";
			}
             
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">您确认全部'+updateMessage+'通过审核？</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					$.ajax({
						url:ajaxurl,				
						dataType:'json',
						method:'post',
						data:{ids2: ids, updateType: updateTypeVar},
						success:function(data){
							//红包直接返回成功
							msg.hide();
						    location.reload();
								
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
						    msg.hide();
						    location.reload();
						},
						complete: function()
						{
							msg.hide();
						    location.reload();
						}				
					});
				},
				cancelIsShow: 'true',
				cancelText: '取消',
				cancelFun: function(){
					msg.hide();
				}
			});
		});
		

								
	});