$(document).ready(function(){
	var editor = $('#J-content').xheditor({upImgUrl:"upload.php",upImgExt:"jpg,jpeg,gif,png"});
	var form = $('#J-form');
	
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
		if(checkContent()){
			form.submit();
		}
		
	});
	
	
});