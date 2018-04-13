$(document).ready(function() {
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		selectLotteryId = $('#J-select-lotteryid'),
		inputWebIssueCode = $('#webIssueCode');
	
		
	if ($('#lotteryid').val() != '') {
		selectLotteryId.val($('#lotteryid').val());
	}	

	
	
	
	//时间选择，隐藏域保存值,改变样式
	$(".time span").click(function () {		
		var txtday = $(this).attr("pro").trim();		
		$(".time span").removeClass().addClass("ico-tab");
	    $(this).addClass("ico-tab ico-tab-current");		
		$("#time").attr("value", txtday);
			
	});	
	
	$('[id^=time]').click(function(){
		var n=$(this).attr('pro');
	    $('#startCreateTime').val(convertDate2UnixTimestamp(getStartTimeOfNDay(1-n)));
	    $('#endCreateTime').val(convertDate2UnixTimestamp(getStartTimeOfNDay(1)));
	    $("#time").attr("value", n);
	});
		
	$('#webIssueCode').focus(function(){
		if($('#webIssueCode').val()=='请输入奖期'){ 
			$("#webIssueCode").val('');
		}}).blur(function(){
			var v = $.trim($(this).val());
			if(v == ''){
				$("#webIssueCode").val('请输入奖期');
			}
	});
	
	
	
	//彩种回显
	var temp = $("#lotteryIdValue").val();
	$("#lotteryId").find("option[value=" + temp + "]").attr("selected", true);		
		
	if($('#webIssueCodeValue').val()!=''){
		$("#webIssueCode").val($('#webIssueCodeValue').val());
	}
	
	$('#J-button-submit').click(function(){
		
		if (selectLotteryId.val() != '') {
			$('#lotteryid').val(selectLotteryId.val());
		}
		
		if($('#webIssueCode').val().trim()=='请输入奖期') {
			$('#webIssueCode').val('');
		}
		
		$('#J-search-form').submit();
	});
	
	
	if($('#time').val()!=''){
		$(".time span").removeClass().addClass("ico-tab");
		$(".time").find("span[pro="+$('#time').val()+"]").addClass("ico-tab ico-tab-current");		
	}

});