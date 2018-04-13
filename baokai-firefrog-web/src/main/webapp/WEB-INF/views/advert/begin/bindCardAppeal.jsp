<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>绑卡奖励审核</title>
	
	<style>
	.table-group .table-toggle a {color:#009B7D; text-decoration:underline;}
	.j-ui-tip {border:1px solid #CCC;padding:10px;}
	.j-ui-tip .sj {display:none;}
	.row-show {position:relative;}
	.row-show-pass {position:relative;}
	.row-show-panel {display:none;position:absolute;background:#FFFFE1;border:1px solid #CCC;padding:10px;}
	.row-show-panel-current {display:block;white-space:normal;}
	.table a {text-decoration:underline;}
	</style>	
</head>
<body>		
			<div class="col-crumbs">
                <div class="crumbs">
			 <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 绑卡奖励审核
                </div>
            </div>
            
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_QUERY">
						<form action="${currentContextPath }/begin/toBindCardAppeal" id="J-form" method="POST">
						<ul class="ui-search">
								<li class="time">
									<c:choose>
										<c:when test="${type == 3}">
											<label for="" class="ui-label">取消资格时间：</label>
										</c:when>
								    	<c:otherwise>
								    		<label for="" class="ui-label">注册时间：</label>
								    	</c:otherwise>
									</c:choose>
									<input id="timestart" name="timeStart" type="text" value="${timeStart}" class="input"> - <input id="timeend" type="text" name="timeEnd" value="${timeEnd}" class="input">
								</li>
								<li>
								<input type="hidden" name="status" id="status" value="${type}">
								<a id="J-button-search" href="javascript:void(0);" class="btn btn-important">查询<b class="btn-inner"></b></a></li>
								<c:if test="${type == 3}">
								<li>
								<a id="J-button-cancel" href="javascript:void(0);" class="btn btn-important">取消资格<b class="btn-inner"></b></a></li>
								</c:if>
								<c:if test="${type == 0 && !empty checkDatas}">
								<div style="float:right;">
								<h4 class="ui-label">批量通过/批量拒绝操作注意信息：</h4><br>
								1.批量通过/批量拒绝文档命名格式 <a href="/upload/files/名单类型_日期_时间.xlsx">名单类型_日期_时间</a><br>								
								2.导入文档为Excel 2010版本，务必先确认版本号再进行导入，否则将造成数据错误！
								</div>
								</c:if>
						</ul>
						</form>
						</permission:hasRight>
						
						<div class="ui-tab">
							<div class="ui-tab-title">
								<ul>
								<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_ALL">
								<c:choose>
								    <c:when test="${type == -1}">
								        <li class="current"><a href="javascript:void(0);" id="-1">全部</a></li>
								    </c:when>    
								    <c:otherwise>
								        <li><a href="javascript:void(0);" id="-1">全部</a></li>
								    </c:otherwise>
								</c:choose>
								</permission:hasRight>
								<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_PASS">
								<c:choose>
								    <c:when test="${type == 1}">
								        <li class="current"><a href="javascript:void(0);" id="1">已通过</a></li>
								    </c:when>    
								    <c:otherwise>
								        <li><a href="javascript:void(0);" id="1">已通过</a></li>
								    </c:otherwise>
								</c:choose>
								</permission:hasRight>
								<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_UNPASS">
								<c:choose>
								    <c:when test="${type == 2}">
								        <li class="current"><a href="javascript:void(0);" id="2">已拒绝</a></li>
								    </c:when>    
								    <c:otherwise>
								        <li><a href="javascript:void(0);" id="2">已拒绝</a></li>
								    </c:otherwise>
								</c:choose>
								</permission:hasRight>
								<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_BECHECK">
								<c:choose>
								    <c:when test="${type == 0}">
								        <li class="current"><a href="javascript:void(0);" id="0">待审核</a></li>
								    </c:when>    
								    <c:otherwise>
								        <li><a href="javascript:void(0);" id="0">待审核</a></li>
								    </c:otherwise>
								</c:choose>
								</permission:hasRight>
								<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_CANCEL">
								<c:choose>
								    <c:when test="${type == 3}">
								        <li class="current"><a href="javascript:void(0);" id="3">活動取消資格名單</a></li>
								    </c:when>    
								    <c:otherwise>
								        <li><a href="javascript:void(0);" id="3">活動取消資格名單</a></li>
								    </c:otherwise>
								</c:choose>
								</permission:hasRight>
								</ul>
								
								<div align="right">
									<c:if test="${type == 0 && !empty checkDatas}">
										<input type="hidden" name="uploadFlag" id="uploadFlag">
										<form id="J-form-upload" name="form1" method="POST" enctype="multipart/form-data" action="${currentContextPath }/begin/fileUpload">
											<input name="tempFile" type="file" id="J-upload-file" class="upload-file-button" size="40" hidefocus="true" value="导入" style="display:none;" />
										</form>
										<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_BATCH_CONFIRM">				
											<button class='btn btn-small'  id='batchPass' style='position:initial'>批量通过</button>
										</permission:hasRight>
										<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_BATCH_CANCEL">
											</permission:hasRight>
										<button class='btn btn-small'  id='batchReject' style='position:initial'>批量拒绝</button>
									</c:if>
									
									<c:if test="${(type < 3 && !empty checkDatas) || (type == 3 && !empty cancelDatas)}">
										<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_EXPORT">
											<button class='btn btn-small'  id='exportList' style='position:initial'>导出名单</button>
										</permission:hasRight>
									</c:if>
									<c:if test="${type == 0 && !empty checkDatas}">
										<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_CANCEL_UPDATE">
											<button class='btn btn-small'  id='cancelCheck' style='position:initial'>取消修改</button>
										</permission:hasRight>
										<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK_CONFIRM_UPDATE">
											<button class='btn btn-small'  id="confirmCheck" style='position:initial' >确认修改</button>
										</permission:hasRight>
									</c:if>
									
								</div>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
								<c:choose>
									<c:when test="${type == 3}">
										<table class="table table-info" id="J-table">
											<thead>
												<tr>
													<th>取消资格时间</th> 
													<th>帐号</th>
													<th>原因</th>
													<th>操作人</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${cancelDatas}" var="cancel" varStatus="status">
													<tr>
														<td>${cancel.cancelTime}</td>
														<td>${cancel.accountName}</td>
														<td>${cancel.cancelReason}</td>
														<td>${cancel.cancelUser}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:otherwise>
								        <table class="table table-info" id="J-table">
											<thead>
												<tr>
													<th>注册时间</th> 
													<th>注册IP</th>
													<th>注册渠道</th>
													<th>注册用户名</th>
													<th>直接上级</th>
													<th>一代号</th>
													<th>总代号</th>
													<th>银行卡绑定时间</th>
													<th>银行卡号码</th>
													<th>银行卡用户名</th>
													<th>银行卡所在城市</th>
													<th>首次充值时间</th>
													<th>首次充值金额</th>
													<th>选择时间段内销量总额</th>
													<th>首次提现时间</th>
													<th>首次提现金额</th>
													<c:choose>
													    <c:when test="${type == 0}">
													        <th>操作 <input type="radio" name="radio" value="Y">全部通过    <input type="radio" name="radio" value="N">全部拒绝 </th>
													    </c:when>    
													    <c:otherwise>
													        <th>状态</th>
													    </c:otherwise>
													</c:choose>
												</tr>
											</thead>
											<tbody id='list'>
												<c:forEach items="${checkDatas}" var="data" varStatus="status">
													<tr>
														<td>${data.registerDate}</td>
														<td>${data.registerIp}</td>
														<td>${data.device}</td>
														<td>${data.account}</td>
														<td>${data.parentAccount}</td>
														<td>${data.chain1}</td>
														<td>${data.chain0}</td>
														<td>${data.createTime}</td>
														<td>${data.bankNum}</td>
														<td>${data.accountName}</td>
														<td>${data.city}</td>
														<td>${data.chargeTime}</td>
														<td>${data.chargeAmt}</td>
														<td>${data.totAmount}</td>
														<td>${data.withdrawTime}</td>
														<td>${data.withdrawAmt}</td>
														<c:choose>
														    <c:when test="${data.checkStatus == 1}">
														        <td>已通过</td>
														    </c:when>
														    <c:when test="${data.checkStatus == 2}">
														        <td>已拒绝</td>
														    </c:when>      
														    <c:otherwise>
														    	<c:if test="${type >= 0}">
														        	<td><label class="label"><input type="radio" name="${data.userId}" value="Y" onclick=''>审核通过    <input type="radio" name="${data.userId}" value="N">审核拒绝</label></td>
														    	</c:if>
														    	<c:if test="${type < 0}">
														        	<c:choose>
																	    <c:when test="${data.checkStatus == 1}">
																	        <td>已通过</td>
																	    </c:when>
																	    <c:when test="${data.checkStatus == 2}">
																	        <td>已拒绝</td>
																	    </c:when>      
																	    <c:otherwise>
																	    	<td>待审核</td>
																	    </c:otherwise>
																	</c:choose>
														    	</c:if>
														    </c:otherwise>
														</c:choose>
													</tr>
												</c:forEach>
											</tbody>
										</table>
								    </c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${type == 3}">
										<c:if test="${!empty cancelDatas}">
											<div style="height:20px"></div>
											<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
										</c:if>
										<c:if test="${empty cancelDatas}">
											<div class="alert alert-waring">
												<i></i>
												<div class="txt">
													<h4>抱歉，没有找到符合的条件，请重新选择搜索！</h4>
												</div>
											</div>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty checkDatas}">
											<div style="height:20px"></div>
											<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
										</c:if>
										<c:if test="${empty checkDatas}">
											<div class="alert alert-waring">
												<i></i>
												<div class="txt">
													<h4>抱歉，没有找到符合的条件，请重新选择搜索！</h4>
												</div>
											</div>
										</c:if>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- pop up window -->			
			<div class="pop w-5" id="cancelWindow" style="display:none;">
		        <div class="hd">
		        	<i class="close" name="closeIcoDiv1"></i>
		        	<h3 align="center"></h3>
				</div>
				<div class="bd">
					帐号：
					<input id="cancelAct"  margin-left:80px" type="text" size="20" maxlength="12" ><br><br>		
					原因：		
					<input id="cancelRsn"  margin-left:80px" type="text" size="20" maxlength="20" ><br><br>	
					<div center>
						<input type="button" id="cancelConfirm"  class="btn btn-important"  value="确认"  style="width:80px;"/>
						<input type="button" class="btn" name="closeIcoDiv1" value="取消"  style="width:80px;"/>				
					</div>
		        </div>
  			</div>	
				
			
	<script type="text/javascript">
		function queryData(){
			starttime = $('#timestart').val();
			endtime = $('#timeend').val(); 
			starttime = starttime.replace(/-/g,"/");
			endtime = endtime.replace(/-/g,"/");
			var startDate = new Date(starttime);
			var endDate = new Date(endtime);
			endDate.setHours(23)
			endDate.setSeconds(59);
			endDate.setMinutes(59);
			var status = $("#status").val();
			var url = "${currentContextPath}/begin/toBindCardAppeal?";
			if(status == 3){
				url = "${currentContextPath}/begin/toCancelList?";
			}
						
			var data = "timeStart="+ startDate.getTime() + "&timeEnd=" + endDate.getTime() + "&status=" + $("#status").val();
			location.href= url+data;
			location.assign();
		}
		//show popup window
		function showAddWindow(){
			box1.OverLay.Color = "rgb(51, 51, 51)" ; 
			box1.Over = true;   
			box1.OverLay.Opacity = 50;  
			box1.Fixed = true;	 
			box1.Center = true;
			$("#cancelWindow h3").text("取消资格");
			box1.Show();
		}
		
		$(function(){
			//pop up window setting
			option = {zIndex:500},	
    		box1 = new LightBox("cancelWindow",option);	
			
			//datePicker setting
			var inputStart = $('#timestart'),inputEnd = $('#timeend');
			var now = new Date();
			now.setHours(0)
			now.setSeconds(0);
			now.setMinutes(0);
			var bDate= now.format('yyyy-MM-dd');
			now.setHours(23)
			now.setSeconds(59);
			now.setMinutes(59);
			var eDate= now.format('yyyy-MM-dd');
			
			if(!$('#timestart').val()){
				inputStart.val(bDate);
			}
			
			if(!$('#timeend').val()){
				inputEnd.val(eDate);
			}
			
			inputStart.focus(function(){
				var d = new phoenix.DatePicker({input:this,isShowTime:false});
				d.show();
			});
			
			inputEnd.focus(function(){
				var d = new phoenix.DatePicker({input:this,isShowTime:false});
				d.show();
			});
			
			//頁簽
			$('.ui-tab-title').find('ul li a').each(function(){
			    $(this).bind("click", function(){
			        var beforeId= $("#status").val();
			        var nowId = $(this).attr('id');
			        
			        if(nowId!=beforeId){
			        	$('#timestart').val(bDate);
			        	$('#timestart').val(eDate);
			        }
			        $("#status").val(nowId);
			        queryData();
			    })
			});
			
			$(document).on('click', '[name="closeIcoDiv1"]', function(e){
				box1.Close();
				actionFlag="";
				updateIndex=-1;
			});
			
			//全選
			$("input[name=radio]").click(function(){
				var status = this.value;
				$("#list input[type=radio]").each(function(i){
					if($(this).val() == status){
						$(this).attr("checked","checked");
					}
				});
			});
			
			//查詢
			$("#J-button-search").click(function(){
				queryData();
			});
			
			//取消資格名單
			$("#cancelList").click(function(){
				location.href="${currentContextPath}/begin/toCancelList";
				location.assign();
				
			});
			
			//取消資格popup
			$("#J-button-cancel").click(showAddWindow);
			
			//取消資格
			$("#cancelConfirm").click(function(){
				if($("#cancelAct").val() == ''){
					alert("请输入帐号");
					return;
				}
				
				if($("#cancelRsn").val() == ''){
					alert("请输入原因");
					return;
				}
				var obj = new Object();
				obj.account = $("#cancelAct").val().trim();
				obj.reason = $("#cancelRsn").val().trim();
				$.ajax({
                    url:'${currentContextPath}/begin/backendCancelMission',
                    method:"post",
                    data:obj,
                    dataType:"json",
                    success:function(data){
                    	if(data.body.result == 1){
                    		alert("已成功取消資格");
                    	}else if(data.body.result== -1){
                    		alert("状态不符，取消資格失败");
                    	}else{
                    		alert("系統錯誤，取消資格失败");	
                    	}
                    	window.location = "${currentContextPath}/begin/toBindCardAppeal";
                    }
                });
			});
			
			//取消修改
			$("#cancelCheck").click(function(){
				queryData();
			});
			
			//导出名单
			$("#exportList").click(function(){
				starttime = $('#timestart').val();
				endtime = $('#timeend').val(); 
				starttime = starttime.replace(/-/g,"/");
				endtime = endtime.replace(/-/g,"/");
				var startDate = new Date(starttime);
				var endDate = new Date(endtime);
				endDate.setHours(23)
				endDate.setSeconds(59);
				endDate.setMinutes(59);
				var status = $("#status").val();
				var data = "timeStart="+ startDate.getTime() + "&timeEnd=" + endDate.getTime() + "&status=" + $("#status").val();
				window.open("${currentContextPath}/begin/exportExcel?" + data);
			});
			
			//批量通过
			$("#batchPass").click(function(){
				$("#uploadFlag").val("pass");
				document.getElementById('J-form-upload').tempFile.click();
			});
			
			//批量拒绝
			$("#batchReject").click(function(){
				$("#uploadFlag").val("unPass");
				document.getElementById('J-form-upload').tempFile.click();
			});
			
			$("[name=tempFile]").change(function(e){
				var fileName = e.target.files[0].name;
				var extIndex = fileName.lastIndexOf('.');
				//檢查檔名
				if (extIndex != -1) 
				{				   	 
				   var ext= fileName.substr(extIndex+1, fileName.length); //副檔名
				}
				
				if(ext != 'xlsx'){
					alert("请上传正确档案");
					return;
				}
				
				var uploadFlag = $('#uploadFlag').val();
				var formData = new FormData();
		        formData.append('tempFile', $("[name=tempFile]").val());
				if(uploadFlag=='pass'){
					if(confirm('将'+fileName+'名单通过审核？')){
						ajaxFileUpload(fileName);
					}
				}else if(uploadFlag=='unPass'){
					if(confirm('将'+fileName+'名单拒絕审核？')){
						ajaxFileUpload(fileName,uploadFlag);
					}
				}		

			});
			
			function ajaxFileUpload(fileName,uploadFlag){
				var url = '${currentContextPath}/begin/fileUploadPass';
				if(uploadFlag=='unPass'){
					url = '${currentContextPath}/begin/fileUploadReject';
				}	
			    $.ajaxFileUpload({  
			        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用) 
			    	url:url,  
			        secureuri:false,                           //是否启用安全提交,默认为false   
			        fileElementId:'J-upload-file',               //文件选择框的id属性 
			        contentType:'text',                           //服务器返回的格式,可以是json或xml等  
			        success:function(data){					      //服务器响应成功时的处理函数 
 			        	
 			        	var obj = eval("("+data.replace("<pre>","").replace("</pre>","")+")");  
			        	//var obj = $.parseJSON(data);
			        	if(obj.status == -1){
 			        		alert("档案笔数超过最低限制!");
 			        		return;
 			        	}else if(obj.status == 0){
 			        		alert("批量审核失败!");
 			        		return;
			        	}
			        	alert(fileName+' 名單已通過審核');
			        	window.location = "${currentContextPath}/begin/toBindCardAppeal";
			        },  
			        error:function(data, status, e){ //服务器响应失败时的处理函数  
			            $('#result').html('批量审核失败!');  
			        }  
			    });  
			}  
			
			//確認修改
			$("#confirmCheck").click(function(){
				var obj = new Object()
				$("#list input[type=radio]:checked").each(function(i){
					var key = this.name;
					obj[key] = this.value;
				});
				if(Object.keys(obj) != ''){
					$.ajax({
	                    url:'${currentContextPath}/begin/updateCheckStatus',
	                    method:"post",
	                    data:{data:obj},
	                    success:function(data){
	                    	window.location = "${currentContextPath}/begin/toBindCardAppeal";
	                    }
	                });
				}else{
					alert("请选择审核项目!")
				}
			});	
		});
		
			
		function doPre(){
			var currentPageNo = $("#pageNo").val();
			var action = "toBindCardAppeal";
			if($("#status").val() == 3){
				action = "toCancelList";	
			}
			starttime = $('#timestart').val();
			endtime = $('#timeend').val(); 
			starttime = starttime.replace(/-/g,"/");
			endtime = endtime.replace(/-/g,"/");
			var startDate = new Date(starttime);
			var endDate = new Date(endtime);
			endDate.setHours(23)
			endDate.setSeconds(59);
			endDate.setMinutes(59);
			location.href= "${currentContextPath}/begin/" + action + "?pageNo="+(parseInt(currentPageNo)-1)+"&status="+$("#status").val()
				+"&timeStart=" +startDate.getTime() + "&timeEnd=" + endDate.getTime();
			location.assign();
		}

		function doNext(){
			var currentPageNo = $("#pageNo").val();
			var action = "toBindCardAppeal";
			if($("#status").val() == 3){
				action = "toCancelList";	
			}
			starttime = $('#timestart').val();
			endtime = $('#timeend').val(); 
			starttime = starttime.replace(/-/g,"/");
			endtime = endtime.replace(/-/g,"/");
			var startDate = new Date(starttime);
			var endDate = new Date(endtime);
			endDate.setHours(23)
			endDate.setSeconds(59);
			endDate.setMinutes(59);
			location.href= "${currentContextPath}/begin/" + action + "?pageNo="+(parseInt(currentPageNo)+1)+"&status="+$("#status").val()
				+"&timeStart=" + startDate.getTime() + "&timeEnd=" + endDate.getTime();
			location.assign();
		}

		function doForward(index){
		    if(index == -1){
		    	var reg = /^[0-9]+$/;
		    	if(reg.test($("#forwardPage").val())){
				$("#pageNo").val(parseInt($("#forwardPage").val()));}
		    	else{
		    		return;
		    	}
		    }else{ 
		    	$("#pageNo").val(index);
		    }
		    var action = "toBindCardAppeal";
			if($("#status").val() == 3){
				action = "toCancelList";	
			}
			starttime = $('#timestart').val();
			endtime = $('#timeend').val(); 
			starttime = starttime.replace(/-/g,"/");
			endtime = endtime.replace(/-/g,"/");
			var startDate = new Date(starttime);
			var endDate = new Date(endtime);
			endDate.setHours(23)
			endDate.setSeconds(59);
			endDate.setMinutes(59);
			location.href= "${currentContextPath}/begin/" + action + "?pageNo="+$("#pageNo").val()+"&status="+$("#status").val()
				+"&timeStart="+ startDate.getTime() + "&timeEnd=" + endDate.getTime();
			location.assign();
		}

		function doCurrent(pageNo){
			var action = "toBindCardAppeal";
			if($("#status").val() == 3){
				action = "toCancelList";	
			}
			starttime = $('#timestart').val();
			endtime = $('#timeend').val(); 
			starttime = starttime.replace(/-/g,"/");
			endtime = endtime.replace(/-/g,"/");
			var startDate = new Date(starttime);
			var endDate = new Date(endtime);
			endDate.setHours(23)
			endDate.setSeconds(59);
			endDate.setMinutes(59);
			location.href= "${currentContextPath}/begin/" + action + "?pageNo="+pageNo+"&status="+$("#status").val()
				+"&timeStart="+ startDate.getTime() + "&timeEnd=" + endDate.getTime();
			location.assign();
		}	
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>
</body>
</html>