<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<meta charset="utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title><spring:message code="userManage"/></title>
     <link href="easyui/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="easyui/js/outlook2.js"> </script>
    <script type="text/javascript" src="easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
    <!-- <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script> -->
    <script type="text/javascript" src="easyui/js/json2.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.edatagrid.js"></script>
    
 
 <script type="text/javascript">
		$( function(){
				var datagridInstance; //定义全局变量datagrid
	            var editIndex = undefined; //定义全局变量：当前编辑的行
	            ///修改密码
	            $('#w1').window('close');

	            $('#btnEp').click(function() {
	                serverLogin();
	            })

	   			$('#btnCancel').click(function(){$('#w1').window('close');})
	   			
	   			///新增用户
	   			
	   			 $('#w2').window('close');

	            $('#btnEp2').click(function() {
	                serverLogin2();
	            })

	   			$('#btnCancel2').click(function(){$('#w2').window('close');})
				var time = new Date();
	            datagridInstance = $('#userList').datagrid({
					url:'user/userList?time='+time.getMilliseconds(),
					method: 'get',
					title: '<spring:message code="userSelect"/>',
					fitColumns: true,
					idField: 'id',
					rownumbers: true,
					//singleSelect: true,
					
					pagination:false,
					columns:[[
						{field:'id', title: '<spring:message code="number"/>', width:60,hidden: true},
						{field:'name',title:'<spring:message code="userName"/>',width:60},
						/* {field:'creatorName',title:'创建人',width:60},
						{field:'updateName',title:'修改人',width:60}, */
						{field:'createTimeString',title:'<spring:message code="createTime"/>',width:80},
						{field:'updateTimeString',title:'<spring:message code="modifyTime"/>',width:80},
						{field:'操作',title:'<spring:message code="opearator"/>',width:30,align:'center',
							 formatter:function(value,row,index){
								var e = '<a href="javascript:void(0)" onclick="editrow('+row.id+')"><spring:message code="modifyPassword"/></a> ';
								return e;
							}
						},
					]],
					toolbar:'#searchTabId'
				});
		}
		);
		
		function editrow(id){
			$('#w1').window('open');
			$('#txtNewPass').val('');
			$('#txtRePass').val('');
			$('#userId').val(id);
		}
		
		function addUser(){
			$('#w2').window('open');
			$('#txtNewPass2').val('');
			$('#txtRePass2').val('');
			$('#txtUserName2').val('');
		}
		
		function queryUser() {
			var userName = $("#userName").val();
			$('#userList').datagrid('load',
				{
					userName:userName
				});
		};
		function cleanQueryUser() {
			$("#userName").val("");
		};
		
		
		function serverLogin() {
	         var $newpass = $('#txtNewPass');
	         var $rePass = $('#txtRePass');
	         var $userId = $('#userId');
	         
	         if ($newpass.val() == '') {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="inputPassword"/>', 'warning');
	             return false;
	         }
	         if ($rePass.val() == '') {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="inputPasswordAgain"/>', 'warning');
	             return false;
	         }
	         
	         if($newpass.val().length!=6){
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="sixPassword"/>', 'warning');
	             return false;
	         }

	         if ($newpass.val() != $rePass.val()) {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="towPasswordNotAgain"/>', 'warning');
	             return false;
	         }

	         $.getJSON('/user/setPassword?password=' + $newpass.val()+"&userId="+$userId.val(), function(msg) {
	        	 if(msg.code==1){
	             $newpass.val(null);
	             $rePass.val(null);
	             $('#w1').window('close');
	             queryUser();
	             }
	        	 else{
	        		 $newpass.val(null);
	                 $rePass.val(null);
	            	 msgShow('系统提示', msg.message, 'info');
	             }
	         })
	     }
		
		
		function serverLogin2() {
	         var $newpass = $('#txtNewPass2');
	         var $rePass = $('#txtRePass2');
	         var $userName = $('#txtUserName2');
	         
	         if ($userName.val() == '') {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="inputUser"/>', 'warning');
	             return false;
	         }
	         
	         if ($newpass.val() == '') {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="inputPassword"/>', 'warning');
	             return false;
	         }
	         if ($rePass.val() == '') {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="inputPasswordAgain"/>', 'warning');
	             return false;
	         }

	         if ($newpass.val() != $rePass.val()) {
	        	 msgShow('<spring:message code="prompt"/>', '<spring:message code="towPasswordNotAgain"/>', 'warning');
	             return false;
	         }

	         $.getJSON('/user/registerDo?password=' + $newpass.val()+"&userName="+$userName.val(), function(msg) {
	        	 if(msg.code==1){
	             $newpass.val(null);
	             $rePass.val(null);
	             $userName.val(null);
	             $('#w2').window('close');
	             queryUser();
	             }
	        	 else{
	        		 $newpass.val(null);
	                 $rePass.val(null);
	                 $userName.val(null);
	            	 msgShow('系统提示', msg.message, 'info');
	             }
	         })
	     }
	</script>
</head>

<body >

<div  id="userList">
<div id="searchTabId" style="padding:3px">
	<span><spring:message code="userName"/>:</span>
	<input id="userName" style="line-height:20px;border:1px solid #ccc">
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onclick="queryUser()"><spring:message code="selectUser"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton" iconCls="icon-clear"  onclick="cleanQueryUser()"><spring:message code="clearup"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-save" onclick="addUser()"><spring:message code="addUser"/></a>
</div>

<div id="w1" class="easyui-window" title="<spring:message code="modifyPassword"/>" collapsible="false" minimizable="false"
		        maximizable="false" icon="icon-save"  style="width: 300px; height: 155px; padding: 5px;
		        background: #fafafa;" >
      		<div class="easyui-layout" fit="true">
    			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			        <table cellpadding=3>
				        <tr>
				              <td><spring:message code="newPassword"/>：</td>
				              <td><input id="txtNewPass" type="password" class="txt01" /></td>
				        </tr>
				        <tr>
				              <td><spring:message code="surePassword"/>：</td>
				              <td><input id="txtRePass" type="password" class="txt01" /></td>
				              <input id="userId" type="hidden" value=""></input>
				        </tr>
			      </table>
        		</div>
			    <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
			    	<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" > <spring:message code="sure"/></a> 
			    	<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"><spring:message code="cancel"/></a> 
			    </div>
			</div>
</div>


<div id="w2" class="easyui-window" title='<spring:message code="addUser"/>' collapsible="false" minimizable="false"
		        maximizable="false" icon="icon-save"  style="width: 350px; height: 200px; padding: 5px;
		        background: #fafafa;" >
      		<div class="easyui-layout" fit="true">
    			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			        <table cellpadding=3>
			        	 <tr>
				              <td><spring:message code="userName"/>：</td>
				              <td><input id="txtUserName2" type="text" class="txt01" /></td>
				        </tr>
				        <tr>
				              <td><spring:message code="password"/>：</td>
				              <td><input id="txtNewPass2" type="password" class="txt01" /></td>
				        </tr>
				        <tr>
				              <td><spring:message code="surePassword"/>：</td>
				              <td><input id="txtRePass2" type="password" class="txt01" /></td>
				        </tr>
			      </table>
        		</div>
			    <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
			    	<a id="btnEp2" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" > <spring:message code="sure"/></a> 
			    	<a id="btnCancel2" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"><spring:message code="cancel"/></a> 
			    </div>
			</div>
</div>

</div>
</body>
</html>
