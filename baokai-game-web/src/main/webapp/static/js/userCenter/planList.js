$(document).ready(function() {
	var form = $('#J-form');
	$('#divDetailed').hide(); 
	$('#J-date-start').val(getStartTimeOfNDay(-6));
	$('#J-date-end').val(getStartTimeOfNDay(1));
	if($.cookie(global_userName+'_Plan_Open')=="true")
	{
		$('#divDetailed').show();
		$('#divExpansion').html("收起∧");
	}else if($.cookie(global_userName+'_Plan_Open')=="false")
	{
		$('#divDetailed').hide();
		$('#divExpansion').html("展开∨");
	}
	if($('#lotteryId').val()==0){
		$('#webIssueCode').empty();
		$('#webIssueCode').attr('disabled',true);
	}else{
		$('#webIssueCode').removeAttr('disabled');
	}
	$('#lotteryId').change(function(){
		if($('#lotteryId').val()==0){
			$('#webIssueCode').empty();
			$('#webIssueCode').attr('disabled',true);
		}else{
			$('#webIssueCode').removeAttr('disabled');
			$.ajax({
				type:  "post",
				url: 'getGameIssuesByLotteryId?lotteryId='+$('#lotteryId').val(),
				data: '',
				success:function(data){
					$('#webIssueCode').empty();
					$('#webIssueCode').addOption('所有奖期','0');
					if(data.length!=0){
						for(var i = 0; i < data.length; i++){
							$('#webIssueCode').addOption(data[i].webIssueCode,data[i].issueCode);
							}
						}
				},
				error: function(){
					//alert('get webIssueCode data error!')
				}
			});
		}
		
		if($('#divExpansion').html()=="展开∨"){
			if ($('#J-date-start').val() != '') {
				var time = convertDate2UnixTimestamp($('#J-date-start').val());
				$('#startTime').val(time);
			}
			if ($('#J-date-end').val() != '') {
				var time = convertDate2UnixTimestamp($('#J-date-end').val());
				$('#endTime').val(time);
			}
			if($('#status').val()==''){//status默认设置为选择全部
				$('#status').val(-1);
			}
			form.submit();
		}
	});
	
	//展开，收起
	$('#divExpansion').click(function(){		
		if($('#divExpansion').html()=="展开∨"){
			var uname=global_userName,key=uname+'_Plan_Open';
		$.cookie(key,true,{path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
			$('#divDetailed').show();
			$('#divExpansion').html("收起∧");
		}
		else{
			var uname=global_userName,key=uname+'_Plan_Open';
		$.cookie(key,false,{path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
			$('#divDetailed').hide();
			$('#divExpansion').html("展开∨");
		}	
	});

	//时间选择，隐藏域保存值,改变样式
	$(".time span").click(function () {		
		var txtday = $(this).attr("pro").trim();		
		$(".time span").removeClass().addClass("ico-tab");
        $(this).addClass("ico-tab ico-tab-current");		
		$("#time").attr("value", txtday);
		
	});

	//状态，隐藏域保存值,改变样式
	$(".state span").click(function () {		
		var txtstatus = $(this).attr("pro").trim();		
		$(".state span").removeClass().addClass("ico-tab");
        $(this).addClass("ico-tab ico-tab-current");		
		$("#status").attr("value", txtstatus);
		if($('#divExpansion').html()=="展开∨"){
			if ($('#J-date-start').val() != '') {
				var time = convertDate2UnixTimestamp($('#J-date-start').val());
				$('#startTime').val(time);
			}
			if ($('#J-date-end').val() != '') {
				var time = convertDate2UnixTimestamp($('#J-date-end').val());
				$('#endTime').val(time);
			}
			if($('#status').val()==''){//status默认设置为选择全部
				$('#status').val(-1);
			}
			form.submit();
		}
	});				
	
	$('#planCode').focus(function(){
		if($('#planCode').val()=='如：ABC77779')
		
		{ $("#planCode").val('');}
		
		}).blur(function(){
			var v = $.trim($(this).val());
			if(v == ''){
				$("#planCode").val('如：ABC77779');
			}
	
	});
	
	//彩种回显
	var temp = $("#lotteryIdValue").val();
	$("#lotteryId").find("option[value=" + temp + "]").attr("selected", true);
	
	if($("#lotteryIdValue").val()!=0){
		$('#webIssueCode').removeAttr('disabled');
		$.ajax({
			type:  "post",
			url: 'getGameIssuesByLotteryId?lotteryId='+$('#lotteryId').val(),
			data: '',
			async:false, 
			success:function(data){
				$('#webIssueCode').empty();
				$('#webIssueCode').addOption('所有奖期','0');
				if(data.length!=0){
					for(var i = 0; i < data.length; i++){
						$('#webIssueCode').addOption(data[i].webIssueCode,data[i].issueCode);
						}
					}
			},
			error: function(){
				alert('get webIssueCode data error!')
			}
		});
	}
	

	if ($('#startTime').val() != '') {
		$('#J-date-start').val(formatDate(new Date(Number($('#startTime').val()))));
	}
	
	if ($('#endTime').val() != '') {
		$('#J-date-end').val(formatDate(new Date(Number($('#endTime').val()))));
	}
	

	if($('#planCodeValue').val()!=''){
		$("#planCode").val($('#planCodeValue').val());
	}
	
	if($('#status').val()!=''){
		$(".state span").removeClass().addClass("ico-tab");
		$(".state").find("span[pro="+$('#status').val()+"]").addClass("ico-tab ico-tab-current");		
	}
	
	if($('#time').val()!=''){
		$(".time span").removeClass().addClass("ico-tab");
		$(".time").find("span[pro="+$('#time').val()+"]").addClass("ico-tab ico-tab-current");		
	}
	
		var temp = $('#webIssueCodeValue').val();
		$("#webIssueCode").find("option[value=" + temp + "]").attr("selected", true);

	
	//表单提交校验
	$('#J-button-submit').click(function(){
		if ($('#J-date-start').val() != '') {
			var time = convertDate2UnixTimestamp($('#J-date-start').val());
			$('#startTime').val(time);
		}
		if ($('#J-date-end').val() != '') {
			var time = convertDate2UnixTimestamp($('#J-date-end').val());
			$('#endTime').val(time);
		}
		if($('#status').val()==''){//status默认设置为选择全部
			$('#status').val(-1);
		}
		form.submit();
	});
	
	$('[id^=time]').click(function(){
		var n=$(this).attr('pro');
	    $('#J-date-start').val(getStartTimeOfNDay(1-n));
	    $('#J-date-end').val(getStartTimeOfNDay(1));
	    if($('#divExpansion').html()=="展开∨"){
			if ($('#J-date-start').val() != '') {
				var time = convertDate2UnixTimestamp($('#J-date-start').val());
				$('#startTime').val(time);
			}
			if ($('#J-date-end').val() != '') {
				var time = convertDate2UnixTimestamp($('#J-date-end').val());
				$('#endTime').val(time);
			}
			if($('#status').val()==''){//status默认设置为选择全部
				$('#status').val(-1);
			}
			form.submit();
		}
	});
	
	form.submit(function(e){
		//e.preventDefault();
		if($('#planCode').val().trim()=='如：ABC77779'){$('#planCode').val('');}
		
	});
	
	//取消重置
	$('#restoreDefaults').click(function(e){
		e.preventDefault();
		$('#J-form').get(0).reset();
		//重置样式,值初始	
		$("#time").attr("value",7);
		$("#status").attr("value",-1);	
		$(".time span").removeClass().addClass("ico-tab");
		$(".time span:eq(2)").addClass("ico-tab ico-tab-current");
		$('#J-date-start').val(getStartTimeOfNDay(-6));
		$('#J-date-end').val(getStartTimeOfNDay(1));
		$(".state span").removeClass().addClass("ico-tab");
		$(".state span:eq(0)").addClass("ico-tab ico-tab-current");
		$('#lotteryId').val(0);
		$('#webIssueCode').empty();
		$('#webIssueCode').attr('disabled',true);
	});
	
});

jQuery.fn.addOption = function(text,val){
    $(this).get(0).options.add(new Option(text,val));
}