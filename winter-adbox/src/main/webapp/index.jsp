<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int userType = Integer.valueOf(request.getAttribute("userType").toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><spring:message code="adboxManage"/></title>
    <link href="<%=basePath%>easyui/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="<%=basePath%>easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>easyui/js/outlook2.js"> </script>
    <script type="text/javascript">  
    var language=window.navigator.language; 
    var userLanguage="${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}";  
    if(null != userLanguage&&userLanguage!=""){//not login  
    	language = userLanguage;    }   
    $(function(){        
    	var src = '<%=basePath%>easyui/locale' + '/easyui-lang-'+language.replace("-","_")+'.js';// when login in China the language=zh-CN
    	$.getScript(src);    });
    </script>
    <script type="text/javascript">
    var time= new Date();
	 var _menus = {
		                 "menus":[
						           {"menuid":"1","icon":"icon-sys","menuname":'<spring:message code="menu"/>',
							      "menus":[
							       <% if(userType ==1||userType==2){ %>
									{"menuid":"12","menuname":'<spring:message code="userManage"/>',"icon":"icon-role","url":"/user/toUserList?time="+time.getMilliseconds()},
									<%}%>
									{"menuid":"13","menuname":'<spring:message code="deviceManage"/>',"icon":"icon-class","url":"/device/toDeviceList?time="+time.getMilliseconds()},
									{"menuid":"14","menuname":'<spring:message code="fileManage"/>',"icon":"icon-page","url":"/resource/toResList?time="+time.getMilliseconds()}
								    ]}
				]};
	 

   
   //修改密码
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
             msgShow('<spring:message code="prompt"/>', '<spring:message code="modifyPasswordSuccess"/>', 'info');
             $newpass.val(null);
             $rePass.val(null);
             $('#w').window('close');
             }
        	 else{
        		 $newpass.val(null);
                 $rePass.val(null);
            	 msgShow('<spring:message code="prompt"/>', msg.message, 'info');
            	 $('#w').window('close');
             }
         })
     }
     
	 $(function() {
		 $('#w').window('close');
		 $('#editpass').click(function() {
             $('#w').window('open');
             $('#txtNewPass').val('');
             $('#txtRePass').val('');
         });

         $('#btnEp').click(function() {
             serverLogin();
         })

		$('#btnCancel').click(function(){$('#w').window('close');})
        $('#lgOut').click(function() {
            $.messager.confirm('<spring:message code="confirm"/>', '<spring:message code="loginOut"/>', function(r) {

                if (r) {
                	location.href = '/user/unLogin';
                }
            });
        })
	 });
    </script>
    </head>
    <body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
		<noscript>
	    	<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;"> 
	    		<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' /> 
	    	</div>
	    </noscript>
		<div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
		        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
		        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> 
		        <span style="float:right; padding-right:20px;" class="head">
		        	<spring:message code="welcome"/> ${loginUser.name }
		        	
		        	<a href="javascript:void(0)" id="editpass"><spring:message code="modifyPassword"/></a>
		        	<a href="javascript:void(0)" id="lgOut"><spring:message code="loginOut"/></a>
		        </span> 
	        	<span style="padding-left:10px; font-size: 16px; ">
	        		<spring:message code="adboxManage"/>
	        	</span> 
		</div>
		<div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
      		<div class="footer"><spring:message code="flootTitle"/></div>
    	</div>
		<div region="west" hide="true" split="true" title="<spring:message code="daohang"/>" style="width:180px;" id="west">
      		<div id="nav" class="easyui-accordion" fit="true" border="false"> 
    		<!--  导航内容 --> 
    
  			</div>
    	</div>
		<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
		     <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
		    	<div title="<spring:message code="welcomeTitle"/>" style="padding:20px;overflow:hidden; color:red; " >
		          <h1 style="font-size:24px;"><spring:message code="welcomeUse"/></h1>
		        </div>
			</div>
    	</div> 
    	<div id="w" class="easyui-window" title="<spring:message code="modifyPassword"/>" collapsible="false" minimizable="false"
		        maximizable="false" icon="icon-save"  style="width: 320px; height: 185px; padding: 5px;
		        background: #fafafa;" >
      		<div class="easyui-layout" fit="true">
    			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			        <table cellpadding=3>
			        	<tr>
				              <td><spring:message code="user"/>：</td>
				              <td>${loginUser.name }</td>
				        </tr>
				        <tr>
				              <td><spring:message code="newPassword"/>：</td>
				              <td><input id="txtNewPass" type="password" class="txt01" /></td>
				        </tr>
				        <tr>
				              <td><spring:message code="surePassword"/>：</td>
				              <td><input id="txtRePass" type="password" class="txt01" /></td>
				              <input id="userId" type="hidden" value="${loginUser.id}"></input>
				        </tr>
			      </table>
        		</div>
			    <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
			    	<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" ><spring:message code="sure"/></a> 
			    	<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"><spring:message code="cancel"/></a> 
			    </div>
			</div>
    </div>
	<div id="mm" class="easyui-menu" style="width:150px;">
	      <div id="mm-tabupdate">刷新</div>
	      <div class="menu-sep"></div>
	      <div id="mm-tabclose">关闭</div>
	      <div id="mm-tabcloseall">全部关闭</div>
	      <div id="mm-tabcloseother">除此之外全部关闭</div>
	      <div class="menu-sep"></div>
	      <div id="mm-tabcloseright">当前页右侧全部关闭</div>
	      <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	      <div class="menu-sep"></div>
	      <div id="mm-exit">退出</div>
    </div>
</body>
</html>