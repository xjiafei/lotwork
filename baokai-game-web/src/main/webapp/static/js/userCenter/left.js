(function () {
    var NOTICE_UPDATE_TIME = 30000;
    var getAppealNoticeCount = function () {
        var noticeDom = $('#fundAppealNoticeCount');
        var host = noticeDom.attr('host');
        var url = '/fundappeal/getappealnoticecount';
        if(host!=null){
            url = host+url;
        }
        $.ajax({
            url: url,
            dataType: 'json',
            method: 'get',
            success: function (data) {
                if (data > 0) {
                    noticeDom.text(data);
                    noticeDom.parent().show();
                } else {
                    noticeDom.parent().hide();
                }
            },
            error: function (e) {
            }
        });
        setTimeout(getAppealNoticeCount, NOTICE_UPDATE_TIME);
    };
    getAppealNoticeCount();
})();