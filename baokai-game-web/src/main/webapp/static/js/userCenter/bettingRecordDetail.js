$(document)
			.ready(
					function() {					
					

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
															 $('#J-Submit').html('提交中...').attr("disabled", true);
															 $('#divCanceled').hide();
														},	
														success : function(data) {
															if (data.status ==1) {
																$('#revSscheme').hide();
																
																$('#cancel').html('<strong class="high color-red" id="revSchemeOk">方案已被撤销</strong>');
																$('#statusName').html('撤销');
																$("[name='statusName']").html('撤销');
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
																//window.location = "/bet/plans";
															} else {
																$('#message').html(data.message);
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
															$('#divCanceled').show();
															$('#J-Submit').html('确定').removeAttr("disabled");
														}
													});
										});

					});

	function setFn(obj) {
		$('#' + obj).css("display", "none");
	}