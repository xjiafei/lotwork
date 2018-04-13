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
<title><spring:message code="deviceManage" /></title>
     <link href="easyui/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="easyui/js/outlook2.js"> </script>
    <script type="text/javascript" src="easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript">  
    var language=window.navigator.language; 
    var userLanguage="${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}";  
    if(null != userLanguage&&userLanguage!=""){//not login  
    	language = userLanguage;    }   
    $(function(){        
    	var src = '<%=basePath%>easyui/locale' + '/easyui-lang-'+language.replace("-","_")+'.js';// when login in China the language=zh-CN
    	$.getScript(src);    });
    </script>
    <script type="text/javascript" src="easyui/js/json2.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.edatagrid.js"></script>
    
 
 <script type="text/javascript">
		$( function(){
				var datagridInstance; //定义全局变量datagrid
	            var editIndex = undefined; //定义全局变量：当前编辑的行
	            var firstVal = $("#firstUserId").val();
	            $.ajax({
	                url:"/device/userList",
	                type:"post",
	                datatype:"json",
	                success:function(data){
	                    $('#userId').combobox({ 
	                        data:data,
	                        valueField:'id', 
	                        textField:'name',
	                        onLoadSuccess: function () { //加载完成后,设置选中第一项
	                            var val = $(this).combobox("getData");
	                        	
	                            for (var item in val[0]) {
	                                if (item == "id") {
	                                	if(firstVal==null||firstVal==''){
	                                    	$(this).combobox("select", val[0][item]);
	                                	}else{
	                                		$(this).combobox("select", firstVal);
	                                	}
	                                }
	                            }
	                        }
	                    });        
	                }
	            });
	            
	            $('#w3').window('close');

	            $('#btnEp3').click(function() {
	            	uploadExcel();
	            });

	   			$('#btnCancel3').click(function(){$('#w3').window('close');});
	   			
	   			$("#userId").combobox({

	   				onChange: function (n,o) {

	   					queryDevice();

	   				}

	   				});
	   			var time = new Date();
	            
	            datagridInstance = $('#userList').datagrid({
					url:'device/deviceList?time='+time.getMilliseconds(),
					method: 'get',
					title: '<spring:message code="deviceQuery"/>',
					fitColumns: true,
					idField: 'id',
					singleSelect: false,
					rownumbers: true,
					pagination:true,
					columns:[[
						{field:'id', title: '<spring:message code="number"/>', width:20 , hidden: true},
						{field:'code',title:'<spring:message code="deviceNumber"/>',width:60},
						{field:'address',title:'<spring:message code="address"/>',width:60},
						{field:'createTimeString',title:'<spring:message code="createTime"/>',width:40,sortable:"true"},
						{field:'onfflineName',title:'<spring:message code="onff"/>',width:20,sortable:"true"},
						{field:'battery',title:'<spring:message code="battery"/>',width:20,sortable:"true"},
						{field:'updateTimeString',title:'<spring:message code="lastUpdateTime"/>',width:30,sortable:"true"},
						{field:'statusName',title:'<spring:message code="lastUpdateStatus"/>',width:30,sortable:"true"},
						{field:'updateName',title:'<spring:message code="updator"/>',width:30}
					]],
					toolbar:'#searchTabId'
				});
	            if(firstVal!=null&&firstVal!=''){
	            	queryDevice();
	            }
		}
		);
		function queryDevice() {
			var code = $("#code").val();
			var userId = $("#userId").combobox("getValue");
			var firstVal = $("#firstUserId").val();
			if(firstVal==null||firstVal==''){
				firstVal = userId;
			}
			var time = new Date();
			$('#userList').datagrid('load',
				{
					userId:firstVal,
					code:code,
					time:time.getMilliseconds()
				});
			$("#firstUserId").val('');
		};
		function cleanQueryDevice() {
			$("#code").val("");
			var val = $('#userId').combobox("getData");
            for (var item in val[0]) {
                if (item == "id") {
                    $('#userId').combobox("select", val[0][item]);
                }
            }
		};
		
		
		function batchDeleteDevice(){
	    	var rows = $('#userList').datagrid('getSelections');  
	        if(rows.length > 0){  
	            $.messager.confirm('<spring:message code="confirm"/>','<spring:message code="confirmMessage"/>', function(option){  
	                if(option){  
	                    //一般将id的一个集合传到后台删除  
	                    var ids = '';  
	                    for(var i = 0; i< rows.length; i++){  
	                        ids = ids+','+rows[i].id;
	                    }  
	                    var time = new Date();
	                    $.ajax({
	                    	url : '/device/deleteDevice?time='+time.getMilliseconds(),
	                    	data : {
	                    		ids : ids
	                    	},
	                    	dataType : 'json',
	                    	success : function(r){
	                    		 $('#userList').datagrid('load');
	                    		 $('#userList').datagrid('unselectAll');
	                    		 if(r==0){
	                    			 $.messager.show({
		                    			 title : '<spring:message code="prompt"/>',
		                    			 msg : '<spring:message code="fail"/>'
		                    		 });
	                    		 }
	                    	}
	                    });
	                }  
	            });  
	        }else{  
	            $.messager.alert('<spring:message code="prompt"/>', '<spring:message code="pleaseSelect"/>', 'error');
	        }
	    }
		
		
		function uploadExcel(){     
	       //得到上传文件的全路径  
	       var fileName= $('#uploadExcel').filebox('getValue');  
	       var userId = $("#userId").combobox("getValue");
	     //进行基本校验  
           if(fileName==""){     
              $.messager.alert('<spring:message code="prompt"/>','<spring:message code="pleaseSelectUploadFile"/>','info');   
              return;
           }if(userId==""){
    		   $.messager.alert('<spring:message code="prompt"/>','<spring:message code="pleaseSelectUser"/>','info');
    		   $('#w3').window('close');
    		   return;
    	   }else{  
               //对文件格式进行校验  
               var d1=/\.[^\.]+$/.exec(fileName);   
               if(d1==".xls"||d1==".xlsx"){ 
                    //提交表单  
                    var time = new Date();
                    document.getElementById("questionTypesManage").action="/device/batchAddDevice?userId="+userId+"&time="+time.getMilliseconds();  
                    document.getElementById("questionTypesManage").submit();     
                    $('#w3').window('close');
              }else{  
                  $.messager.alert('<spring:message code="prompt"/>','<spring:message code="pleaseSelectXls"/>','info');   
                  $('#uploadExcel').filebox('setValue','');   
              }  
           }
	 }  
		
	function batchSaveDevice(){
		$('#uploadExcel').filebox('setValue','');
		$('#w3').window('open');
	}
	function ssssss(){
		var time = new Date();
		document.getElementById("questionTypesManage").action="/resource/downLoad?time="+time.getMilliseconds();  
		document.getElementById("download").submit();
	}
	
	function publish(){
		var userId = $("#userId").combobox("getValue");
		var userName = $("#userId").combobox("getText");
		if(userId==""){
 		   $.messager.alert('<spring:message code="prompt"/>','<spring:message code="pleaseSelectUser"/>','info');
 		   return;
 	    }
		var time = new  Date();
		$.messager.confirm('<spring:message code="confirm"/>','<spring:message code="confirmMessagePublish"/>', function(option){  
            if(option){
				$.ajax({
		        	url : '/resource/publish?time='+time.getMilliseconds(),
		        	data : {
		        		userId : userId
		        	},
		        	dataType : 'json',
		        	success : function(r){
		        		 if(r.code==0){
		        			 $.messager.alert('<spring:message code="confirm"/>', '<spring:message code="publishFail"/>', 'error');
		        		 }else if(r.code == 1){
		        			 $.messager.alert('<spring:message code="confirm"/>','<spring:message code="waitUpload"/>','info');   
		        		 }
		        		 else if(r.code == 2){
		        			 $.messager.alert('<spring:message code="confirm"/>','<spring:message code="addFile"/>','info');   
		        		 }
		        		 else if(r.code == 3){
		        			 $.messager.alert('<spring:message code="confirm"/>','<spring:message code="notNeedPublish"/>','info');   
		        		 }
		        	}
		        });
            }
		}); 
	}
	
	
	
	
	
	</script>
