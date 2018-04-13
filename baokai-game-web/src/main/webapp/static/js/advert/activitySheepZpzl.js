$(function(){
									$('.menu-list li').removeAttr("class");
									$('.menu-list li:eq(6)').attr("class","current"); 
									$('.col-side .nav dd:eq(13)').attr("class","current");
									//var $allDetailTbodys = $('.detail-tbody').slideUp(0);
									
									var MSG = new phoenix.Message();
									$('.j-ui-miniwindow').removeClass("w-9");
									$('.j-ui-miniwindow').addClass("w-11");
									$('a[data-detail]').on('click', function(e){
										e.preventDefault();
										var URL = "/adAdmin/querySheepDetailCount";
										var	$this = $(this);
										var date = $this.parent().parent().attr("data-str");
										var activityId = $('#activityId').val();
										
										$.get(URL, {'date':date,'activityId':activityId}, function(res) {
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
										 var template='<table class="table table-info">'
											 template += '<thead class="text-center">';
											 template += '<tr><th>日期</th>';
											 template += '<th>获得次数</th>';
											 template += '<th>使用次数</th>';
											 template += '<th>中奖数量</th>';
											 template += '<th>剩余数量</th>';
											 template += '<th>总中奖金额</th>';
											 template += '</thead>';
											 template += '<tbody class="detail-tbody text-center">';
											 for (var i = 0; i < data.length; i++) {
												 template += '<tr><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td><td>'+data[i][4]+'</td><td>'+ data[i][5]+'</td></tr>';
											 }
											 template += '</tbody></table>';
												MSG.show({
												'content':template,
												'confirmIsShow': false,
											  });  
									}
});