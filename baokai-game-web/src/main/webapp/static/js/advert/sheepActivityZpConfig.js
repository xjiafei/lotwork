$(function(){
								$('.menu-list li').removeAttr("class");
								$('.menu-list li:eq(6)').attr("class","current"); 
								$('.col-side .nav dd:eq(17)').attr("class","current");
								var ratioTip = $("#ratioTip").val();
								if(ratioTip!="" && ratioTip != 10000){
									alert("概率不合法,目前合计为"+ratioTip/100+",必须合计为100！请修改后保存。");
								}
								var MSG = new phoenix.Message();
								var URL = baseUrl+'/adAdmin/queryActivitySheepConfigOperate';
								var STATUSURL = baseUrl +'/adAdmin/updateActivitySheepConfigOperate';

								$('body').on('click', '[data-action="setting"]', function(){
									var $this = $(this),
										model = $this.attr('data-model')
										activityConfigId = $this.parent().parent().attr('data-id');
										;
									

									getSource(model,activityConfigId);
								});

								bindEvent = function(){

									$('body').on('click', '.add-info', function(){
										var	$this = $(this),
											$parent = $this.parents('.container-rewards:eq(0)'),
											$dom = $parent.find('.J-rewards-list'),
											$tr = $dom.find('tr:last'),
											$rewardsDom = $parent.find('.J-rewards-num'),
											index = (Number($tr.find('[name="rsIndex"]').val()) + 1) || 1;

										$dom.append('<tr id="re-index-'+index+'">'
												+ '<td>'+index+'.<input type="hidden" name="rsIndex" value="'+index+'"></td>'
												+ '<td>'
												+ '	<input name="rsTime" type="text" class="input w-2 date-picker" value="">'
												+ '	<input name="rsId" type="hidden" class="input w-2 date-picker" value="-1">'
												+ '</td>'
												+ '<td>'
												+ '	<input name="rsNum" type="num" class="input w-2" value="0"> 个'
												+ '</td>'
												+ '<td>'
												+ '	<a href="#" class="btn btn-link del">x</a>'
												+ '</td>'
											+ '</tr>');

										$rewardsDom.text(Number($rewardsDom.text()) + 1);
									});


									$('body').on('focus', '.date-picker', function(){
										 (new phoenix.DatePicker({
								            input: $(this),
								            isShowTime: true
								        })).show();
									});

									$('body').on('focus', '.del', function(){
										var $rewardsDom = $(this).parents('.container-rewards:eq(0)').find('.J-rewards-num');
										$(this).parents('tr:eq(0)').remove();
										$rewardsDom.text(Number($rewardsDom.text()) - 1);
									});
								};

								//读取数据
								getSource = function(model,activityConfigId){

									$.get(URL, {'reChargeModel':model,'activityConfigId':activityConfigId}, function(res) {
										if(res && res['status'] == 1){
											render(res['data'],activityConfigId);
										}
									}, 'json');
								};

								render = function(data,activityConfigId){
									var template = $('#J-control-rewards').val();
									rebulidData(data);

									MSG.show({
										'content': replaceHtml(data, template,activityConfigId),
										'confirmIsShow': true,
										'confirmText': data['action'],
										'confirmFun': function(){
											var formData = $('#form1').serialize();
											$.post(STATUSURL, formData, function(data) {
												var me = this;

												MSG.hide();
											}, 'json');
										}
									});
								};

								rebulidData = function(data){

									//判断状态 1是开启 0是关闭
									if(Number(data['status']) == 1){
										data['status'] = '开启';
										data['action'] = '关闭';
										data['actionNum'] = 0;
									}else{
										data['status'] = '未开启';
										data['action'] = '开启';
										data['actionNum'] = 1;
									}

									data['list'] = '';

									for (var i = 0; i < data['rewards'].length; i++) {
										var current = data['rewards'][i];
										data['list'] += '<tr id="re-index-'+current['index']+'">'
												+ '<td>'+(i+1)+'.<input type="hidden" name="rsIndex" value="'+current[2]+'"></td>'
												+ '<td>'+'	<input name="rsId" type="hidden" class="input w-2 date-picker" value="'+current[3]+'">'
												+ '	<input name="rsTime" type="text" class="input w-2 date-picker" value="'+current[0]+'">'
												+ '</td>'
												+ '<td>'
												+ '	<input name="rsNum" type="num" class="input w-2" value="'+current[1]+'"> 个'
												+ '</td>'
												+ '<td>'
												+ '	<a href="#" class="btn btn-link del">x</a>'
												+ '</td>'
											+ '</tr>';
									};
									
								};

								replaceHtml = function(data, html,activityConfigId){
									var result = html;

									for(var name in data){
										var nameExp = new RegExp('<#='+name+'#>', 'ig');
										result  = result.replace(nameExp, data[name]);
									}
									result  = result.replace(new RegExp('<#='+'activityConfigId'+'#>', 'ig'), activityConfigId);
									return result;
								};


								bindEvent();
});