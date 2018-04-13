$(function(){
									$('.menu-list li').removeAttr("class");
									$('.menu-list li:eq(6)').attr("class","current"); 
									$('.col-side .nav dd:eq(16)').attr("class","current");
									$("#channel").val($('#channelv').val());
									$("#updateStatus").val($('#updateStatusv').val());
										// 搜索相关
									    $('#J-button-submit').click(function() {
									        $('#J-form').submit();
									    });

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
											minWindowDom = minWindow.getContainerDom(),
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
												times = $tr.find('[data-times]').data('times')
												id = $tr.data('id');
											minWindowDom.width(488);

											if( action == 'update' ){
												minWindow.setTitle('更新');
												minWindow.setContent(
													'<div class="pop-title text-left"><strong>用户名：</strong>' + username + '</div>' +
													'<div class="pop-title text-left"><p><strong>当前转盘抽奖剩余次数：</strong>' + times + '&nbsp;次</p>' + '</div>' +
													'<div class="pop-title text-left">' +
														'<select class="ui-select w-2" id="J-update-select">' +
															'<option value="increase">添加</option>' +
															'<option value="decrease">扣除</option>' +									
														'</select>&nbsp;' +
														'<input id="J-update-num" type="text" name="" value="" placeholder="输入次数" class="input w-2 input-money">&nbsp;次' +
													'</div>' + 
													'<div class="pop-title text-left">' +
														'<p><strong>操作原因<span class="color-red">*</span></strong></p>' +
														'<textarea id="J-update-reason" class="textarea w-6" name="" placeholder="请输入更新数据的原因"></textarea>' + 
													'</div>' + 
													'<div class="pop-title text-left"><strong>更新后转盘抽奖剩余次数：</strong><span id="J-update-times">' + times + '</span>&nbsp;次</div>' + 
													'<div class="pop-btn">' +
														'<a href="javascript:void(0);" class="btn btn-important" id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a>' +
														'<a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a>' +
													'</div>'
												);
												minWindow.show();
												$('#J-update-select, #J-update-num').on('change', function(){
													// console.log('aaa')
													var type = $('#J-update-select').val(),
														num = parseInt($('#J-update-num').val());
													if( type == 'decrease' && num > times ){
														alert('扣除的剩余次数不能大于当前剩余次数');
														num = 0;
														$('#J-update-num').val(num);
													}
													doAmountUpdate(times, type, num);
												});
												$('#J-update-num').on('keyup', function(){
													var val = parseInt($(this).val());
													$(this).val(val);
												});

												$('#J-button-pop-confirm').on('click', function(e) {
													e.preventDefault();
													var type = $('#J-update-select').val(),
														num = parseInt($('#J-update-num').val()),
														reason = $('#J-update-reason').val();
													if( type != 'increase' && type != 'decrease' ){
														return alert('请选择操作类型！');
													}
													if( isNaN(num) || num < 0 ){
														return alert('操作次数输入有误！');
													}												
													if( !reason ){
														return alert('请输入更新数据的原因!');
													}

													minWindow.hide();
													// 请开启以下注释，并去掉上一行代码
													$.post('/adAdmin/updateActivityZp', {
														type: type,
														num: num,
														reason: reason,
														id:id,
														updateType:1
													}).done(function(resp){
														var resp = $.parseJSON(resp);                
														if( resp['status'] == 1 ){
															minWindow.hide();
															alert('操作成功！');
															location.reload();
														}else{
															alert(resp.message);
														}
													}).fail(function(xhr, textStatus, errorThrown){
														alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
													});

												});
												$('#J-button-pop-cancel').on('click', function() {
													minWindow.hide();
												});
											}else if( action == 'publish' ){
												var 
													showtip=$tr.find('[data-last]').data('last'),
													type       =times-showtip<0 ? 'increase' : 'decrease',
													num        = $tr.find('[data-updatetimes]').data('updatetimes'),
													result     = times + num,
													actionuser = $tr.find('[data-actionuser]').data('actionuser'),
													reason     = $tr.find('[data-reason]').data('reason');

												minWindow.setTitle('发布');
												minWindow.setContent(
													'<div class="pop-title text-left"><strong>用户名：</strong>' + username + '</div>' +
													'<div class="pop-title text-left">' +
														'<p><strong>更新前转盘抽奖剩余次数：</strong>' + times + '&nbsp;次</p>' +
														'<p><strong>更新内容：</strong>' + (type == 'increase' ? '添加' + num: '扣除'+ (-num))  + '次</p>' +
														'<p><strong>更新后转盘抽奖剩余次数：</strong>' + result + '&nbsp;次</p>' + 
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
													minWindow.hide();
													// 请开启以下注释，并去掉上一行代码
													$.post('/adAdmin/updateActivityZp', {id: id,updateType:2}).done(function(resp){
														var resp = $.parseJSON(resp);                
														if( resp.status == 1 ){
															minWindow.hide();
															alert('操作成功！');
															location.reload();
														}else{
															alert(resp.message);
														}
													}).fail(function(xhr, textStatus, errorThrown){
														alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
													});

												});
												$('#J-button-pop-cancel').on('click', function() {
													minWindow.hide();
												});
											}else if( action == 'detail' ){
												$.get('/adAdmin/queryActivitySheepYaDaXiaoDetail', {id: username,activityId:5}).done(function(resp){
													var resp = $.parseJSON(resp);
													if( resp.status == 1 ){
														var tableHtml = createDetailTable(resp.data);
														minWindowDom.width(800);
														minWindow.setTitle('详细');
														minWindow.setContent(
															'<div class="pop-title text-left"><strong>用户名：</strong>' + username + 
																'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
																'<strong>当前转盘抽奖剩余次数：</strong>' + times + '&nbsp;次' +
															'</div><div style="height:400px;overflow-y:auto;">' + tableHtml + 
															'</div><div class="pop-btn">' +
																'<a href="javascript:void(0);" class="btn btn-important" id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a>' +
															'</div>'
														);
														minWindow.show();

														$('#J-button-pop-confirm').on('click', function(e) {
															minWindow.hide();
														});
													}else{
														alert(resp.msg);
													}
												}).fail(function(xhr, textStatus, errorThrown){
													alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
												});
														
											}
										});

										function doAmountUpdate(times, type, num){
											if( type == 'increase' ){
												times += num;
											}else{
												times -= num;
											}
											// return times;
											$('#J-update-times').text(times);
										}

										function createDetailTable(data){
											var html = '<table class="table table-info">' +
															'<thead class="text-center"><tr><th>时间</th><th>充值金额</th><th>获得次数</th><th>使用次数</th><th>转盘抽奖结果</th></tr></thead>' +
															'<tbody class="text-center">';

											$.each(data, function(i, n){
												html += '<tr>';
												$.each(n, function(k,v){
													html += '<td>' + v + '</td>';
												});
												html += '</tr>';
											});
											html += '</tbody></table>';
											console.log(html);
											return html;
										}				
									});