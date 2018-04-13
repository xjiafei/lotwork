$(document).ready(function(){  
		var button = $('#J-feedback-button-no'),
			panel = $('#J-help-feedback-panel'),
			text = $('#J-help-feedback-text'),
			cancel = $('#J-help-feedback-cancel'),
			ID = 'J-reason-4',
			submit = $("#submit");
		button.click(function(e){
			e.preventDefault();
			text.hide();
			$("#J-help-message-panel-2").hide();
			$("#J-help-feedback-message").hide();
			$("#J-help-feedback-message").html("");
			$(".ui-prompt").hide();
			$("input[name='reason']")[0].checked=true;
			panel.show();
		});
		panel.find('input').click(function(){
			if(this.id == ID && !!this.checked){
				$(".ui-prompt").show();
				text.show();
			}else{
				$(".ui-prompt").hide();
				text.hide();
			}
		});
		cancel.click(function(e){
			e.preventDefault();
			$(".ui-prompt").hide();
			panel.find('input').attr('checked', false);
			text.hide();
			panel.hide();
		});
		$("[name='reason']").click(function(){
			$("#J-help-feedback-message").hide();
			$("#J-help-feedback-message").html("");
		});
		
		$('#J-feedback-button-yes').click(function(){
			panel.hide();
			$(".ui-prompt").hide();
			var url = baseUrl + "/help/addFeedBack";
			var helpId = $("#helpId").val();
			var helpCookie=$.cookie("phoenix_help_"+global_userID+"_"+helpId);
			if(helpCookie==null)
			{
				jQuery.ajax({
					type: "get",
					url: url,
					data : "helpId="+helpId+"&isSolved=1&num="+Math.random(),
					success: function(data,textStatus){
						$("#J-help-message-panel-2").html("<h4 class='pop-text'>您的反馈信息已经成功提交，谢谢您的反馈</h4>");
						$("#J-help-message-panel-2").show();
						$.cookie("phoenix_help_"+global_userID+"_"+helpId,helpId,30);
						setTimeout(function(){$("#J-help-message-panel-2").hide()},3000);
					},
					error: function(xhr,status,errMsg){
					alert("操作失败!");
					}
					});
			}else
			{
				$("#J-help-message-panel-2").html("<h4 class='pop-text'>您已提交过此次反馈</h4>");
				$("#J-help-message-panel-2").show();
				setTimeout(function(){$("#J-help-message-panel-2").hide()},3000);
			}
		});
		
		$('#submit').click(function(){
			var helpId = $("#helpId").val();
			var helpCookie=$.cookie("phoenix_help_"+global_userID+"_"+helpId);
			if(helpCookie==null)
			{
				var reasonId = $("input[name='reason']:checked").val();
				var reason = '';
				var isSolved = 0;
				if(reasonId == 3)
				{
					reason = $("#reasonText").val();
				}
				var url = baseUrl +"/help/addFeedBack";
				var helpId = $("#helpId").val();
				jQuery.ajax({
					type: "post",
					url: url,
					data : "helpId="+helpId+"&isSolved=0&"+"reasonId="+reasonId+"&reason=" + reason+"&num="+Math.random(),
					success: function(data,textStatus){
						$("#J-help-message-panel-1").html("<h4 class='pop-text'>您的反馈信息已经成功提交，谢谢您的反馈</h4>");
						$("#J-help-message-panel-1").show();
						$.cookie("phoenix_help_"+global_userID+"_"+helpId,helpId,30);
						setTimeout(function(){$("#J-help-message-panel-1").hide();panel.hide();},3000);
					},
					error: function(xhr,status,errMsg){
						alert("操作失败!");
					}
				});
			}else
			{
				$("#J-help-message-panel-1").html("<h4 class='pop-text'>您已提交过此次反馈</h4>");
				$("#J-help-message-panel-1").show();
				setTimeout(function(){$("#J-help-message-panel-1").hide();},3000);
			}
		});
});
	
