<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>题目录入</title>
	<style>
		.panel-field-urgent {display:none;}
		.ui-form .J-panel-group {margin:0;}
		.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
		.checkbox-list-last {border:0;}
	</style>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 题目录入</div></div>
			<div class="col-content">
			<div class="col-main">
				<div class="main" id="DivRules"></div>
				<div >
					<div align="right">
					<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_ADD">
						<button class='btn btn-small'  id='addQuestion' style='position:initial'>添加题目</button>
					</permission:hasRight>
					<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_CANCEL">
						<button class='btn btn-small'  id='cancelQuestion' style='position:initial'>取消修改</button>
					</permission:hasRight>
					<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_CONFIRM">
						<button class='btn btn-small'  id='confirmQuestion' style='position:initial' >确认修改</button>
					</permission:hasRight>
					</div>
					<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
						<table class="table table-info" id="questionTbd">
							<thead>
								<tr>
									<th class="text-center w-4">序号</th>
									<th class="text-center w-4">问题描述</th>
									<th class="text-center w-4">正确答案</th>
									<th class="text-center w-4">错误答案</th>
									<th class="text-center w-4">错误答案</th>
									<th class="text-center w-4">操作</th>
								</tr>
							</thead>
							<tbody>
							<tr style="display:none" id="copyRow">
									<td class="text-center w-4"></td>
									<td class="text-center w-4" style="display:none" questionName="id"></td>																											
									<td class="text-center w-4" questionName="question"></td>									
									<td class="text-center w-4" questionName="crtAnswer"></td>
									<td class="text-center w-4" questionName="errAnswer1"></td>
									<td class="text-center w-4" questionName="errAnswer2"></td>
									<td class="text-center w-4">
										<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_UPDATE">
											<button class='btn btn-small' href='javascript:void(0);' name='editQuestion' style='position:initial'>修改</button>									
										</permission:hasRight>
										<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_DELETE">
											<button class='btn btn-small' href='javascript:void(0);' name='delete' style='position:initial'>刪除</button>	
										</permission:hasRight>
									</td>									
								</tr>
								<c:forEach items="${questions}" var="question" varStatus="status">
								<tr>
									<td class="text-center w-4">${question.index}</td>
									<td class="text-center w-4" style="display:none" questionName="id"><input type="hidden" name="quesId" value="${question.id}"></td>																		
									<td class="text-center w-4" questionName="question">${question.question}</td>									
									<td class="text-center w-4" questionName="crtAnswer">${question.crtAnswer}</td>
									<td class="text-center w-4" questionName="errAnswer1">${question.errAnswer1}</td>
									<td class="text-center w-4" questionName="errAnswer2">${question.errAnswer2}</td>
									<td class="text-center w-4">
										<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_UPDATE">									
											<button class='btn btn-small' href='javascript:void(0);'  name='editQuestion' style='position:initial'>修改</button>
										</permission:hasRight>
										<permission:hasRight moduleName="BEGIN_NEW_QUESTUIN_DELETE">										
											<button class='btn btn-small' href='javascript:void(0);'  name='delete' style='position:initial'>刪除</button>	
										</permission:hasRight>
									</td>									
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>	
						</div>
					</div>
				</div>
	
				<!-- pop up window -->			
				  <div class="pop w-13" id="QuestionWindow" style="display:none;">
			       <div class="hd">
			        <i class="close" name="closeIcoDiv1"></i>
			        <h3 align="center"></h3>
					</div>
					<div class="bd">
							问题描述-最多50字<br>
							<input id="question"  margin-left:80px" type="text" size="95" maxlength="50" ><br><br>				
							正确选项-最多20字<br>
							<input id="crtAnswer"  margin-left:80px" type="text" size="40" maxlength="20" ><br><br>
							错误选项-最多20字<br>	
							<input id="errAnswer1"  margin-left:80px" type="text" size="40" maxlength="20" ><br><br>		
							错误选项-最多20字<br>			
							<input id="errAnswer2"  margin-left:80px" type="text" size="40" maxlength="20" ><br><br>	
							<span center>
								<input type="button" id="questionConfirm"  class="btn btn-important"  value="确认"  style="width:80px;"/>
								<input type="button" class="btn" name="closeIcoDiv1" value="撤销编辑"  style="width:80px;"/>				
							</span>
			      </div>
	  			</div>	
				
<script>

var updateIndex=-1;
var actionFlag="";

function showAddWindow(){
	box1.OverLay.Color = "rgb(51, 51, 51)" ; 
	  box1.Over = true;   
	  box1.OverLay.Opacity = 50;  
	  box1.Fixed = true;	 
	  box1.Center = true;
	  $("#QuestionWindow h3").text("添加题目");
	  $("#QuestionWindow input:text").each(function(){
		  $(this).val("");
	  });
	  actionFlag="add";
	  box1.Show();
}