</head>

<body >

<div  id="userList">
<div id="searchTabId" style="padding:3px">
	
	<span><spring:message code="user"/>:</span>
	<input id="userId" class="easyui-combobox" data-options="
		url:'/device/userList',
		valueField: 'id',
		textField: 'name',
		panelHeight:75" />
	<span><spring:message code="deviceNumber"/>:</span>
	<input id="code" style="line-height:20px;border:1px solid #ccc">
	<input id="firstUserId" type="hidden" value="${userId}"/>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onclick="queryDevice()"><spring:message code="deviceQuery"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-clear" onclick="cleanQueryDevice()"><spring:message code="clearup"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-print" onclick="ssssss()"><spring:message code="template"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-save" onclick="batchSaveDevice()"><spring:message code="batchImportDevice"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-redo" onclick="publish()"><spring:message code="publishFile"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-remove" onclick="batchDeleteDevice()"><spring:message code="batchDeleteDevice"/></a>
</div>
</div>

<div id="w3" class="easyui-window" title='<spring:message code="importDevice"/>' collapsible="false" minimizable="false"
		        maximizable="false" icon="icon-save"  style="width: 350px; height: 150px; padding: 5px;
		        background: #fafafa;" >
      		<div class="easyui-layout" fit="true">
    			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<form id="questionTypesManage"  method="post" enctype="multipart/form-data">  
					   <spring:message code="selectFile"/>：<input id="uploadExcel" name="uploadExcel" class="easyui-filebox" style="width:180px;height:25px" data-options="prompt:'请选择excel文件...'">　     　　　　　      
					</form>
        		</div>
			    <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
			    	<a id="btnEp3" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" ><spring:message code="upload"/></a> 
			    	<a id="btnCancel3" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"><spring:message code="cancel"/></a> 
			    </div>
		</div>
</div>
<form id="download"  method="post" action="/resource/downLoad"></form>

</body>
</html>
