function showRecycleStatus(index) {
    var Wd = phoenix.Message.getInstance();
    var table = $('#levelRecycle-table');
    //点击的回收状态
    current_recycleStatus = $('#recycleStatus' + index).val();
    //点击的用户帐号
    current_account_index = index;

    Wd.show({
        mask: true,
        title: '回收状态',
        content: '<div"><div class="pop-title table-tool">\n\
                                <div class="bd text-center">\n\
                                    <table>\n\
                                    <thead>\n\
                                        <th width="20%"></th>\n\
                                        <th width="40%"></th>\n\
                                        <th width="40%"></th>\n\
                                    </thead>\n\
                                    <tbody>\n\
                                           <tr><td>1.</td><td>清除奖金组</td><td>' + checkStatus(1) + '</td></tr>\n\
                                           <tr><td>2.</td><td>清理安全中心</td><td>' + checkStatus(2) + '</td></tr>\n\
                                           <tr><td>3.</td><td>清理个人资料</td><td>' + checkStatus(3) + '</td></tr>\n\
                                           <tr><td>4.</td><td>清理银行卡信息</td><td>' + checkStatus(4) + '</td></tr>\n\
                                           <tr><td>5.</td><td>清理投注纪录</td><td>' + checkStatus(5) + '</td></tr>\n\
                                           <tr><td>6.</td><td>清理站内信</td><td>' + checkStatus(6) + '</td></tr>\n\
                                           <tr><td>7.</td><td>重置PT密码</td><td>' + checkStatus(7) + '</td></tr>\n\
                                           <tr><td>8.</td><td>重置平台登录密码</td><td>' + checkStatus(8) + '</td></tr>\n\
                                    </tbody>\n\
                                </table>\n\
                            </div>\n\
                            </div></div>',
        confirmIsShow: false,
        cancelIsShow: false,
        confirmFun: function () {
            $.ajax({
                url: baseUrl + "/aclAdmin/closeGroup?id=" + id + "&inUser=0&userId=1" + "&num=" + Math.random() + "&pid=" + pid,
                dataType: 'json',
                success: function (data) {
                    if (data.status == 1) {
                        window.location.href = baseUrl + "/aclAdmin/queryAclGroup?userId=1";
                    }
                    else {
                        alert(data.message);
                    }
                    Wd.hide();
                },
                error: function (xhr, status, errMsg) {
                    alert(errMsg);
                    Wd.hide();
                }

            });
        },
        cancelFun: function () {
            Wd.hide();
        }
    });


}


function checkStatus(checkIndex) {
    var startIndex = parseInt(checkIndex) - 1;
    var endIndex = startIndex + 1;
    var current_status = parseInt(current_recycleStatus.substring(startIndex, endIndex));
    var statusIcon = '';
    if (current_status != 1) {
        statusIcon = ' <a id=a'+checkIndex+' class="ico-wrong"></a><td>';
		if($('#canRedoTarget').val()==1){
			statusIcon += '<button onclick="recycleAction('+checkIndex+')" id=button'+checkIndex+' type="button" class="recycle_btn" style="width:50px;height:20px;font-size:12px">重置</button>';
		}
        statusIcon += '</td>';
	} else {
        statusIcon = ' <a class="ico-right"></a><td></td>';
    }
    return statusIcon;
}

//回收功能重置
function recycleAction(checkIndex) {
    $.ajax({
        url: '/admin/user/resetlevelrecycle',
        data:{
            actionName: getActionName(checkIndex),
            id: $('#id'+current_account_index).val(), 
            account: $('#account'+current_account_index).val(), 
            userId: $('#userId'+current_account_index).val(), 
            operator: $('#operator').val(),
            recycleStatus: $('#recycleStatus'+current_account_index).val()
            },
        dataType: 'json',        
        beforeSend: function(xhr){
            $('.recycle_btn').attr('disabled', 'disabled');
            $('#button'+checkIndex).text('处理中');
         },
        success: function (data) {
            if (data['body']['result']['status'] == "SUCCESS") {
                var return_recycleStatus = data['body']['result']['recycleStatus'];
                $('#recycleStatus'+current_account_index).val(return_recycleStatus)
                $("#a"+checkIndex).attr('class', 'ico-right');
                $('#button'+checkIndex).hide();
                $('.recycle_btn').attr('disabled', false);
            }else{
                alert("重置失败");
                $('.recycle_btn').attr('disabled', false);
            }
        },
        error: function (xhr, status, errMsg) {
            $('.recycle_btn').attr('disabled', false);
            alert(errMsg);
        },
        complete: function (XMLHttpRequest, textStatus) {
            $('#button'+checkIndex).text('重置');
        }        
    });
}

function getActionName(checkIndex){   
    var name = '';
    switch (parseInt(checkIndex)) {
    case 1:
        name = "cleanAwardGroup";
        break;
    case 2:
        name = "cleanSafeCenter";
        break;
    case 3:
        name = "cleanPersonalinfo";
        break;
    case 4:
        name = "cleanBindCard";
        break;
    case 5:
        name = "cleanOrderHistory";
        break;
    case 6:
        name = "cleanUserMessage";
        break;
    case 7:
        name = "resetPtPassword";
        break;
    case 8:
        name = "resetLoginPassword";
        break;
    }
    return name;
}




