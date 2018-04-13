<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>图片上传</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
    <link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
	<style>
	html,body {height:auto;}
	.thumbnail img {width:40px;height:auto}
	.thumbnail:hover,.thumbnail-current{border-color:#F60;}
	</style>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	
</head>
<body>

	<ul class="ui-form" id="J-list">
		<li class="thumbnail-list" id="imgPlaceHolderId"  >
			<label class="ui-label" >选择图片：</label>
		</li>
	</ul>

<script>
$(document).ready(function(){
	var urlViewPic = $('#urlViewPic', parent.document).val();
	
	//修改时回显数据
	$('#J-input-placeholder-li', parent.document).find('[name=J-input-placeholder-img]').each(function(){
		var tempImgSpan="<span class='thumbnail' data-id='1' ><div class='hd'><i class='close' onclick='removeImgSrc(this)' value='"+$(this).val()+"'></i><a href='"+urlViewPic+$(this).val()+"' target='_blank'>查看大图</a></div><img style='height: 90px;width:100px;' name='bg-header.jpg' alt='' src='"+urlViewPic+$(this).val()+"'></span>";
		$("#imgPlaceHolderId").append(tempImgSpan);
	})
	if('${isAddImg}'== '1'){
		if('${operater.status}' == '100'){
			var msg = {'status':'${operater.status}','message':'${operater.message}','fileUrl':'${operater.fileUrl}','operator':'select'};
			var tempImgSpan="<span class='thumbnail' data-id='1'><div class='hd'><i class='close' onclick='removeImgSrc(this)' value='${operater.fileUrl}'></i><a href='"+urlViewPic+"${operater.fileUrl}' target='_blank'>查看大图</a></div><img style='height: 90px;width:100px;' name='bg-header.jpg' alt='' src='"+urlViewPic+"${operater.fileUrl}'></span>";
			try{
				$("#imgPlaceHolderId").append(tempImgSpan);
				global_img_placeholder(msg);
			}catch(e){
			}
		}else{
			$('#J-panel-img-position',parent.document).find('.ui-check').html('<i></i>${operater.message}').show();
		}
	}
	
})
// 浏览器兼容问题，极速浏览器移除图片时无效果
function removeImgSrc(msg){
	var tempSrc = $(msg).attr("value");
	$('#J-input-placeholder-li', parent.document).find('[name=J-input-placeholder-img]').each(function(){
		if(tempSrc == $(this).val()){
			$(this).remove();
		}
	})
	$(msg).parent().parent().remove();
	
}
function global_img_placeholder(msg){
		if(Number(msg.status) == 100){
			$('#J-input-img-h', parent.document).addClass('input-disable').attr('readonly','readonly');
			$('#J-input-img-w', parent.document).addClass('input-disable').attr('readonly','readonly');
			if(msg.operator == 'select'){
				var imgHidden = "<input type ='text' name='J-input-placeholder-img' value='"+msg.fileUrl+"'>"
				$('#J-input-placeholder-li', parent.document).append(imgHidden)
				$('#J-panel-img-position',parent.document).find('.ui-check').hide();
			}
		}else{
		}
	}
	
$('#J-list').on('click', '.thumbnail', function(){
	var dom = $(this),id = dom.attr('data-id'),msg,cls = 'thumbnail-current';
	try{
		dom.parent().find('.thumbnail').removeClass(cls);
		dom.addClass(cls);
	}catch(e){
		
	}
});
</script>
</body>
</html>