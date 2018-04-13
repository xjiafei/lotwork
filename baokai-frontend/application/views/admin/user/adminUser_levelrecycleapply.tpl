<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; <a href="/admin/user/levelrecycle?type=list">一代回收</a> &gt; {$account}</div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div class="ui-tab">
                        <ul class="prompt" style="margin:10px 10px 0;">
                            <li>功能说明:</li>
                            <li>1.帐户被回收后密码将会被重置为123qweasd客户首次登入后将会强制提示修改登录密码。</li>
                            <li>2.帐户回收后将会清理客户个人资料里的"性别、邮箱、手机号、生日、QQ联系"信息。</li>
                            <li>3.回收后将会清理客户的站内信、投注/追号纪录(包含PT)、奖金组、绑定邮箱、安全问题、安全密码手机令牌、预留验证信息、银行卡绑定等所有信息。</li>
                            <li>4.有未完成追号无法进行一代回收</li>
                        </ul>
                        <form action="sendlevelrecycle" method="POST" id="applyLevelRecycle">
                            <ul class="ui-form" id="J-search-panel">
                                <li>
                                    <label class="ui-label">用户帐号: </label> <span class="ui-text-info">{$account}<!-- {if $viplevel!= '' && $viplevel != '0'}-->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$viplevel}.png"><!-- {/if} --></span><br>
                                    <label class="ui-label">所属总代 : </label> <span class="ui-text-info">{$topAgent}</span><br>
                                    <label class="ui-label">平台余额 : </label> <span class="ui-text-info">{$format_availBal} 元</span><br>
                                    <label class="ui-label">PT金额 : </label> <span class="ui-text-info">{$format_availPtBal} 元</span><br>
                                    <label class="ui-label">最后一次登录时间 : </label> <span class="ui-text-info">{$format_lastLoginDate}<br> [{$format_lastLoginIp}] {$lastLoginAddress}</span><br><br><br>
                                    <label class="ui-label">回收原因 : </label>
                                    <div class="textarea w-5">
                                        <textarea id="levelrecycleReason" name="recycleReason"></textarea>
                                        <span class="ui-check" style="display: none;"><i></i><label>回收原因内容不能为空</label></span>
                                    </div>
                                <li class="ui-btn">
                            <!-- {if "USER_MANAGE_LEVELRECYCLE_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                    <a class="btn btn-important" id="applySubmit">回 收<b class="btn-inner"></b></a>
                            <!-- {/if} -->
                                    <a class="btn" id="J-form-rollback" href="/admin/user/levelrecyclelist?frsearch=1&searchtypetxt={$account}">取 消<b class="btn-inner"></b></a>
                                </li>
                            </ul>                                        
                            <input type="hidden" name="userId" value="{$userId}" />
                            <input type="hidden" name="account" value="{$account}" />
                            <input type="hidden" name="topAgent" value="{$topAgent}" />
                            <input type="hidden" name="availBal" value="{$availBal}" />
                            <input type="hidden" name="availPtBal" value="{$availPtBal}" />
                            <input type="hidden" name="lastLoginDate" value="{$lastLoginDate}" />
                            <input type="hidden" name="lastLoginIp" value="{$lastLoginIp}" />
                            <input type="hidden" name="lastLoginAddress" value="{$lastLoginAddress}" />
                        </form>    
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="pop pop-success w-4" style="position:absolute;left:900px;display:none;z-index: 800;" id="divApplySuccess">
        <i class="ico-success"></i><h4 class="pop-text">申请成功</h4>
    </div>
    <div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none;z-index: 800;" id="divApplyFailure">
        <i class="ico-error"></i><h4 class="pop-text">申请失败，请重试</h4>
    </div>
    {include file='/admin/script-base.tpl'}
    {literal}
        <script >
            (function ($) {
                //一、二级菜单选中样式加载	
                selectMenu('MenuUser', 'MenuUserlevelrecycle');

                //表单提交校验
                $('#applySubmit').click(function (e) {
                    if ($('#levelrecycleReason')[0].value.trim() !== '') {
                        $('.ui-check').hide();
                        if($('#levelrecycleReason').val().length<400){
                            $('#recycleReason').val($('#levelrecycleReason').val());
                            var userId =  $('#userId').val();
                            sendApply();
                        }else{
                            $('.ui-check label').text("超过400字数上限！");
                            $('.ui-check').show();
                        }
                    } else {
                        $('.ui-check').show();
                    }
                });

                function sendApply() {
                    $.ajax({
                        url: '/admin/user/sendlevelrecycle',
                        dataType: 'json',
                        data: $('#applyLevelRecycle').serialize(),
                        type: "post",
                        success: function (data) {
                            //alert(JSON.stringify(data));
                            var rtnStatus = data['body']['result']['status'];
                            var rtnMessage = data['body']['result']['message'];
                            if (rtnStatus == "SUCCESS") {
                                if(rtnMessage == ""){
                                    fn("divApplySuccess","申请成功");
                                    setTimeout(function () {window.location.href = '/admin/user/levelrecycle?type=list';}, 1000);                                    
                                }else{
                                    fn("divApplyFailure",rtnMessage);
                                    setTimeout(function () {$('#divApplyFailure').hide()}, 2000);
                                }
                            } else {
                                fn("divApplyFailure",rtnMessage);
                                setTimeout(function () {$('#divApplyFailure').hide()}, 2000);
                            }
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            fn("divApplyFailure","系统错误");
                            setTimeout(function () {$('#divApplyFailure').hide()}, 1000);
                        }
                    });
                }

                function fn(obj, msg) {
                    var Idivdok = document.getElementById(obj);
                    Idivdok.style.display = "block";
                    Idivdok.style.left = (document.documentElement.clientWidth - Idivdok.clientWidth) / 2 + document.documentElement.scrollLeft + "px";
                    Idivdok.style.top = (document.documentElement.clientHeight - Idivdok.clientHeight) + document.documentElement.scrollTop - 300 + "px";
                    if(msg!=""){
                        $(".pop-text").text(msg)                        
                    }
                }         

            })(jQuery);
        </script>
    {/literal}
</body>
</html>