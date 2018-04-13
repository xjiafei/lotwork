$(document).ready(function(){
	// 日期控件js 开始
	var inputStart = $('#J-date-start'), inputEnd = $('#J-date-end'),

	dateFilterFn = function(e) {
		var Dt = new phoenix.DatePicker(
				{
					input : this,
					
					isShowTime : true,
					setDisabled : function() {
						var me = this, tds = me.getContent()
								.find('td'), it, tempDate, _y, _m, _d;
						// n天前的某个日期
						before = dateUtil
								.getOneDateTime(
										time_now,
										-1
												* 3600
												* 24
												* Number($(
														'#J-date-bound')
														.val())
												+ 1);

						tds
								.each(function() {
									it = $(this);
									_y = Number(it
											.attr('data-year'));
									_m = Number(it
											.attr('data-month'));
									_d = Number(it.text());
									tempDate = new Date(_y, _m,
											_d);
									if (tempDate < before
											|| tempDate > dateUtil.now) {
										it
												.addClass('day-disabled');
									}
								});
					}
				});
		Dt.show();
	},
	// 年月日
	time_now, _arrDate = $.trim($('#J-data-now').val()).split(
			/[^\d]/), dateUtil = {}, setStartEndTime = function(
			start, end) {
		$('#J-time-start').val(start);
		$('#J-time-end').val(end);
	}, time_y = Number(_arrDate[0]), time_m = Number(_arrDate[1]), time_d = Number(_arrDate[2]), time_h = Number(_arrDate[3]), time_s = Number(_arrDate[4]);

	time_now = new Date();

	time_now.setFullYear(time_y);
	time_now.setMonth(time_m - 1);
	time_now.setDate(time_d);
	time_now.setHours(time_h);
	time_now.setMinutes(time_s);

	dateUtil = {
		now : time_now,
		// 获取当前日期前后n秒的日期
		getOneDateTime : function(now, n) {
			var now_ms = now.getTime(), n = n || 0, d_n = now_ms
					+ n * 1000, d2 = new Date();
			d2.setTime(d_n)
			return d2;
		},
		getYestodayBound : function() {
			var me = this, now = me.now, result = [], d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate() - 1);
			result.push(me.formatDateToString(d, true));
			result.push(me.formatDateToString(d, false));
			return result;
		},
		getTodayBound : function() {
			var me = this, now = me.now, result = [], d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate());
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-'
					+ (now.getMonth() + 1) + '-'
					+ now.getDate());
			return result;
		},
		// 前一周时间
		// 7天前的 00:01 + 今天已过的时间
		// 今天当成1天计算
		getBeforeWeekBound : function() {
			var me = this, now = me.now, result = [], d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate() - 7);
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-'
					+ (now.getMonth() + 1) + '-'
					+ now.getDate());
			return result;
		},
		formatDateToString : function(d, isFirst) {
			var str = isFirst ? '00:01' : '23:59';
			return d.getFullYear() + '-' + (d.getMonth() + 1)
					+ '-' + d.getDate();
		}
	};
	inputStart.click(dateFilterFn);
	inputEnd.click(dateFilterFn);

	// 初始化首次起始时间
	setStartEndTime(dateUtil.getTodayBound()[0], dateUtil
			.getTodayBound()[1]);

	// 日期控件js 结束
});