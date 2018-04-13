$(function(){
    var guideUrl = '/iapi/begin/mission/missioninfoAction';
    var token = '';
    var homeUrl = 'phlotto://go_lottolobby';

    function getQueryString(name){
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null){
            return unescape(r[2]);
        }
        return null; 
    }
    
    function converString(strV) {   
        while(strV.search(/\-/) != -1) {
            strV = strV.replace(/\-/, "/");
        }
        while(strV.search(/\$/) != -1) {
            strV = strV.replace(/\$/, "%");
        }
        while(strV.search(/\!/) != -1) {
            strV = strV.replace(/\!/, "=");
        }
        while(strV.search(/\*/) != -1) {
            strV = strV.replace(/\*/, "+");
        } 
        return strV;
    }

    token = getQueryString('token');
    if (IsParameterReady(token)) {
        token = converString(token);
    }
    
    function IsParameterReady(par) {
        if (typeof(par) == 'undefined' || !par) {
            if (par !== 0) {
                return false;
            }
        }
        return true;
    }
    if (!IsParameterReady(token)) {
        window.location.href = homeUrl;
    }
    $.ajax({
        type: 'POST',
        url: guideUrl,
        contentType: "application/json; charset=utf-8",
        processData: false,
        data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}',
        dataType: 'json'
    }).done(function(result) {
        var guideReturnStatus = result['body']['result']['isSuccess'];
        if (!IsParameterReady(guideReturnStatus) || guideReturnStatus !== 1) {
            alert('未知网络错误');
            window.location.href = homeUrl;
        }

        var guideReturnData = result['body']['result']['data']['help'];
        if (IsParameterReady(guideReturnData)) {
            for (var tr=0; tr<guideReturnData.length; tr++) {
                for (var td=0; td<guideReturnData[tr].length; td++ ) {
                    $('.guide-table').find('tr').eq(tr+1).find('td').eq(td).html(guideReturnData[tr][td]);
                }
            }
        } else {
            alert('未知网络错误');
            window.location.href = homeUrl;
        }
    }).fail(function(xhr) {
        if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
            alert('请重新登录');
        }else{
            alert('未知网络错误');
        }
        window.location.href = homeUrl;
    });
});