var DatePickerUtil = function (pickerDom,hasTime) {
    var time_now = new Date();
    var dateFilterFn = function (event) {
        var Dt = new phoenix.DatePicker(
                {
                    input: this,
                    isShowTime: hasTime,
                    setDisabled: function () {
                        var me = this;
                        var tds = me.getContent().find('td');
                        var it, tempDate, _y, _m, _d;
                        // n天前的某个日期
                        var days = 9999;
                        var before = dateUtil.getOneDateTime(time_now, -1 * 3600 * 24 * days + 1);

                        tds.each(function () {
                            it = $(this);
                            _y = Number(it.attr('data-year'));
                            _m = Number(it.attr('data-month'));
                            _d = Number(it.text());
                            tempDate = new Date(_y, _m, _d);
                            if (tempDate < before || tempDate > dateUtil.now) {
                                it.addClass('day-disabled');
                            }
                        });
                    }
                });
        Dt.show();
    };
    var dateUtil = {
        now: time_now,
        // 获取当前日期前后n秒的日期
        getOneDateTime: function (now, n) {
            var now_ms = now.getTime(), n = n || 0, d_n = now_ms
                    + n * 1000, d2 = new Date();
            d2.setTime(d_n)
            return d2;
        },
        getYestodayBound: function () {
            var me = this, now = me.now, result = [], d = new Date();
            d.setFullYear(now.getFullYear());
            d.setMonth(now.getMonth());
            d.setDate(now.getDate() - 1);
            result.push(me.formatDateToString(d, true));
            result.push(me.formatDateToString(d, false));
            return result;
        },
        getTodayBound: function () {
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
        getBeforeWeekBound: function () {
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
        formatDateToString: function (d) {
            return d.getFullYear() + '-' + (d.getMonth() + 1)
                    + '-' + d.getDate();
        }
    };
    var time = dateUtil.getTodayBound()[0];
    if(hasTime){
        time+='  00:00:00';
    }
    if(!pickerDom.val()){
        pickerDom.val(time);
    }
    pickerDom.bind('click',dateFilterFn);
};