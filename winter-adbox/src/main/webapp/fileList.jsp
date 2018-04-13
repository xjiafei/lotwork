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
<title><spring:message code="fileManage"/></title>
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
	            var firstVal = $("#firstUserId").val();
	            var time= new Date();
	            $.ajax({
	                url:"/device/userList?time="+time.getMilliseconds(),
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
	            var message = $("#message").val();
	            if(message==null||message==""){
	            $('#w3').window('close');}
	            else{
	            	$.messager.alert('<spring:message code="prompt"/>','<spring:message code="fileMax"/>','info');
	            }

	            $('#btnEp3').click(function() {
	            	uploadExcel();
	            });

	   			$('#btnCancel3').click(function(){$('#w3').window('close');});
	   			
	   			$("#userId").combobox({

	   				onChange: function (n,o) {

	   					queryRes();

	   				}

	   				});
	            
	            datagridInstance = $('#resList').datagrid({
					url:'resource/getResList?',
					method: 'get',
					title: '<spring:message code="fileSelect"/>',
					fitColumns: true,
					idField: 'id',
					singleSelect: false,
					rownumbers: true,
					pagination:false,
					columns:[[
						{field:'id', title: '<spring:message code="number"/>', width:20 , hidden: true},
						{field:'fileName',title:'<spring:message code="fileName"/>',width:35},
						{field:'filePath',title:'<spring:message code="filePath"/>',width:60},
						{field:'createTimeString',title:'<spring:message code="uploadTime"/>',width:20},
						{field:'createName',title:'<spring:message code="uploadUser"/>',width:20}
					]],
					toolbar:'#searchTabId'
				});
	            queryRes();
		}
		);
		function queryRes() {
			var userId = $("#userId").combobox("getValue");
			var firstVal = $("#firstUserId").val();
			if(firstVal==null||firstVal==''){
				firstVal = userId;
			}
			 var time= new Date();
			$('#resList').datagrid('load',
				{
					userId:firstVal,
					time:time.getMilliseconds()
				});
			$("#firstUserId").val('');
		};
		function batchDelete(){
	    	var rows = $('#resList').datagrid('getSelections'); 
	    	 var userName = $("#userId").combobox("getText");
	        if(rows.length > 0){  
	            $.messager.confirm('<spring:message code="confirm"/>','<spring:message code="confirmMessage"/>', function(option){  
	                if(option){  
	                    //一般将id的一个集合传到后台删除  
	                    var ids = '';  
	                    for(var i = 0; i< rows.length; i++){  
	                        ids = ids+','+rows[i].id;
	                    }  
	                    var time= new Date();
	                    $.ajax({
	                    	url : '/resource/deleteResource',
	                    	data : {
	                    		ids : ids,
	                    		time:time.getMilliseconds()
	                    	},
	                    	dataType : 'json',
	                    	success : function(r){
	                    		 $('#resList').datagrid('load');
	                    		 $('#resList').datagrid('unselectAll');
	                    		 if(r==0){
	                    			 $.messager.alert('<spring:message code="prompt"/>', '<spring:message code="fail"/>', 'error');
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
               if(d1==".jpg"||d1==".jpeg"||d1==".png"||d1==".bmp"||d1==".mp4"||d1==".avi"||
            		   d1==".JPG"||d1==".JPEG"||d1==".PNG"||d1==".BMP"||d1==".MP4"||d1==".AVI"){ 
                    //提交表单  
                    
                    $.messager.progress({ title:'<spring:message code="wait"/>', msg:'<spring:message code="uploading"/>'});
                    
                    var time= new Date();
                    document.getElementById("questionTypesManage").action="/resource/upload?userId="+userId+"&time="+time.getMilliseconds();  
                    document.getElementById("questionTypesManage").submit();
              }else{  
                  $.messager.alert('<spring:message code="prompt"/>','<spring:message code="rightFile"/>(jpg,jpeg,png,bmp,mp4,avi)！','info');   
                  $('#uploadExcel').filebox('setValue','');   
              }  
           }
	 }  
		
	function upload(){
		$('#uploadExcel').filebox('setValue','');
		$("#message").val("");		
		$('#w3').window('open');
	}
	
	function publish(){
		var userId = $("#userId").combobox("getValue");
		var userName = $("#userId").combobox("getText");
		var time= new Date();
		if(userId==""){
 		   $.messager.alert('<spring:message code="prompt"/>','<spring:message code="pleaseSelectUser"/>','info');
 		   return;
 	    }
		$.messager.confirm('<spring:message code="confirm"/>','<spring:message code="confirmMessagePublish"/>', function(option){  
            if(option){
				$.ajax({
		        	url : '/resource/publish',
		        	data : {
		        		userId : userId,
		        		time:time.getMilliseconds()
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

<div  id="resList">
<div id="searchTabId" style="padding:3px">
	
	<span><spring:message code="user"/>:</span>
	<input id="userId" class="easyui-combobox" data-options="
		url:'/device/userList',
		valueField: 'id',
		textField: 'name',
		panelHeight:75" />
	<input id="firstUserId" type="hidden" value="${userId}"/>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-save" onclick="upload()" title="sdsfsdfsdf"><spring:message code="uploadFile"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-remove" onclick="batchDelete()"><spring:message code="batchDeviceFile"/></a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-redo" onclick="publish()"><spring:message code="publishFile"/></a>
	<font color="red" size="2"><spring:message code="publishNotice"/></font>
</div>
</div>

<div id="w3" class="easyui-window" title="<spring:message code="uploadFile"/>" collapsible="false" minimizable="false"
		        maximizable="false" icon="icon-save"  style="width: 450px; height: 160px; padding: 5px;
		        background: #fafafa;" >
      		<div class="easyui-layout" fit="true">
    			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<form id="questionTypesManage"  method="post" enctype="multipart/form-data">  
					   <spring:message code="selectFile"/>：<input id="uploadExcel" name="uploadExcel" class="easyui-filebox" style="width:280px;height:25px" data-options="prompt:'<spring:message code="selectFile"/>'">　   
					   <input id="message" type="hidden" value="${message}">　　　　　      
					</form>
					
        		</div>
			    <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
			    	<a id="btnEp3" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" > <spring:message code="upload"/></a> 
			    	<a id="btnCancel3" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"><spring:message code="cancel"/></a> 
			    </div>
		</div>
</div>


</body>
</html>
