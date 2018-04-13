$(document).ready(function() {
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		table = $('#J-table'),
		panel = $('#J-search-panel'),
		Tip = phoenix.Tip.getInstance(),
		inputStart = $('#J-time-start'),
		inputEnd = $('#J-time-end'),
		selectLotteryId = $('#J-select-lotteryid'),
		inputParamCode = $('#J-input-paramCode'),
		selectTime = $('#J-select-time'),
		optionTime = selectTime.find('option'),
		selectStatus = $('#J-select-status'),
		inputStartWins = $('#J-input-startWins'),
		inputEndWins = $('#J-input-endWins'),
		inputPageCount = $('#J-input-pageCount'),
		device = $('#J-select-device'),
		setTimeFn = null,
		setSelect = null,
		st = $('#sortType');
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(4)').attr("class","current");
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
	
	selectTime.change(function(){
		var v = Number(this.value);
		if(optionTime.get(optionTime.size() -1).value == this.value){
			inputStart.focus();
		}else{
			if(v > 0){
				setTimeFn(v);
			}
		}
	});
	setTimeFn = function(day){
		var now = new Date(),y = now.getFullYear(),m = now.getMonth(),d = now.getDate(),h = now.getHours(),mm = now.getMinutes(),s = now.getSeconds(),newDate;
		newDate = new Date(y, m, d - day + 1, 0, 0, 1);
		
		inputStart.val(newDate.getFullYear()+ '-' + (newDate.getMonth()+1) + '-'+  newDate.getDate() +'  00:00:00');
		inputEnd.val(y+ '-' + (m+1) + '-'+  d +'  '+ h +':'+ mm +':'+ s);
	};
	
	setSelect = function(v){
		var i = 0,len = optionTime.size();
		for(;i < len;i++){
			if(Number(v) == Number(optionTime[i].value)){
				optionTime[i].setAttribute('selected', 'selected');
			}else{
				optionTime[i].removeAttribute('selected');
			}
		}

	};
	setTimeFn(Number(selectTime.val()));
	
	if($('#J-select-lotteryid').val()==0){
		$('#webIssueCode').empty();
		$('#webIssueCode').attr('disabled',true);
	}else{
		$('#webIssueCode').removeAttr('disabled');
	}
	$('#J-select-lotteryid').change(function(){
		if($('#J-select-lotteryid').val()==0||$("#J-select-lotteryid").val()==99112||$("#J-select-lotteryid").val()==99306){
			$('#webIssueCode').empty();
			$('#webIssueCode').attr('disabled',true);
		}else{
			$('#webIssueCode').attr('disabled',true);
			$.ajax({
				type:  "post",
				url: 'getGameIssuesByLotteryId?lotteryId='+$('#J-select-lotteryid').val(),
				data: '',
				success:function(data){
					$('#webIssueCode').removeAttr('disabled');
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
	});
	
	
	//彩种回显
	var temp = $("#lotteryid").val();
	$("#J-select-lotteryid").find("option[value=" + temp + "]").attr("selected", true);
	
	if($("#J-select-lotteryid").val()!=0&&$("#J-select-lotteryid").val()!=99112&&$("#J-select-lotteryid").val()!=99306){
		$('#webIssueCode').removeAttr('disabled');
		$.ajax({
			type:  "post",
			url: 'getGameIssuesByLotteryId?lotteryId='+$('#J-select-lotteryid').val(),
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
		
	
	
	//panel.on('keyup', '.input-money', function(){
	//	var v = this.value;
	//	v = v.replace(/[^\d^\.]/g, '').split('.');
	//	if(v.length > 2){
	//		v.pop();
	//	}
	//	v = v.join('.');
	//	this.value = v;
	//}).on('blur', '.input-money', function(){
	//	var v = this.value.replace(/[^\d^\.]/g, '');
	//	if($.trim(v) != ''){
	//		this.value = phoenix.util.formatMoney(v);
	//	}
	//}).on('keyup', '.input-num', function(){
	//	this.value = this.value.replace(/[^\d]/g, '');
	//});
	panel.on('keyup', '.input-num', function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	//保持搜索条件
	if ($('#lotteryid').val() != '') {
		selectLotteryId.val($('#lotteryid').val());
	}
	if ($('#device').val() != '') {
		device.val($('#device').val());
	}
	if ($('#paramCode').val() != ''){
		inputParamCode.val($('#paramCode').val());
	}
	if ($('#selectTimeMode').val() != ''){
		selectTime.val($('#selectTimeMode').val());
	}
	var containSubChecked = document.getElementById("J-select-containSub");
	if($('#containSub').val() != ''){
		containSubChecked.checked = $('#containSub').val()==1?true:false;
	}
	if ($('#startCreateTime').val() != '') {
		inputStart.val(formatDate(new Date(Number($('#startCreateTime').val()))));
	}
	if ($('#endCreateTime').val() != '') {
		inputEnd.val(formatDate(new Date(Number($('#endCreateTime').val()))));
	}
	if ($('#status').val() != '') {
		selectStatus.val($('#status').val());
	}
	if ($('#startWinsCount').val() != '' && $('#startWinsCount').val() != '0.0') {
		inputStartWins.val(+$('#startWinsCount').val()/10000);
	} else {
        $('#startWinsCount').val('');
    }
	if ($('#endWinsCount').val() != '' && $('#endWinsCount').val() != '0.0') {
		inputEndWins.val(+$('#endWinsCount').val()/10000);
	} else {
        $('#endWinsCount').val('');
    }
	if ($('#pageCount').val() != '') {
		inputPageCount.val($('#pageCount').val());
	}
	var temp = $('#webIssueCodeValue').val();
	$("#webIssueCode").find("option[value=" + temp + "]").attr("selected", true);
	//setTimeFn(1);
	$('#J-button-submit').click(function(){
		if(device.val() != ''){
			$('#device').val(device.val());
		}else{
			$('#device').val(null);
		}
		if (selectLotteryId.val() != '') {
			$('#lotteryid').val(selectLotteryId.val());
		} else {
            $('#lotteryid').val('');
        }
		if (inputParamCode.val() != '') {
			$('#paramCode').val(inputParamCode.val());
		}else{
			$('#paramCode').val("");
		}
		if ($('#J-select-containSub').val() != '') {
			if(containSubChecked.checked == true){
				$('#containSub').val(1);
			}else{
				$('#containSub').val(0);
			}
		}
		
		if($('#webIssueCode').val()!=0){
			$('#issueCode').val($('#webIssueCode').val());
		}else{
			$('#issueCode').val('');
		}
		if(selectTime.val() != ''){
			var mode = selectTime.val();
			$('#selectTimeMode').val(mode);
			
			if(mode == -1){
				$('#startCreateTime').val("");
				$('#endCreateTime').val("");
			}else{
				if (inputStart.val() != '') {
					var time = convertDate2UnixTimestamp(inputStart.val());
					$('#startCreateTime').val(time);
				}
				if (inputEnd.val() != '') {
					var time = convertDate2UnixTimestamp(inputEnd.val());
					$('#endCreateTime').val(time);
				} 
			}
		}
		if(selectStatus.val() != ''){
			$('#status').val(selectStatus.val());
		}
		if(inputStartWins.val() != ''){
			$('#startWinsCount').val(+inputStartWins.val()*10000);
		}else{
			$('#startWinsCount').val("");
		}
		if(inputEndWins.val() != ''){
			$('#endWinsCount').val(+inputEndWins.val()*10000);
		}else{
			$('#endWinsCount').val("");
		}
		if(inputPageCount.val() != ''){
			$('#pageCount').val(inputPageCount.val());
		}else{
			$('#pageCount').val(5);
		}
		
		$('#J-search-form').submit();
	});
	
	$('#J-button-export').click(function(){
		var ipts = $('#J-form-hidden').find('input[type="hidden"]'),data = {},htmlStr = '';
		ipts.each(function(){
			var name = this.getAttribute('name');
			data[name] = this.value;
		});
		htmlStr = phoenix.util.template($('#J-tpl-export').text(), data);
		
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:htmlStr,
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$('#J-download-form').submit();
				Wd.hide();
			},
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				var rows = Wd.win.dom.find('li[data-data]');
				rows.each(function(){
					var dom = $(this);
					if($.trim(dom.attr('data-data')) == ''){
						dom.remove();
					}
				});
			}
		});
		
		$('#Span-lotteryName').html($('#J-select-lotteryid option:selected').text());
		$('#Span-account').html($('#J-input-paramCode').val());
		$('#Span-startTime').html($('#J-time-start').val());
		$('#Span-endTime').html($('#J-time-end').val());
		$('#Span-status').html($('#J-select-status option:selected').text());
		$('#Span-issueCode').html($('#webIssueCode option:selected').text());
		if($('#J-input-startWins').val() == '' && $('#J-input-endWins').val() == ''){
			$('#Span-startWins').html("无限制");
		}else if($('#J-input-startWins').val() != '' && $('#J-input-endWins').val() == ''){
			$('#Span-startWins').html($('#J-input-startWins').val()+"元");
		}else{
			$('#Span-startWins').html($('#J-input-startWins').val()+"元");
			$('#Span-endWins').html(" - "+$('#J-input-endWins').val()+"元");
		}
		
	});	
	
	// table header sorting start
	$('.table-toggle').children("a").click(function(){
		switchSortType(this, parseInt(this.id));
	});
	
	function switchSortType(e, n) {
		var $down = $(e).children("i").filter('.ico-down-current');
	    if ($down.is(":visible")) {
	    }  else {
	       n++;
	    }
	    st.val(n);
	    $('#J-search-form').submit();
	}
	
	function checkUpOrDown() {
		var sortTypeVale = parseInt(st.val());
		if (sortTypeVale != 0) {
			var b = sortTypeVale % 2;
			if (b == 1) {
				var a = sortTypeVale;
				$('.table-toggle').children("a").filter('#'+a+'').children("i").filter('.ico-down-current').hide();
			} else if (b == 0) {
				var a = sortTypeVale - 1;
				$('.table-toggle').children("a").filter('#'+a+'').children("i").filter('.ico-up-current').hide();
			}
		}
	}
	
	checkUpOrDown();
	// table header sorting end
	
});
jQuery.fn.addOption = function(text,val){
    $(this).get(0).options.add(new Option(text,val));
}

