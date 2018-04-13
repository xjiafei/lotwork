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
		inputPageCount = $('#J-input-pageCount'),
		setTimeFn = null,
		setSelect = null;
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(4)').attr("class","current"); 
	$('.col-side .nav dd:eq(3)').attr("class","current");
	
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
		//if($('#startCreateTime').val() != '' && $('#endCreateTime').val() !=''){return true;}
		var now = new Date(),y = now.getFullYear(),m = now.getMonth(),d = now.getDate(),h = now.getHours(),mm = now.getMinutes(),s = now.getSeconds(),newDate;
			newDate = new Date(y, m, d - day + 1, 0, 0, 1),
			monthDateNow = newDate.getMonth() + 1,
			monthNewDate = m + 1;
		if(Number(monthDateNow) < 10){
			monthDateNow = '0' + monthDateNow ;
		}
		inputStart.val(newDate.getFullYear()+ '-' + monthDateNow + '-'+  newDate.getDate() +'  00:00:00');
		if(Number(monthNewDate) < 10){
			monthNewDate = '0' + monthNewDate ;
		}
		
		inputEnd.val(y+ '-' + monthNewDate + '-'+  d +'  '+ h +':'+ mm +':'+ s);
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
	if ($('#pageCount').val() != '') {
		inputPageCount.val($('#pageCount').val());
	}
	
	
	$('#J-button-submit').click(function(){
		if (selectLotteryId.val() != '') {
			$('#lotteryid').val(selectLotteryId.val());
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
	});	
	
});