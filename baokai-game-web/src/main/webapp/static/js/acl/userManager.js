$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
			var message = phoenix.Message.getInstance(),
				lockDom = '.lock',
				unlockDom = '.unlock',
				deleteDom = '.delete';

				$('body').on('click', lockDom, function(){
					var data = $(this).parents('tr').eq(0).find('.username').text();
					var id = $(this).attr('data-id');
					message.show({
						title:'锁定提示',
						content : ['<div class="bd text-center">',
										'<div class="pop-title">',
											'<i class="ico-waring"></i>',
											'<h4 class="pop-text">你确定要锁定管理员'+data+'吗？锁定成功后该账号将不能登录管理后台.</h4>',
										'</div>',
									'</div>'].join(''),
						mask: true,
						closeIsShow: true,
						closeFun: function(){
							this.hide();
						},
						confirmIsShow: true,
						confirmFun: function(){
							$.ajax({
								url: baseUrl+'/aclAdmin/lockUser',
								dataType: 'json',
								type: 'POST',
								data : {id:id},
								success:function(data){
									if(Number(data['isSuccess']) == 1){
										win.location.reload();
									}else{
									}
								},
								complete: function(){
									message.hide();
									location.href = baseUrl+'/aclAdmin/goUserManager';
								}
							});
						}
					})
				})
				$('body').on('click', unlockDom, function(){
					var data = $(this).parents('tr').eq(0).find('.username').text();
					var id = $(this).attr('data-id');
					message.show({
						content : ['<div class="bd text-center">',
										'<div class="pop-title">',
											'<i class="ico-waring"></i>',
											'<h4 class="pop-text" style="width:250px;">你确定要解锁管理员'+data+'吗？解锁成功后该账号将恢复正常使用。</h4>',
										'</div>',
									'</div>'].join(''),
						mask: true,
						closeIsShow: true,
						closeFun: function(){
							this.hide();
						},
						confirmIsShow: true,
						confirmFun: function(){
							$.ajax({
								url: baseUrl+'/aclAdmin/unlockUser',
								dataType: 'json',
								type: 'POST',
								data : {id:id},
								success:function(data){
									if(Number(data['isSuccess']) == 1){
										win.location.reload();
									}else{
									}
								},
								complete: function(){
									message.hide();
									location.href = baseUrl+'/aclAdmin/goUserManager';
								}
							});
						}
					})
				})
				$('body').on('click', deleteDom, function(){
					var data = $(this).parents('tr').eq(0).find('.username').text();
					var id = $(this).attr('data-id');
					message.show({
						content : ['<div class="bd text-center">',
										'<div class="pop-title">',
											'<i class="ico-waring"></i>',
											'<h4 class="pop-text">你确定要删除管理员'+data+'吗？删除后将不能恢复。</h4>',
										'</div>',
									'</div>'].join(''),
						//不能删除情况的HTML结构
						// content : ['<div class="bd text-center">',
						// 				'<div class="pop-title">',
						// 					'<i class="ico-waring"></i>',
						// 					'<h4 class="pop-text">你不能删除当前账号，请先确认OA系统当前账号是否已被删除。</h4>',
						// 				'</div>',
						// 			'</div>'].join(''),
						mask: true,
						closeIsShow: true,
						closeFun: function(){
							this.hide();
						},
						confirmIsShow: true,
						confirmFun: function(){
							$.ajax({
								url: baseUrl+'/aclAdmin/deleteUser',
								dataType: 'json',
								type: 'POST',
								data : {id:id},
								success:function(data){
									if(Number(data['isSuccess']) == 1){
										win.location.reload();
									}else{
									}
								},
								complete: function(){
									message.hide();
									location.href = baseUrl+'/aclAdmin/goUserManager';
								}
							});
						}
					})
				})
				$('#J-button-submit').click(function(){
					$('#J-form').submit();
				});
		})
