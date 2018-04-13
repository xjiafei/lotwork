<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>临时功能-金牌录入</title>
</head>

<body>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong>临时功能&gt;金牌录入
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-content ui-tab-content-current">
						<form action="${currentContextPath}/adAdmin/medals" id="J-form-1" method="post" >
							<ul class="ui-search">
								<li><label for="" class="ui-label">金牌数量：</label> 
								<input type="text" class="input w-1" disabled="" value="${medalsNo}"/>
								</li>
								<li><label for="" class="ui-label">修改金牌数量：</label> 
								<input id ="input-id" type="text" class="input w-1" />
								</li>
								<li><a id="button-submit" class="btn btn-important" href="javascript:void(0);">修改<b class="btn-inner"></b></a></li>
							</ul>					
						</form>						
					</div>
				</div>
			</div>
		</div>
	</div>
<script>

$('#input-id').blur(function() {
	var str = $('#input-id').val().trim();
	var num = str.replace(/\D/gi,'');
		if(num <= 0){
			$('#input-id').val("0");
		}else{
			$('#input-id').val(num);
		}
});


$("#button-submit").click(function(){
	var submit = false;
	var str = $('#input-id').val().trim();
	var actionFrom = $('#J-form-1').attr('action');
	if(str == ''){
		alert('请输入数字');
		return;
	}else{
		var num = str.replace(/\D/gi,'');
		submit = true;
		actionFrom += '?num='+num;
	}
	
	if(submit){
		$.ajax({
			url:actionFrom,
			dataType:'json',
			method:'get',
			success:function(data){
				location.reload();
			},
		    error: function (ajaxContext) {
		    	 $('#input-id').val(0);
		        location.reload();
		     }

		});
	}
});
</script>
</body>

