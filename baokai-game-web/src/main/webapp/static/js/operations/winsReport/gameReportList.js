$(document).ready(function() {
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		table = $('#J-table'),
		panel = $('#J-search-panel'),
		Tip = phoenix.Tip.getInstance(),
		inputStart = $('#J-time-start'),
		inputEnd = $('#J-time-end'),
		selectTime = $('#J-select-time'),
		selectLotteryId = $('#J-select-lotteryid'),
		optionTime = selectTime.find('option'),
		setTimeFn = null,
		setSelect = null;
		st = $('#sortType');
	
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
		});
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
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
		
		inputStart.val(newDate.getFullYear()+ '-' + (newDate.getMonth()+1) + '-'+  newDate.getDate() +'  00:00:01');
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
	
	panel.on('keyup', '.input-money', function(){
		var v = this.value;
		v = v.replace(/[^\d^\.]/g, '').split('.');
		if(v.length > 2){
			v.pop();
		}
		v = v.join('.');
		this.value = v;
	}).on('blur', '.input-money', function(){
		var v = this.value.replace(/[^\d^\.]/g, '');
		if($.trim(v) != ''){
			this.value = phoenix.util.formatMoney(v);
		}
	}).on('keyup', '.input-num', function(){
		this.value = this.value.replace(/[^\d]/g, '');
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
		$('#Span-startTime').html($('#J-time-start').val());
		$('#Span-endTime').html($('#J-time-end').val());
		
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