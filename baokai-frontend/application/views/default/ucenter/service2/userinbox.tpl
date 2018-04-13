<!DOCTYPE HTML>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>站内信</title>
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
                                <a href="/Service2/servicesup?unread={$unread}">给上级发信</a>
                            {/if}
                            {if $smarty.session.datas.info.userLvl >= 0}
                                <a href="/Service2/servicesub?unread={$unread}">给下级发信</a>
                            {/if}
                        </div>	站内信(<span id="msgcount2">0</span>)
                    </div>
                    <div class="content">
                        <div class="ui-tab">
                            <div class="ui-tab-content" style="height:600px;overflow-y:auto;position:relative;">
                                <ul class="ui-form form-mail-info">
                                    <li>
                                        <label for="" class="ui-label">发件人：</label>
                                        <span class="ui-text-info" id="sender">
                                            {if $messageInfo.sendAccount eq $smarty.session.datas.info.account}
                                                我
                                            {else}
                                                {if $parentaccount eq $messageInfo.sendAccount}
                                                    上级
                                                {else}
                                                     {$messageInfo.sendAccount}
                                                {/if}
                                            {/if}					
                                        </span>
                                    </li>
                                    <li>
                                        <label for="" class="ui-label">时间：</label>
                                        <span class="ui-text-info">{$messageInfo.sendTime}</span>
                                    </li>
                                    <li>
                                        <label for="" class="ui-label">收件人：</label>
                                        <span class="ui-multiline" id="receiver" style="word-wrap:break-word;">
                                            {if $messageInfo.sendAccount eq $pro}
                                                我
                                            {else}
                                                {if $parentaccount eq $pro}
                                                    上级
                                                {else}
                                                     {$pro}
                                                {/if}
                                            {/if}
                                        </span>
                                    </li>
                                </ul>
                                <input type="hidden" name="sendMsgRout" id="sendMsgRout" value="{$result[0].sendMsgRout}">
                                <input type="hidden" name="rootId" id="rootId" value="{$result[0].id}">
                                <div class="dia-list" id="divApendto">
                                    {foreach from=$result key=k item=v}
                                        {if $v.sendAccount neq $smarty.session.datas.info.account}		
                                            <dl class="dia-list-left">
                                                <dt><strong>
                                                    {if $v.sendAccount eq $smarty.session.datas.info.parentAccount}
                                                        上级代理
                                                    {else}
                                                        {$v.sendAccount}
                                                    {/if}
                                                </strong> {$v.sendTime}</dt>
                                                <dd class="talkContent"><i pids={$v.id} class="tri"></i>{$v.content}</dd>
                                            </dl>
                                        {else}
                                            <dl class="dia-list-right" id="DivMessage">
                                                <dt><strong>
                                                    我
                                                </strong> {$v.sendTime}</dt>
                                                <dd class="talkContent"><i pids={$v.id} class="tri"></i>{$v.content} </dd>
                                            </dl>			
                                        {/if}
                                    {/foreach}		 
                                </div>
                                <!-- {if $result[0].sender gt 0} -->	
                                <div  class="dia-list"  id="isDivShow">
                                    <div class="dia-input">
                                        <div class="textarea"   id="openDIv">
                                            <textarea id="txttarea" >点击回复</textarea>
                                        </div>
                                        <div class="dia-btn">
                                        </div>
                                        <div class="dia-input dia-input-current"  >
                                            <div class="textarea"  id="showDiv" style=" display:none">
                                                <textarea id="clickTos"></textarea>
                                            </div>
                                            <div class="dia-btn">
                                                <a class="btn btn-important" href="javascript:void(0);" id="btnReply">回 复<b class="btn-inner"></b></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- {/if} -->
                            </div>
                        </div>
                    </div>
                </div>
            </div></div>
        <!-- //////////////底侧公共页面////////////// -->
        {include file='/default/ucenter/footer.tpl'}
        <!-- /////////////底侧公共页面/////////////// -->
        <script>
            var pathImgUrl = '{$path_img}';
        </script>
        {literal}
            <script>
                // 初始化缓存，页面仅仅加载一次就可以了
                (function ($) {
                    function Hashtable() {
                        this._hash = new Object();
                        this.put = function (key, value) {
                            if (typeof (key) != "undefined") {
                                if (this.containsKey(key) == false) {
                                    this._hash[key] = typeof (value) == "undefined" ? null : value;
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                        this.remove = function (key) {
                            delete this._hash[key];
                        }
                        this.size = function () {
                            var i = 0;
                            for (var k in this._hash) {
                                i++;
                            }
                            return i;
                        }
                        this.get = function (key) {
                            return this._hash[key];
                        }
                        this.containsKey = function (key) {
                            return typeof (this._hash[key]) != "undefined";
                        }
                        this.clear = function () {
                            for (var k in this._hash) {
                                delete this._hash[k];
                            }
                        }
                    }
                    var uSinaEmotionsHt = new Hashtable();
                    var emotions = [];
                    var categorys = [];
                    var analyticEmotion = function(s) {
                        if (typeof (s) != "undefined") {
                            var sArr = s.match(/\[.*?\]/g);
                            if (!sArr) {
                                return s;
                            }
                            for (var i = 0; i < sArr.length; i++) {
                                if (uSinaEmotionsHt.containsKey(sArr[i])) {
                                    var reStr = "<img src=\"" + pathImgUrl+'/'+uSinaEmotionsHt.get(sArr[i]) + "\" height=\"22\" width=\"22\" />";
                                    s = s.replace(sArr[i], reStr);
                                }
                            }
                        }
                        return s;
                    };
                    var changePageEmotions = function(){
                        var talkContents = $('.talkContent');
                        talkContents.each(function(){
                            var content = $(this).html();
                            $(this).html(analyticEmotion(content));
                        });
                    };
                    
                    var emotionCallback = function(res){
                        var data = res.data;
                        for (var i in data) {
                            if (data[i].category == '') {
                                data[i].category = '默认';
                            }
                            if (emotions[data[i].category] == undefined) {
                                emotions[data[i].category] = new Array();
                                categorys.push(data[i].category);
                            }
                            emotions[data[i].category].push({
                                name: data[i].phrase,
                                icon: data[i].icon
                            });
                            uSinaEmotionsHt.put(data[i].phrase, data[i].icon);
                        }
                        changePageEmotions();
                    };
                    $.ajax({
                        url:pathImgUrl+'/images/chat/emotions/emotions.json',
                        dataType:'jsonp',
                        jsonpCallback:'emotionCallback',
                        success:emotionCallback
                    });
                })($);
            </script>
            <script>
                //获取上级对象    
                (function ($) {
                    // FirstLoad();
                    var arg = GetRequest(), ids, pros;
                    var myDate = GetDateT();//当前时间
                    //获取URL传参数据
                    if (arg != undefined) {
                        var aid = arg["id"];
                        var pro = arg["pro"];
                        if (aid != undefined) {
                            ids = aid;
                        }
                        if (pro != undefined) {
                            pros = pro;
                        }
                    }
                    //回复是否显示判断
                    var receivers = $.trim($('#receiver').html());
                    var counts = receivers.replace(/[^,]/g, '').length;
                    if (receivers == '' || counts > 0) {
                        $('#isDivShow').css("display", "none");
                        return false;
                    }
                    //点击回复展开
                    $('#txttarea').focus(function () {
                        $('#showDiv').show();
                        $('#openDIv').hide();
                        $('#clickTos').focus();
                    });
                    //发送信件
                    $('#btnReply').click(function (e) {
                        $('#Txttitle').css('border', '1px solid #CACACA');
                        if ($('#clickTos').val() == '') {
                            $('#clickTos').css('border', '1px solid red');
                            e.preventDefault();
                            return false;
                        }
                        try {
                            sendData();
                        }
                        catch (ex) {
                        }
                    });
                    //获取url中"?"符后的字串
                    function GetRequest() {
                        var url = location.search;
                        var json = {};
                        if (url.indexOf("?") != -1) {
                            var str = url.substr(1);
                            strs = str.split("&");
                            for (var i = 0; i < strs.length; i++) {
                                json[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
                            }
                        }
                        return json;
                    }
                    //取当前时间，格式为,yyyy-mm-dd hh:mm:ss
                    function GetDateT()
                    {
                        var d, s;
                        d = new Date();
                        s = d.getYear() + "-";
                        s = s + (d.getMonth() + 1) + "-";
                        s += d.getDate() + " ";
                        s += d.getHours() + ":";
                        s += d.getMinutes() + ":";
                        s += d.getSeconds();
                        return(s);
                    }
                    //发送信件方法
                    function sendData()
                    {
                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            cache: false,
                            url: "/Service2/replymessage",
                            data: {'rootId': $.trim($('#rootId').val()), 'content': $.trim($("#clickTos").val()), 'sendAccount': $.trim($('#sender').html()), 'receiveAccount': $.trim($('#receiver').html())},
                            beforeSend: function () {
                                //禁用发送								
                                $('#btnReply').val("回复中...");
                                $("#btnReply").css("color", "#CACACA").css("background-color", "#CACACA");
                 
                                        $("#btnReply").attr("disabled", "disabled");
                            },
                            success: function (json) {
                                if (json['status'] == "0") {
                                    //发送成功后，在层里无刷新注入					   
                                    window.location.reload();
                                    $("#clickTos").val('');
                                }
                                else {
                                    alert(json['data']);
                                }
                            },
                            error: function (xhr, type) {
                                alert("信息发送失败");
                            },
                            complete: function () {
                                $('#btnReply').val("回 复");
                                $('#btnReply').removeAttr("disabled");
                                $('#clickTos').css('border', '1px solid #CACACA');
                            }
                        });
                    }
                })(jQuery);
            </script>
        {/literal}
    </body>
</html>
