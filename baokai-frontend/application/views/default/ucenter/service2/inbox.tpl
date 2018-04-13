<!DOCTYPE HTML>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>{$title}</title>
        <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
        <link rel="stylesheet" href="{$path_img}/images/service/service.css" />
        {include file='/default/script-base.tpl'}
    <body>
        <!-- //////////////头部公共页面////////////// -->
        {include file='/default/ucenter/header.tpl'}
        <!-- /////////////头部公共页面/////////////// -->
        <div class="g_33 common-main">
            <div class="g_5">
                <!-- //////////////左侧公共页面////////////// -->
                {include file='/default/ucenter/left.tpl'}
                <!-- /////////////左侧公共页面/////////////// -->	
            </div>
            <div class="g_28 g_last">
                <div class="common-article">
                    <div class="title">
                        <div class="more">
                            {if $smarty.session.datas.info.userLvl neq 0}
                                <a href="/Service2/servicesup?unread={$unreadCnt}">给上级发信</a>
                            {/if}
                            {if $smarty.session.datas.info.userLvl >= 0}
                                <a href="/Service2/servicesub?unread={$unreadCnt}">给下级发信</a>
                            {/if}
                        </div>
                        站内信(<span id="msgcount2">0</span>)
                <!--{if $unreadCnt}站内信（{$unreadCnt}） {else} 站内信（0） {/if}-->
                    </div>
                    {if $receives}
                        <div class="content">
                            <div class="ui-tab">
                                <div class="ui-tab-content" id="Divnbox">
                                    <table class="table table-info table-mail">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>发件人</th>
                                                <th style="width:480px;" class="text-left">内容</th>
                                                <th>最后回覆时间</th>
                                            </tr>
                                        </thead>
                                        <tbody>		
                                            {foreach from=$receives key=myId item=i}
                                                {if $i.type == 1}
                                                    <tr>
                                                        <td class="cb">
                                                            <input value="{$i.id}" data-read="{$i.isRead eq 1}" type="checkbox" class="checkbox"  name="checkboxs"  id="{$i.id}"  pro="{$i.sendAccount}"/>
                                                        </td>
                                                        <td>{$i.sendAccount}</td>
                                                        <td class="txtMsgContent text color-red">
                                                            <i class="ico-mail-system">系统{if $i.sendMsgRout > 0}({$i.sendMsgRout}){/if}</i> 
                                                            <a isContent="true" style="{if $i.isRead eq 1}color:#666666{else}color:red{/if}"   href="/Service2/sysmessages?id={$i.id}&msgTpye=sMsg&unread={$unreadCnt}&pro=我" >
                                                                {$i.content}
                                                            </a>
                                                        </td>
                                                        <td>{if $i.sendTime}{date('Y-m-d G:i:s',getSrtTimeByLong($i.sendTime))}{else}-{/if}</td> 
                                                    </tr>
                                                {else}
                                                    <tr>
                                                        <!-- receiveMsgROUT -->
                                                        <td class="cb">
                                                            <input value="{$i.id}" data-read="{$i.isRead eq 1}" type="checkbox" class="checkbox" id="{$i.id}" name="checkboxs" pro="{$i.sendAccount}"/>
                                                        </td>
                                                        <td>
                                                            {if ($i.sendAccount neq $smarty.session.datas.info.parentAccount ) and ( $i.sendAccount neq $smarty.session.datas.info.account)}
                                                                下级玩家{$i.sendAccount}
                                                            {else if $i.sendAccount eq $smarty.session.datas.info.parentAccount}
                                                                上级代理
                                                            {else if $i.sendAccount eq $smarty.session.datas.info.account}
                                                                下级玩家{$i.receiveAccount}
                                                            {/if}
                                                            {if $i.sendMsgRout > 0}({$i.sendMsgRout}){/if}
                                                        </td>										
                                                        <td class="txtMsgContent text {if $i.isRead eq 1}color-red{else}color-green{/if}">
                                                            <a isContent="true" style="{if $i.isRead eq 1}color:#666666{else}color:green{/if}"    href="/Service2/sysmessages?id={$i.id}&msgTpye=uMsg&unread={$unreadCnt}&pro={$i.sendAccount}" >
                                                                {$i.content}
                                                            </a>
                                                        </td>
                                                        <td>{if $i.sendTime}{date('Y-m-d G:i:s',getSrtTimeByLong($i.sendTime))}{else}-{/if}</td> 
                                                    </tr>
                                                {/if} 
                                            {/foreach}	
                                        </tbody>
                                    </table>
                                    <div class="page-wrapper clearfix">
                                        <div  class="page-short page-right">
                                            {if $pages}
                                                {if $pages.pre && $pages.currpage.index!=1}
                                                    <i class="prev"></i>
                                                    <a href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}" >上一页</a>
                                                {/if}
                                                <span>{$pages.currpage.text}/{$pages.max.text}</span>
                                                {if $pages.next && $pages.currpage.index!=$pages.max.index}
                                                    <a  href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}">下一页</a>
                                                    <i class="next"></i>
                                                {/if}
                                            {/if}
                                        </div>
                                        <span class="page-text">
                                            <label class="label"><input type="checkbox" class="checkbox" id="ckxAll"  />全选</label>								
                                            <a href="javascript:void(0);" class="btn" id="AllMarkRead" style="display:none">全部标为已读<b class="btn-inner"></b></a>
                                            <a href="javascript:void(0);" class="btn disable" id="btnAllDisplay" >全部标为已读<b class="btn-inner"></b></a>
                                            <a href="javascript:void(0);" class="btn" id="DeleteInfo">删 除<b class="btn-inner"></b></a>
                                            <span class="lower">提示：系统将自动清空5天前的用户消息记录</span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    {else}
                        <div class="content">
                            <div class="ui-tab-content">
                                <table class="table table-info table-mail">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th style="width:480px;" class="text-left">主题</th>
                                            <th>发件人</th>
                                            <th>时间</th>
                                        </tr>
                                    </thead>
                                </table>
                                <div class="alert alert-waring">
                                    <i></i>
                                    <div class="txt">
                                        <h4>暂无消息</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    {/if}			
                </div>
            </div>
        </div>
        <!-- //////////////底侧公共页面////////////// -->
        {include file='/default/ucenter/footer.tpl'}
        <!-- /////////////底侧公共页面/////////////// -->	
        <div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="DivMarkReadOk">
            <i class="ico-success"></i>
            <h4 class="pop-text">标为已读成功</h4>
        </div>
        <div style="position:absolute;z-index:2; display:none" class="pop pop-success w-4" id="DivDeleteOK">
            <i class="ico-success"></i>
            <h4 class="pop-text">删除成功</h4>
        </div>
        <div style="position:absolute;left:700px;display:none" class="pop pop-error w-4" id="DivMarkReaNo">
            <i class="ico-error"></i>
            <h4 class="pop-text">标为失败，请重试</h4>
        </div>
        <div style="position:absolute;left:1000px;display:none" class="pop pop-error w-4" id="DivDeleteNo">
            <i class="ico-error"></i>
            <h4 class="pop-text">删除失败，请重试</h4>
        </div>
        <div style="position:absolute;left:1000px;display:none" class="pop pop-success w-4" id="DivMessage">
            <i class="ico-success"></i>
            <h4 class="pop-text">请先选择一条站内信</h4>
        </div>
        <div style="position:absolute;left:1000px;display:none" class="pop pop-success w-4" id="DivNoMessage">
            <i class="ico-success"></i>
            <h4 class="pop-text">请先选择一条未读站内信</h4>
        </div>
        {literal}
            <script>
                (function ($) {
                    var msgContents = $('.txtMsgContent');
                    $.each(msgContents,function(){
                       var as = $(this).children().not($(this).find('i'));
                       var text = as.text();
                       $.each(as,function(){
                          if($(this).attr('isContent')){
                              $(this).text(text);
                          }else{
                              $(this).remove();
                          }
                       });
                    });
                    //判断是否为已读邮件
                    function checkIsRead(dom) {
                        var saveReady = 0,
                                objs = dom,
                                numSize = objs.length;
                        if (numSize > 1) {
                            $.each(objs, function (i) {
                                if ($(this).attr('data-read')) {
                                    saveReady++;
                                }
                            });
                            return saveReady == numSize;
                        } else {
                            if ($(objs).attr('data-read')) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                    //代理标记按钮已读事件
                    $('input[name="checkboxs"]').on('click', function () {
                        var objs = $('input[name="checkboxs"]'),
                                checkDom = [],
                                markReadDom = $('#AllMarkRead'),
                                displayBtnDom = $('#btnAllDisplay');
                        for (var i = 0; i < objs.length; i++) {
                            if (objs[i].checked) {
                                checkDom.push(objs[i]);
                            }
                        }
                        if (checkIsRead(checkDom)) {
                            markReadDom.show();
                            displayBtnDom.hide();
                        } else {
                            markReadDom.hide()
                            displayBtnDom.show();
                        }
                    });
                    //全选			
                    $('#ckxAll').click(function ()
                    {
                        if ($('#ckxAll,input:checkbox').prop("checked"))
                        {
                            $('#AllMarkRead').hide()
                            $('#btnAllDisplay').show();
                        } else
                        {
                            $('#AllMarkRead').show();
                            $('#btnAllDisplay').hide();
                        }
                        $('#ckxAll,input:checkbox').prop("checked", this.checked);
                    });
                    //全部标记为已读
                    $('#AllMarkRead').click(function (e) {
                        var objs = $('input[name="checkboxs"]'),
                        checkDom = [];
                        for (var i = 0; i < objs.length; i++) {
                            if (objs[i].checked) {
                                checkDom.push(objs[i]);
                            }
                        }
                        if (checkDom.length == 0) {
                            fn("DivMessage");
                            setTimeout(function ()
                            {
                                $("#DivMessage").css("display", "none");
                            }
                            , 2000);
                            return;
                        }
                        var isTrue = true;
                        $(checkDom).each(function (index, element) {
                            var me = $(this).parent().parent().find('a');
                            if ($(me).attr('style').indexOf("red") > 0 || $(me).attr('style').indexOf("green") > 0)
                            {
                                isTrue = false;
                                return false;
                            }
                        });
                        if (isTrue) {
                            fn("DivNoMessage");
                            setTimeout(function ()
                            {
                                $("#DivNoMessage").css("display", "none");
                            }
                            , 2000);
                            return;
                        }
                        try {
                            Publicmethod("/service2/markread");
                        } catch (err) {
                            e.preventDefault();
                            return;
                        }
                    });
                    //选中删除
                    $('#DeleteInfo').click(function (e) {
                        try {
                            Deletemessages("/service2/deletemessages");
                        } catch (err) {
                            e.preventDefault();
                            return;
                        }
                    });
                    //标记为已读
                    function Publicmethod(obj)
                    {
                        //获取选中ckeckbox
                        var istrue = true;
                        var objs = $('input[name="checkboxs"]');
                        var sy = '', pro1 = '';
                        for (var i = 0; i < objs.length; i++) {
                            if (objs[i].checked) {
                                sy += objs[i].value + ',';
                                pro1 += $('input[name="checkboxs"]').attr('pro') + ',';
                            }
                        }
                        if (sy == '' && pro1 == '') {
                            istrue = false;
                        }
                        //去除尾数逗号
                        var reg = /,$/gi;
                        var idStr = sy.replace(reg, "");
                        var pros1 = pro1.replace(reg, "");
                        if (istrue == false) {
                            obj.preventDefault();
                            return false;
                        }
                        try {
                            $.ajax({
                                type: 'post',
                                dataType: 'json',
                                cache: false,
                                url: obj,
                                //arrys拆出传参数，已保存勾选数据myId
                                data: 'id=' + idStr + '&pro=' + pros1,
                                beforeSend: function () {
                                    $('#AllMarkRead').hide();
                                    $('#btnAllDisplay').show();
                                    $('#btnAllDisplay').text("执行中...");
                                },
                                success: function (json) {
                                    if (json['status'] == "0") {
                                        fn("DivMarkReadOk");
                                        setTimeout(function () {
                                            $("#DivMarkReadOk").css("display", "none");
                                            window.location = window.location;
                                        }, 1500);
                                    }
                                    else {
                                        fn("DivMarkReaNo");
                                        setTimeout(function () {
                                            $("#DivMarkReaNo").css("display", "none");
                                        }, 2000);
                                    }
                                },
                                error: function (xhr, type)
                                {
                                    fn("DivMarkReaNo");
                                    setTimeout(function () {
                                        $("#DivMarkReaNo").css("display", "none");
                                    }, 2000);
                                },
                                complete: function ()
                                {
                                    $('#AllMarkRead').show();
                                    $('#btnAllDisplay').hide();
                                    $('#btnAllDisplay').text("全部标为已读");
                                    $('#DeleteInfo').attr('disabled', true)
                                }
                            });
                        } catch (err) {
                            return err.message;
                        }
                    }
                    //选择删除
                    function Deletemessages(obj)
                    {
                        //debugger
                        //获取选中ckeckbox
                        var istrue = true;
                        var objs = $('input[name="checkboxs"]');
                        var sy = '', pro1 = '';
                        for (var i = 0; i < objs.length; i++) {
                            if (objs[i].checked) {
                                sy += objs[i].value + ',';
                                pro1 += $(objs[i]).attr('pro') + ',';
                            }
                        }
                        if (sy == '' && pro1 == '') {
                            istrue = false;
                        }
                        //去除尾数逗号
                        var reg = /,$/gi;
                        var idStr = sy.replace(reg, "");
                        var pros1 = pro1.replace(reg, "");
                        if (istrue == false) {
                            return false;
                        }
                        try {
                            //debugger
                            var istrue = confirm("您确定要删除该组站内信吗？\n删除后将不能恢复。")
                            if (!istrue) {
                                return false;
                            }
                            $.ajax({
                                type: 'post',
                                dataType: 'json',
                                cache: false,
                                url: obj,
                                data: 'id=' + idStr + '&pro=' + pros1,
                                beforeSend: function () {
                                    //禁用发送								
                                    $('#DeleteInfo').val("删除中...");
                                    $("#DeleteInfo").css("color", "#CACACA").css("background-color", "#CACACA");
                                    $("#DeleteInfo").attr('disabled', false);
                                },
                                success: function (json) {
                                    if (json['status'] == "0") {
                                        fn("DivDeleteOK");
                                        setTimeout(function () {
                                            $("#DivDeleteOK").css("display", "none");
                                            window.location = "/Service2/inbox";
                                        }, 1500);
                                    }
                                    else {
                                        fn("DivDeleteNo");
                                        setTimeout(function () {
                                            $("#DivDeleteNo").css("display", "none");
                                        }, 2000);
                                    }
                                },
                                error: function (xhr, type) {
                                    fn("DivDeleteNo");
                                    setTimeout(function () {
                                        $("#DivDeleteNo").css("display", "none");
                                    }, 2000);
                                },
                                complete: function () {
                                    $("#DeleteInfo").attr('disabled', true);
                                }
                            });
                        } catch (err) {
                            fn("DivDeleteNo");
                            setTimeout(function () {
                                $("#DivDeleteNo").css("display", "none");
                            }, 2000);
                        }
                    }
                    //操作后提示	 
                    function fn(obj) {
                        var Idivdok = document.getElementById(obj);
                        Idivdok.style.display = "block";
                        Idivdok.style.left = (document.documentElement.clientWidth - Idivdok.clientWidth) / 2 + document.documentElement.scrollLeft + "px";
                        Idivdok.style.top = (document.documentElement.clientHeight - Idivdok.clientHeight) / 2 + document.documentElement.scrollTop - 40 + "px";
                    }
                })(jQuery);
            </script>
        {/literal}
    </body>
</html>