function showUpdateWindow(){
	  var tr = $(this).parent().parent();
	  updateIndex=$(tr).index();
	  box1.OverLay.Color = "rgb(51, 51, 51)" ; 
	  box1.Over = true;   
	  box1.OverLay.Opacity = 50;  
	  box1.Fixed = true;	 
	  box1.Center = true;
	  $("#QuestionWindow h3").text("修改题目");
	  $("#QuestionWindow input:text").each(function(){
		  $(this).val($(tr).find("td[questionName="+$(this).attr("id")+"]").text());
	  });
	  actionFlag="update";
	  box1.Show();
}

function addQuestion(){
	$("#copyRow").children().each(function(){
		  var td = this;
		  $(td).text($("#"+$(td).attr("questionName")).val());
	  });
	 $("#questionTbd tbody ").append("<tr>"+$("#copyRow").html()+"</tr>");	
	 
	 $("#questionTbd tbody tr:last td").each(function(){
		 $(this).find("[name=editQuestion]").click(showUpdateWindow);
		 $(this).find("[name=delete]").click(deleteQuestion);		 
	 });
	 resetIndex();
	 box1.Close();
}

function updateQuestion(){
	  $("#questionTbd tbody tr").eq(updateIndex).children().each(function(){
		  var td = this;
		  $(td).text($("#"+$(td).attr("questionName")).val());
	  });
	  updateIndex = -1;
	  box1.Close();
}

function deleteQuestion(){
	var quesId = $(this).parent().parent().find("[name=quesId]").val();
	if(quesId>0){
		$.ajax({
	        url:'/begin/updateDeleteFalg',
	        type: "POST",
	        contentType : "application/json",
	        data: JSON.stringify({deleteId:quesId})
	    });
	}else{
		alert(8);
	}
	$(this).parent().parent().remove();
	resetIndex();
}

function questionConfirm(){
	var isCompelete = false;	
	$("#QuestionWindow input:text").each(function(){
		  if($(this).val().length===0){
			  isCompelete = true;
		  }
	  });
	  if(isCompelete){
		  alert("问题与答案请完整输入");
		  return false;
	  }
	
	if("add"==actionFlag){
		addQuestion();
	}else if("update"==actionFlag){
		updateQuestion();
	}
	  actionFlag="";
}

function saveQuestion(){
	var questionArray = new Array();
	 $("#questionTbd tbody tr:not(:first)").each(function(){
		 var question = new Object();

		 $(this).find("td").each(function(){
			 if(typeof($(this).attr("questionName")) != "undefined"){
				 if($(this).attr("questionName")=="id"){
					 question.id=$(this).find("[name=quesId]").val();				 
				 }else{
					 question[$(this).attr("questionName")] =$(this).text();				 
				 }
			 }
		 });
		 questionArray[questionArray.length]=question;
	 });
	 
	 if(questionArray.length<3){
		 alert('题目总数至少需输入三笔');
		 return false;
	 }
	 
	$.ajax({
        url:'/begin/saveQuestion',
        type: "post",
        contentType : "application/json",
        data: JSON.stringify({pageNo:$("#pageNo").val(),questions:questionArray}),
        beforeSend: function () {
            isLock = false;
            TableStyle("DivRules", 19, 1, "保存中");
            return;
        },
        success:function(){
        	location.href= "${currentContextPath}/begin/toNewQuestion";
    		location.assign();
        },
        complete: function ()
        {
            isLock = true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            TableStyle("DivRules", 17, 2, "数据异常");
        }
    });
}


function resetIndex(){
/**
	$("#questionTbd tbody tr :not(:first)").each(function(index){
		$(this).find("td").first().text(index+1);
	});
	var rowNum = "共"+$("#questionTbd tbody tr :not(:first)").size()+"条记录";
	$(".lower").text(rowNum);*/
}


(function() {	
	option = {zIndex:500},	
    box1 = new LightBox("QuestionWindow",option);	
	 
	$("#addQuestion").click(showAddWindow);
	
	$("[name=editQuestion]").click(showUpdateWindow);
	
	$("[name=delete]").click(deleteQuestion);

	$("#questionConfirm").click(questionConfirm);
	
	$(document).on('click', '[name="closeIcoDiv1"]', function(e){
		box1.Close();
		actionFlag="";
		updateIndex=-1;
	});
	
	$("#cancelQuestion").click(function(){
		location.href= "${currentContextPath}/begin/toNewQuestion";
		location.assign();
	});
	
	$("#confirmQuestion").click(saveQuestion);
})();	

function excelComfire(){
	var formData = new FormData();
	formData.append('file', $('input[type=file]')[0].files[0]);
	
}

	
function doPre(){
	var currentPageNo = $("#pageNo").val();
	location.href= "${currentContextPath}/begin/searchQuestion?pageNo="+(parseInt(currentPageNo)-1);
	location.assign();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	location.href= "${currentContextPath}/begin/searchQuestion?pageNo="+(parseInt(currentPageNo)+1);
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
	location.href= "${currentContextPath}/begin/searchQuestion?pageNo="+$("#pageNo").val();
	location.assign();
}

function doCurrent(pageNo){
	location.href= "${currentContextPath}/begin/searchQuestion?pageNo="+pageNo;
	location.assign();
}	
	
	
</script>		

	<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adNoticeModify.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>	
</body>