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


		// TIPS
		var inputTip = new phoenix.Tip.getInstance();
		$('body').on('mouseover', '[data-showtip]', function() {
			var dom = $(this),
				text = dom.attr('data-showtip');
			if (text) {
				inputTip.setText('更新前：' + text);
				inputTip.show(dom.outerWidth() + 4, dom.outerHeight() * -1, this);
			}
		}).on('mouseout', '[data-showtip]', function() {
			var text = this.getAttribute('data-showtip');
			if (text) {
				inputTip.hide();
			}
		});

		// 更新/发布
		var minWindow = new phoenix.MiniWindow({ cls: 'pop' }), 
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
				amount = parseFloat($tr.find('[data-amount]').data('amount'));
				id = $tr.find('[data-id]').data('id');
			if( action == 'update' ){
				minWindow.setTitle('更新');
				minWindow.setContent(
					'<div class="pop-title text-left"><input type="hidden" id="J-update-id" value='+id+'></input><strong>用户名：</strong>' + username + '</div>' +
					'<div class="pop-title text-left"><p><strong>当前累计投注金额：</strong>' + amount + '&nbsp;元</p>' + '</div>' +
					'<div class="pop-title text-left">' +
						'<select class="ui-select w-2" id="J-update-select">' +
							'<option value="increase">添加</option>' +
							'<option value="decrease">扣除</option>' +									
						'</select>&nbsp;' +
						'<input id="J-update-num" type="text" name="" value="" placeholder="输入金额" class="input w-2 input-money">&nbsp;元' +
					'</div>' + 
					'<div class="pop-title text-left">' +
						'<p><strong>操作原因<span class="color-red">*</span></strong></p>' +
						'<textarea id="J-update-reason" class="textarea w-6" name="" placeholder="请输入更新数据的原因"></textarea>' + 
					'</div>' + 
					'<div class="pop-title text-left"><strong>更新后累计投注金额：</strong><span id="J-update-amount">' + amount + '</span>&nbsp;元</div>' + 
					'<div class="pop-btn">' +
						'<a href="javascript:void(0);" class="btn btn-important" id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a>' +
						'<a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a>' +
					'</div>'
				);
				minWindow.show();
				$('#J-update-select, #J-update-num').on('change', function(){
					// console.log('aaa')
					var type = $('#J-update-select').val(),
						num = parseFloat($('#J-update-num').val());
					if( type == 'decrease' && num > amount ){
						alert('扣除的累计投注金额不能大于当前累计投注金额');
						num = 0;
						$('#J-update-num').val(num);
					}
					doAmountUpdate(amount, type, num);
				});
				$('#J-update-num').on('keyup', function(){
					var me = this,v = me.value,index;
					me.value = v = v.replace(/^\.$/g, '');		
					index = v.indexOf('.');
					if(index > 0){
						me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
						if(v.substring(index+1,v.length).length>2){				
							me.value= v  = v.substring(0, v.indexOf(".") + 3);
						}
					}	
					me.value = v = v.replace(/[^\d|^\.]/g, '');
					me.value = v = v.replace(/^00/g, '0');			
				});

				$('#J-button-pop-confirm').on('click', function(e) {
					e.preventDefault();
					var type = $('#J-update-select').val(),
						num = parseFloat($('#J-update-num').val()),
						id = $('#J-update-id').val(),
						reason = $('#J-update-reason').val();
					if( type != 'increase' && type != 'decrease' ){
						return alert('请选择操作类型！');
					}
			
					if( isNaN(num) || num < 0 ){
						return alert('操作金额输入有误！');
					}												
					if( !reason ){
						return alert('请输入更新数据的原因!');
					}
					if( type == 'decrease' ){
						num = (0 - num) *10000;
					}else{
						num =  num *10000;
					}
					//minWindow.hide();
					// 请开启以下注释，并去掉上一行代码
					$.post('updateActivitySheepHongBao', {
						type: type,
						updateAward: num,
						id:id,
						updateType: 1,
						updateReason: reason
					}).done(function(resp){
						/*var resp = $.parseJSON(resp);                
						if( resp.status == 1 ){
							minWindow.hide();
							alert('操作成功！');
							location.reload();
						}else{
							alert(resp.msg);
						}*/
						alert('操作成功！');
						location.reload();
					}).fail(function(xhr, textStatus, errorThrown){
						alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
					});

				});
				$('#J-button-pop-cancel').on('click', function() {
					minWindow.hide();
				});
			}else if( action == 'publish' ){
				var num         = $tr.find('[data-updateamount]').data('updateamount'),
					type       = num >0 ? 'increase' : 'decrease',
					result     =  $tr.find('[data-updatedamount]').data('updatedamount'),
					actionuser = $tr.find('[data-actionuser]').data('actionuser'),
					reason     = $tr.find('[data-reason]').data('reason'),
					actionid   = $tr.find('[data-actionid]').data('actionid');
					num = num >0 ? num : 0-num;
				minWindow.setTitle('发布');
				minWindow.setContent(
					'<div class="pop-title text-left"><strong>用户名：</strong>' + username + '</div>' +
					'<div class="pop-title text-left">' +
						'<p><strong>更新前累计投注金额：</strong>' + amount + '&nbsp;元</p>' +
						'<p><strong>更新内容：</strong>' + (type == 'increase' ? '添加' : '扣除') + num + '元</p>' +
						'<p><strong>更新后累计投注金额：</strong>' + result + '&nbsp;元</p>' + 
					'</div>' +
					'<div class="pop-title text-left">' +
						'<p><strong>操作人：</strong>' + actionuser + '</p>' +
						'<p><strong>操作原因：</strong>' + reason + '</p>' +
					'</div>' + 
					'<div class="pop-btn">' +
						'<a href="javascript:void(0);" class="btn btn-important" id="J-button-pop-confirm">同 意<b class="btn-inner"></b></a>' +
						'<a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">拒 绝<b class="btn-inner"></b></a>' +
					'</div>'
				);
				minWindow.show();

				$('#J-button-pop-confirm').on('click', function(e) {
					e.preventDefault();
					//minWindow.hide();
					// 请开启以下注释，并去掉上一行代码
					$.post('updateActivitySheepHongBao', {
						id: id,
						updateType: 2

						}).done(function(resp){
						
						minWindow.hide();
						alert('操作成功！');
						location.reload();           
						/*
						var resp = $.parseJSON(resp);     
						if( resp.status == 'ok' ){
							minWindow.hide();
							alert('操作成功！');
							location.reload();
						}else{
							alert(resp.msg);
						}
						*/
					}).fail(function(xhr, textStatus, errorThrown){
						alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
					});

				});
				$('#J-button-pop-cancel').on('click', function() {
					e.preventDefault();
					//minWindow.hide();
					// 请开启以下注释，并去掉上一行代码
					$.post('updateActivitySheepHongBao', {
						id: id,
						updateType: 5

						}).done(function(resp){
						
						minWindow.hide();
						alert('操作成功！');
						location.reload();           
						/*
						var resp = $.parseJSON(resp);     
						if( resp.status == 'ok' ){
							minWindow.hide();
							alert('操作成功！');
							location.reload();
						}else{
							alert(resp.msg);
						}
						*/
					}).fail(function(xhr, textStatus, errorThrown){
						alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
					});
				});
			}
		});

		function doAmountUpdate(amount, type, num){
			if( type == 'increase' ){
				amount += num;
			}else{
				amount -= num;
			}
			// return amount;
			$('#J-update-amount').text(currency(amount))
		}

		function currency(num, n, x){
			n = n || 2;
			x = x || 3;
			// console.log(typeof num)
			if( isNaN(num) || num <= 0 ){
				return '0.00';
			}else{
				var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
				return num.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$&,');
			}
		}
		
		var MSG = new phoenix.Message();
		$('.j-ui-miniwindow').removeClass("w-9");
		$('.j-ui-miniwindow').addClass("w-11");
		$('a[data-detail]').on('click', function(e){
			e.preventDefault();
			
			var URL = "/adAdmin/querySheepHongBaoDetail";
			var	$this = $(this);
			var id = $this.parent().parent().attr("data-id");
			var userName = $this.parent().parent().attr("data-name");
			var amount = $this.parent().parent().attr("data-amount");
			$.get(URL, {'id':id,'userName':userName,'amount':amount}, function(res) {
				if(res && res['status'] == 1){
					render(res['data']);
				}
			}, 'json');
			
			
        
			/*var $thisTbody = $(this).parents('tbody:eq(0)'),
				$nextTbody = $thisTbody.next();
			$nextTbody.siblings('.detail-tbody').slideUp(0);
			$nextTbody.slideToggle();*/
		});
		
		render = function(data){
			var templatehed = '用户名:'+data[0][0]+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;累计投注金额:'+data[0][1];
			 var template=templatehed+'<div style="height:400px;overflow-y:auto;"><table class="table table-info">'
				 template += '<thead class="text-center">';
				 template += '<tr><th>时间</th>';
				 template += '<th>3.0</th>';
				 template += '<th>4.0</th>';
				 template += '<th>小计</th>';
				 template += '</thead>';
				 template += '<tbody class="detail-tbody text-center">';
				 for (var i = 1; i < data.length; i++) {
					 template += '<tr><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td></tr>';
				 }
				 template += '</tbody></table></div>';
					MSG.show({
					'content':template,
					'confirmIsShow': false,
				  });  
		}
		
		
	});
  