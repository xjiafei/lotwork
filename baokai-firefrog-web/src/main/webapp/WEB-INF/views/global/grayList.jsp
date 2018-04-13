<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
		<title>我是灰名單</title>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/global/whiteList.js"></script>
		<script>
			var currentPath = "${currentContextPath}";
		</script>
	</head>
	<body>
		
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-whiteList-form" action="${currentContextPath}/globeAdmin/searchWhiteList" method="post">
							<input type="text" id ="userName">
							<input type="button" id="j-submit" value="看上級按我" onclick="jsubmit();">
							<input type="button" id="j-submit" value="看下級按我" onclick="jsubmit2();">
						</form>		
						<div id="dataDiv">
						</div>	
					</div>
				</div>
			</div>
<script>
function jsubmit(){
	var jsonStr = "";
	jsonStr += '{"account":';
	jsonStr += '"'+$("#userName").val()+'"';
	jsonStr += '}';
	
	jQuery.ajax({
		type:  "post",
		url: currentPath+"/globeAdmin/queryGrayList",
		dataType:'json', 
		contentType: "application/json; charset=utf-8",
		data: jsonStr,
		success:function(data){
			var htmlData = "<table>";
			$.each(data.body.result.userData, function(key,value) {
				var lv = "";
				if(value.level == null){
					lv  ="白名單"
				}else if(value.level ==1){
					lv  ="沉默用戶"
				}else if(value.level ==6){
					lv  ="風險沉默用戶(低)"
				}else if(value.level ==7){
					lv  ="風險用戶(中)"
				}else if(value.level ==8){
					lv  ="風險用戶(高)"
				}
				htmlData += "<tr style='border:2px solid gray'><td style='border:2px solid gray'>"+value.account+"</td><td style='border:2px solid gray'>"+lv+"</td></tr>"
				
				});
			htmlData += "</table>"
			$("#dataDiv").html(htmlData);
		},
		error: function(){
			operationFailure();
		}
	});
}


function jsubmit2(){
	var jsonStr = "";
	jsonStr += '{"account":';
	jsonStr += '"'+$("#userName").val()+'"';
	jsonStr += '}';
	
	jQuery.ajax({
		type:  "post",
		url: currentPath+"/globeAdmin/queryGrayList2",
		dataType:'json', 
		contentType: "application/json; charset=utf-8",
		data: jsonStr,
		success:function(data){
			var htmlData = "<table>";
			$.each(data.body.result.userData, function(key,value) {
				var lv = "";
				if(value.level == null){
					lv  ="白名單"
				}else if(value.level ==1){
					lv  ="沉默用戶"
				}else if(value.level ==6){
					lv  ="風險沉默用戶(低)"
				}else if(value.level ==7){
					lv  ="風險用戶(中)"
				}else if(value.level ==8){
					lv  ="風險用戶(高)"
				}
				htmlData += "<tr style='border:2px solid gray'><td style='border:2px solid gray'>"+value.account+"</td><td style='border:2px solid gray'>"+lv+"</td></tr>"
				
				});
			htmlData += "</table>"
			$("#dataDiv").html(htmlData);
		},
		error: function(){
			operationFailure();
		}
	});
}


</script>

	</body>
</html>