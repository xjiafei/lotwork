$(document).ready(function() {
	//弹窗
	var Wd = phoenix.Message.getInstance(),table = $('#J-table'),panels = table.find('.panel-lotterys-inner'),panelHeight = 144;
	
	var lotteryid = $('#lotteryid').val();
	var issueCode = $('#issueCode').val();
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(4)').attr("class","current");
	
//	$('#J-button-cancel').click(function(){
//		var id = this.getAttribute('data-id');
//		Wd.show({
//			mask:true,
//			title:'温馨提示',
//			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定要撤销该方案吗？</h4></div>',
//			confirmIsShow:true,
//			cancelIsShow:true,
//			confirmFun:function(){
//				Wd.hide();
//				
//				//方案记录>撤销方案
//				var text = "方案记录>撤销方案";
//				
//				var url = baseUrl+"/gameoa/cancelCurrentPackage";
//				var data = '{"lotteryid":'+lotteryid+',"issueCode":'+issueCode+',"disposeMemo":"'+text+'"}';
//				sendUrl(url, data);
//			},
//			cancelFun:function(){
//				Wd.hide();
//			}
//		});
//	});
	
	
	
	$('#J-table').on('click', '.ico-view-more', function(){
		var dom = $(this),panel = dom.parent().find('.panel-lotterys');
		if(panel.height() > panelHeight){
			panel.css({height:panelHeight});
			dom.find('i').removeClass('ico-open').addClass('ico-close');
		}else{
			panel.css({height:'auto'});
			dom.find('i').removeClass('ico-close').addClass('ico-open');
		}
		

	});
	
	panels.each(function(){
		var dom = $(this);
		if(dom.height() > panelHeight){
			dom.parent().parent().find('.ico-view-more').show();
		}
	});
	
//	function sendUrl(url, data){
//		jQuery.ajax({
//			type:  "post",
//			url: url,
//			dataType:'json', 
//			contentType: "application/json; charset=utf-8",
//			data: data,
//			success:function(data){
//				Wd.show({
//					mask:true,
//					content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data.message +'</h4></div></div>',
//					cancelIsShow:true,
//					cancelText:'关闭',
//					cancelFun:function(){
//						Wd.hide();
//					}
//				});
//			},
//			error: function(){
//				alert("error");
//			}
//		});
//	}
	
	
	//弹出提示框
	$('#revSscheme').click(function() {
		fn("divPrompt");
	});

	$('#divclose,#divCanceled').click(function() {
		$('#divPrompt').css("display", "none");
	});

	//操作后提示	 
	function fn(obj) {
		var Idivdok = document.getElementById(obj);
		Idivdok.style.display = "block";

		Idivdok.style.left = (document.documentElement.clientWidth - Idivdok.clientWidth)
				/ 2
				+ document.documentElement.scrollLeft
				+ "px";
		Idivdok.style.top = (document.documentElement.clientHeight - Idivdok.clientHeight)
				/ 2
				+ document.documentElement.scrollTop
				- 40 + "px";
	}
	//提示方式例子
	$('#J-Submit')
			.click(
					function() {
						$
								.ajax({
									type : 'post',
									contentType : "application/json",
									cache : false,
									url : "cancelOrder?orderId="+$('#orderId').val(),
									data :'',
									beforeSend : function() {
										$('#revSscheme').hide();
										
									},
									success : function(data) {
										if (data.status ==1) {
											$('#revSscheme')
													.hide();
											$('#cancel').html('<strong class="high color-red" id="revSchemeOk">方案已被撤销</strong>');
											$('#statusName').html('撤销');
											$(
													'#revSchemeOk')
													.show();
											fn("divPromptOk");
											$('#divPrompt')
													.css(
															"display",
															"none");
											setTimeout(
													"setFn('divPromptOk')",
													2500);
											location.href = location.href;
										} else {
											$('#message').html(data.message);
											$('#revSscheme')
											.show();
											fn("divPromptFailure");
											$(
													'#divPromptFailuren1,#divPromptFailuren2')
													.click(
															function() {
																$(
																		'#divPrompt')
																		.css(
																				"display",
																				"none");
																$(
																		'#divPromptFailure')
																		.css(
																				"display",
																				"none");
															});
										}

									},

									error : function(data,
												type) {
										$('#message').html(data.message);
										$('#revSscheme')
										.show();
										fn("divPromptFailure");
										$(
												'#divPromptFailuren1,#divPromptFailuren2')
												.click(
														function() {
															$(
																	'#divPrompt')
																	.css(
																			"display",
																			"none");
															$(
																	'#divPromptFailure')
																	.css(
																			"display",
																			"none");
														});
									},
									complete : function() {
									}
								});
					});

	


function setFn(obj) {
	$('#' + obj).css("display", "none");
}

var error = $("#error").val();
	if(error == 1){
		fn("divError");
	};
});
