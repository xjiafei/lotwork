
		$(function(){
			var panelControl = $('#J-panel-control'),
				periodsControl = $('#J-periods-data'),
				timePeriods = $('#J-time-periods'),
				reportDownload = $('#report-download'),
				buttonControl = $('#J-button-showcontrol'),
				table = $('#J-chart-table'),
				chartTbody = $('#J-chart-content');
			
			
			//操作面板显示/隐藏
			buttonControl.click(function(e){
				var ico = buttonControl.parent().find('i'),cls1 = 'arrow-down',cls2 = 'arrow-up';
				panelControl.toggle();
				if(ico.hasClass(cls1)){
					ico.removeClass(cls1).addClass(cls2);
				}else{
					ico.removeClass(cls2).addClass(cls1);
				}
				e.preventDefault();
			});
			
			
			//头部显示控制区
			panelControl.on('click', '[data-action]', function(e){
				var el = $(e.target),action = el.attr('data-action'),cls = '';
				switch(action){
					//号温
					case 'temperature':
						cls = 'table-temperature';
						if(table.hasClass(cls)){
							table.removeClass(cls);
						}else{
							table.addClass(cls);
						}
					break;
					//遗漏条
					case 'lost-post':
						cls = 'table-lost-post';
						if(table.hasClass(cls)){
							table.removeClass(cls);
						}else{
							table.addClass(cls);
						}
					break;
					//辅助线
					case 'guides':
						cls = 'table-guides';
						if(chartTbody.hasClass(cls)){
							chartTbody.removeClass(cls);
						}else{
							chartTbody.addClass(cls);
						}
					break;
					//遗漏数
					case 'lost':
						cls = 'table-lost';
						if(table.hasClass(cls)){
							table.removeClass(cls);
						}else{
							table.addClass(cls);
						}
					break;
					//走势图
					case 'trend':
						cls = 'table-trend';
						if($('body').hasClass(cls)){
							$('body').removeClass(cls);
						}else{
							$('body').addClass(cls);
						}
					break;
					default:
					break;
				}
			});
			
			//选球
			$('#J-select-content').on('click', '[data-action]', function(e){
				var el = $(e.target),action = el.attr('data-action');
				switch(action){
					case 'addSelect':
						CHART.addSelectRow();
					break;
					case 'delSelectRow':
						CHART.delSelectRow(el.parent().parent());
					break;
					case 'selectBall':
						if(el.hasClass('ball-orange')){
							el.removeClass('ball-orange');
						}else{
							el.addClass('ball-orange');
						}
					break;
					default:
					break;
				}
			});
			
			//
			var hiddValue =function(){
				$('#periodsType,#periodsNum,#startTime,#endTime').val('');
			}

			//期数读取
			periodsControl.on('click', '[data-action]', function(e){
				var el = $(e.target),action = el.attr('data-action'),cls = '';
				switch(action){
					//30期
					case 'periods-30':
						hiddValue();
						$('#periodsType').val('periods');
						$('#periodsNum').val(30);
						
						CHART.changePeriodsShow({
							dateType : 'periods',
							dateNum : 30
						});
						
					break;
					//50期
					case 'periods-50':
						hiddValue();
						$('#periodsType').val('periods');
						$('#periodsNum').val(50);
						
						CHART.changePeriodsShow({
							dateType : 'periods',
							dateNum : 50
						});
						
					break;
					//1天
					case 'day-1':
						hiddValue();
						$('#periodsType').val('day');
						$('#periodsNum').val(1);
						
						CHART.changePeriodsShow({
							dateType : 'day',
							dateNum : 1
						});
						
					break;
					//2天
					case 'day-2':
						hiddValue();
						$('#periodsType').val('day');
						$('#periodsNum').val(2);
						
						CHART.changePeriodsShow({
							dateType : 'day',
							dateNum : 2
						});
						
					break;
					//3天
					case 'day-5':
						hiddValue();
						$('#periodsType').val('day');
						$('#periodsNum').val(5);
						
						CHART.changePeriodsShow({
							dateType : 'day',
							dateNum : 5
						});
						
					break;
					default:
					break;
				}
			});

			//时间戳期数读取
			timePeriods.click(function(){
				var startTimeDom = $('#J-date-star'),
					endTimeDom = $('#J-date-end'),
					startTime = startTimeDom.val(),
					endTime = endTimeDom.val();

				if(startTime == ''){
					startTimeDom.focus();
					return;
				}

				if(endTime== ''){
					endTimeDom.focus();
					return;
				}
				hiddValue();
				$('#periodsType').val('time');
				$('#startTime').val(startTime);
				$('#endTime').val(endTime);				
				
				CHART.changePeriodsShow({
					dateType : 'time',
					dateNum : {
						startTime: startTime,
						endTime: endTime
					}
				});
			});
			
			//每次修改期数参数
			//修改报表下载链接地址
			CHART.addEvent('afterChangePeriods', function(e, dateType, dateNum, gameType) {
				reportDownload.attr('href', 'http://www.ph158.com/reportDownload/gametype=' + gameType +'?dataType=' +dateType+'?dataNum=' + dateNum);
			});
			
			//日期参数输入框
			$('#J-date-star').focus(function(){
				var dt = new phoenix.DatePicker({input:this,isShowTime:false});
					dt.show();
			});
			$('#J-date-end').focus(function(){
				var dt = new phoenix.DatePicker({input:this,isShowTime:false});
					dt.show();
			});
			
		});