

//日期控件
(function(host, name, Event, $, undefined){
	var defConfig = {
		//绑定的input控件
		input:null,
		//初始化使用的日期
		//年月日之间使用非数字区分
		date:new Date(),
		//追加的class
		cls:'',
		//选区开始年份
		startYear:1980,
		//选区结束年份
		endYear:(new Date()).getFullYear() + 1,
		//endYear:2020,
		
		effectShow:function(){
			this.dom.show();
		},
		effectHide:function(){
			this.dom.hide();
		},
		//是否锁定Input输入字符类型
		isLockInputType:true,
		//是否显示小时和分钟和秒
		isShowTime:false,
		setDisabled:function(){
			var me = this,tds = me.getContent().find('td'),it;
			/**
			tds.each(function(){
				it = $(this),num = Number(it.text());
				if(!it.hasClass('day-thisMonth') || (num < 4) || (num > 10)){
					it.addClass('day-disabled');
				}
			});
			**/
		}
	},
	//星期的html
	DAYSTRING = '<tr><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr>',
	//公共实例
	instance,
	//实例dom的引用
	caseHas = [];
	
	var pros = {
		init:function(cfg){
			var me = this,_dateArr,_case,_input,str_h,str_s,inputCheckFn;

			_input = $(cfg.input);
			if(_input.size() > 0){
				if(_case = me.checkCases(_input.get(0))){
					//console.log(_case.effectShow);
					me.removeCase(_case);
					_case.dom.remove();
				}
				caseHas.push(me);
			}
			me.input = _input;
			me.randomNum = (''+Math.random()).replace('0.', '');
			
			me.effectShow = cfg.effectShow;
			me.effectHide = cfg.effectHide;
			
			me.dom = $('<div onselectstart="return false;" class="j-ui-datepicker '+ cfg.cls +'" style="display:none;"><div class="control"><span class="pre">&lt;&lt;</span><span class="year"></span><span class="month"></span><span class="next">&gt;&gt;</span></div><div class="content"></div><div class="content-time" style="display:none;">时间：<input class="time-input time-h" type="text" value="00" /> : <input class="time-input time-s" type="text" value="00" /> : <input class="time-input time-sec" type="text" value="00" />&nbsp;&nbsp;<input class="button-confirm" type="button" value="确定" /></div></div>').appendTo($('body'));
			
			me.addEvent('afterRender', cfg.setDisabled);
			
			
			if(me.input.size() > 0 && $.trim(me.input.val()) != ''){
				me.setDate(me.input.val());
			}else{
				me.setDate(cfg.date);
			}
			
			if(me.defConfig.isLockInputType){
				if(me.input.size() > 0){
					me.input.keydown(function(e){
						if(e.keyCode == 8 || e.keyCode == 46){
							return true;	
						}else{
							return false;
						}					  
					});
				}
			}

			
			
			//下拉框初始化请放在setDate之后，它将使用到初始化的date
			me.initSelectYear(cfg.startYear, cfg.endYear);
			me.initSelectMonth();
			
			me.getContent().click(function(e){
				var el = $(e.target),year = me.getDate().getFullYear(),mon = me.getDate().getMonth(),day = me.getDate().getDate(),cfg = me.defConfig,_date;

				if(e.target.tagName.toLowerCase() == 'td' && !el.hasClass('day-disabled')){
					mon = el.hasClass('day-preMonth') ? mon - 1 : mon;
					if(mon < 0){
						mon = 11;
						year -= 1;
					}
					mon = el.hasClass('day-nextMonth') ? mon + 1 : mon;
					if(mon > 11){
						mon = 0;
						year += 1;
					}
					day = Number(el.text());
					
					me.confirmDate(year, mon, day);

				}
			});
			
			me.dom.find('.content-time .button-confirm').click(function(){
				var highDt = me.dom.find('.day-curr');
				if(highDt.size() > 0 && !highDt.hasClass('day-disabled')){
					me.buttonConfirm();
				}
			});
			
			phoenix.util.doc.bind('mousedown.datapcker-' + me.randomNum, function(e){
				if(!$.contains(me.dom.get(0), e.target)){
					me.hide();
				}
			});
			
			me.getPreMonth().click(function(e){
				me.preMonth();
			});
			me.getNextMonth().click(function(e){
				me.nextMonth();
			});
			
			if(cfg.isShowTime){
				inputs = me.dom.find('.content-time .time-input');
				inputCheckFn = function(e){
					var me = this,v = $.trim(me.value),a = inputs.get(0),b = inputs.get(1);
					if(me == a){
						me.value = v = v.replace(/^(\d{2}).*/g, '$1').replace(/\D/, '');
						v = Number(v) > 23 ? 23 : v;
					}else{
						me.value = v = v.replace(/^(\d{2}).*/g, '$1').replace(/\D/, '');
						v = Number(v) > 59 ? 59 : v;
					}
					me.value = v;
				};
				inputs.keyup(inputCheckFn).blur(function(){
					var me = this,v = $.trim(me.value);
					inputCheckFn.call(me);
					if(v == ''){
						me.value = '00';	
					}else{
						me.value = Number(v) < 10 ? '0'+Number(v) : v;
					}
					
				});
			}

		},
		confirmDate:function(y, m, d, isButtonConfirm){
			var me = this,cfg = me.defConfig,_date,inputs = me.dom.find('.content-time input');
			if(cfg.isShowTime){
				_date = new Date(y, m, d);
				_date.setHours(inputs.get(0).value, inputs.get(1).value, inputs.get(2).value, 0);
				me.setDate(_date);
				if(isButtonConfirm){
					me.setInputVal(me.formatDateTime(y, m + 1, d, _date.getHours(), _date.getMinutes(), _date.getSeconds()));
					me.hide();
				}
			}else{
				me.setDate(new Date(y, m, d));
				me.hide();
				me.setInputVal(me.formatDate(y, m + 1, d));
			}
		},
		buttonConfirm:function(){
			var me = this,dt = me.getPanelDate();
			me.confirmDate(dt.y, dt.m, dt.d, true);
		},
		getPanelDate:function(){
			var me = this,
				y = Number(me.getControlYear().find('select').val()),
				m = Number(me.getControlMonth().find('select').val()) - 1,
				d = Number(me.dom.find('.day-curr').text()),
				inputs = me.dom.find('.content-time input'),
				h = Number(inputs.get(0).value),
				s = Number(inputs.get(1).value),
				sec = Number(inputs.get(2).value);
			//console.log({y:y,m:m,d:d,h:h,s:s});
			return {y:y,m:m,d:d,h:h,s:s,sec:sec};
		},
		formatDate:function(y, m, d){
			m = Number(m) < 10 ? '0' + Number(m) : m;
			d = Number(d) < 10 ? '0' + Number(d) : d;
			return y + '-' + m + '-' + d;
		},
		formatDateTime:function(y, m, d, h, s, sec){
			m = Number(m) < 10 ? '0' + Number(m) : m;
			d = Number(d) < 10 ? '0' + Number(d) : d;
			h = Number(h) < 10 ? '0' + Number(h) : h;
			s = Number(s) < 10 ? '0' + Number(s) : s;
			sec = Number(sec) < 10 ? '0' + Number(sec) : sec;
			return y + '-' + m + '-' + d + '  ' + h + ':' + s + ':' + sec;
		},
		removeCase:function(cs){
			var i = 0,len = caseHas.length;
			for(;i < len;i++){
				if(caseHas[i] == cs){
					caseHas.splice(i, 1);
					break;
				}
			}
		},
		checkCases:function(input){
			var i = 0,len = caseHas.length;
			for(;i < len;i++){
				if(caseHas[i]['input'] && input == caseHas[i]['input'].get(0)){
					return caseHas[i];
				}
			}
			return false;
		},
		initSelectYear:function(startYear, endYear){
			var me = this,strArr = [],sel = '',currYear = me.getDate().getFullYear();
			strArr.push('<select class="control-year">');
			for(;startYear <= endYear; endYear--){
				sel = endYear == currYear ? ' selected="selected" ' : '';
				strArr.push('<option'+ sel +' value="'+ endYear +'">'+ endYear +'</option>');
			}
			strArr.push('</select>');
			me.getControlYear().html(strArr.join(''));
			me.dom.find('.control-year').change(function(){
				var y = Number(this.value),m = me.getDate().getMonth(),d = me.getDate().getDate();
				me.setDate(new Date(y, m, d));
			});
		},
		initSelectMonth:function(){
			var me = this,strArr = [],i = 1,month = 12,sel = '',currMonth = me.getDate().getMonth();
			strArr.push('<select class="control-month">');
			for(;i <= month; i++){
				sel = i == (currMonth + 1) ? ' selected="selected" ' : '';
				strArr.push('<option'+ sel +' value="'+ i +'">'+ i +'</option>');
			}
			strArr.push('</select>');
			me.getControlMonth().html(strArr.join(''));
			me.dom.find('.control-month').change(function(){
				var y = me.getDate().getFullYear(),m = Number(this.value) - 1,d = 1;
				me.setDate(new Date(y, m, d));
				//console.log(m);
			});
			
		},
		getDate:function(){
			return this._date;
		},
		setDate:function(date){
			var me = this,darr = [],y,m,d,h = 0,s = 0,sec = 0,_date,cfg = me.defConfig;
			if(typeof date == 'string'){
				darr = me.getDateArr(date);
				y = darr[0];
				m = darr[1] - 1;
				d = darr[2];
				if(darr.length > 4){
					h = darr[3];
					s = darr[4];
					sec = darr[5];
				}
			}else if(typeof date == 'undefined'){
				_date = new Date();
				y = _date.getFullYear();
				m = _date.getMonth();
				d = _date.getDate();
				h = _date.getHours();
				s = _date.getMinutes();
				sec = _data.getSeconds();
			}else{
				y = date.getFullYear();
				m = date.getMonth();
				d = date.getDate();
				h = date.getHours();
				s = date.getMinutes();
				sec = date.getSeconds();
			}
			me._date = new Date(y, m, d);
			me._date.setHours(h, s, sec, 0);
			me.render(y, m, d, h, s, sec);
		},
		setInputVal:function(v){
			var me = this;
			if(me.input.size() > 0){
				me.input.val(v);
			}
			me.fireEvent('afterSetValue');
		},
		//获取上个月的天数
		getPreMonthDays:function(y, m){
			var y = (m == 0) ? (y - 1) : y,
				date = new Date(y, m, 0);
			//alert(y + '/' + date.getMonth() + '/' + date.getDate());
			return date.getDate();
		},
		//将字符串日期格式化成日期数组
		getDateArr:function(dateStr){
			return $.trim(dateStr).replace(/\D/g, '-').replace(/\-+/g, '-').split('-');
		},
		show:function(){
			var me = this,pos;
			if(me.input && me.input.size() > 0){
				pos = me.input.offset();
				me.dom.css({left:pos.left,top:pos.top + me.input.outerHeight()});
			}
			me.effectShow();
		},
		hide:function(){
			var me = this;
			me.effectHide();
			phoenix.util.doc.unbind('mousedown.datapcker-' + me.randomNum);
			me.dom.remove();
		},
		getPreMonth:function(){
			var me = this;
			return me._domPre || (me._domPre = me.dom.find('.pre'));
		},
		getNextMonth:function(){
			var me = this;
			return me._domNext || (me._domNext = me.dom.find('.next'));
		},
		getControlYear:function(){
			var me = this;
			return me._domYear || (me._domYear = me.dom.find('.year'));
		},
		getControlMonth:function(){
			var me = this;
			return me._domMonth || (me._domMonth = me.dom.find('.month'));
		},
		getContent:function(){
			var me = this;
			return me._content || (me._content = me.dom.find('.content'));
		},
		getMaxDay:function(y, m){
			var date = new Date(y, m+1, 0);
			return date.getDate();
		},
		getWeek:function(y, m, d){
			return (new Date(y, m, d)).getDay();
		},
		isToday:function(date, y, m, d){
			return date.getFullYear() == y && date.getMonth() == m && date.getDate() == d;
		},
		preMonth:function(){
			var me = this,date = me.getDate(),y = date.getFullYear(),m = date.getMonth(),d = date.getDate();
			m -= 1;
			if(m < 0){
				m = 11;
				y -= 1;
			}
			me.setDate(new Date(y, m, 1));
		},
		nextMonth:function(){
			var me = this,date = me.getDate(),y = date.getFullYear(),m = date.getMonth(),d = date.getDate();
			m += 1;
			if(m > 11){
				m = 0;
				y += 1;
			}
			me.setDate(new Date(y, m, 1));	
		},
		render:function(y, m, d, h, s, sec){
			var me = this,cfg = me.defConfig,date = new Date(),maxDay,week,i = 0,j = 1,k = 1,l = 0,n = 1,rownum,str = [],isFirst = true,
				preMonthDays,
				preYear,
				preMonth,
				nextYear,
				nextMonth,
				now = new Date(),
				//今天
				todayCls = 'day-today',
				//目标日期
				dayCurrCls = 'day-curr',
				//小时和分钟
				contentTime,
				inputs;
				
				
			if(!y){
				y = me._date.getFullYear();
				m = me._date.getMonth();
				d = me._date.getDate();
				h = me._date.getHours();
				s = me._date.getMinutes();
				sec = me._date.getSeconds();
			}
			//本月最大天数
			maxDay = me.getMaxDay(y, m);
			week = me.getWeek(y, m, 0);
			rownum = Math.ceil((week + 1 + maxDay)/7);
			//上月最大天数
			preMonthDays = me.getPreMonthDays(y, m);
			

			str.push('<table class="tb">');
			str.push('<tbody>');
			str.push(DAYSTRING);
			for(;i < rownum;i++){
				str.push('<tr>');
				k = 1;
				if(isFirst){
					preMonth = (m - 1) < 0 ? 11 : m - 1;
					preYear = (m - 1) < 0 ? y - 1 : y;
					for(;l <= week;l++){
						str.push('<td class="day-preMonth" data-year="'+ preYear +'" data-month="'+ preMonth +'">'+ (preMonthDays - week + l) +'</td>');
					}
					isFirst = false;
					k += week+1;
				}
				for(;j <= maxDay;j++){
					if(k%(7 + 1) == 0){
						break;
					}
					todayCls = me.isToday(now, y, m, j) ? 'day-today' : '';
					daycurrCls = d == j ? ' day-curr ' : '';
					str.push('<td class="day-thisMonth '+ todayCls + daycurrCls +'" data-year="'+ y +'" data-month="'+ m +'">'+ j +'</td>');
					k++;
				}
				
				if(i == rownum - 1){
					nextMonth = (m + 1) > 11 ? 0 : m + 1;
					nextYear = (m + 1) > 11 ? y + 1 : y;
					for(;n < rownum*7 - week - maxDay;n++){
						str.push('<td class="day-nextMonth" data-year="'+ nextYear +'" data-month="'+ nextMonth +'">'+ n +'</td>');
					}
				}

				
				str.push('</tr>');
			}
			str.push('</tbody>');
			str.push('</table>');
			
			me.getContent().html(str.join(''));
			
			
			
			me.fireEvent('afterRender');
			
			if(cfg.isShowTime){
				contentTime = me.dom.find('.content-time');
				contentTime.show();
				inputs = contentTime.find('input');
				inputs.get(0).value = Number(h) < 10 ? '0' + Number(h) : h;
				inputs.get(1).value = Number(s) < 10 ? '0' + Number(s) : s;
				inputs.get(2).value = Number(sec) < 10 ? '0' + Number(sec) : sec;
			}
			
			me.dom.find('.control-year option').each(function(){
				if(Number(this.value) == y){
					this.selected = true;
				}else{
					this.selected = false;
				}
			});
			me.dom.find('.control-month option').each(function(){
				if(Number(this.value) == (m+1)){
					this.selected = true;
				}else{
					this.selected = false;
				}
			});
			
			
		}

	};

	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	
	host[name].getInstance = function(){
		return instance || (instance = new host[name]());
	};

})(phoenix, "DatePicker", phoenix.Event, jQuery);







