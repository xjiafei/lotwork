$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	var editor = $('#J-content').xheditor({upImgUrl:"upload.php",upImgExt:"jpg,jpeg,gif,png"});
	var form = $('#J-form');
	
	var checkTitle = function(){
		var title = $('#J-input-title'),titlev = $.trim(title.val()),isPass = false;
		if(titlev == ''){
			title.parent().find('.ui-check').html('<i></i>主题不能为空').show();
			isPass = false;
			title.focus();
		}else{
			title.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-title').blur(function(){
		checkTitle();
	});
	
	var checkContent = function(){
		var content = $('#J-content'),contentv = $.trim(content.val()),isPass = false;
		if(contentv == ''){
			content.parent().parent().find('.ui-check').html('<i></i>内容不能为空').show();
			isPass = false;
			content.focus();
		}else{
			content.parent().parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-content').blur(function(){
		checkContent();
	});
	
	
	$('#J-button-submit').click(function(){
		if(checkTitle() && checkContent()){
			form.submit();
		}
	});
});