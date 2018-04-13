<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>宝开VIP</title>
        <link href="{$path_img}/images/common/base.css" rel="stylesheet">
        <link rel="shortcut icon" href="{$path_img}/images/common/favicon.ico">
        <link rel="stylesheet" type="text/css" href="{$path_img}/images/common/base.css" >
        <link rel="stylesheet" type="text/css" href="{$path_img}/css/vipv2/css/vip.css?20160602">
    </head>
    <body>
        <script type="text/javascript" src="{$path_js}/js/base-all.js"></script>
        {include file='/default/layout/header_front_banner.tpl'}
        <script>
            $('#header').hide();
        </script>
        <div class="banner">
            <h1 class="logo-vip"></h1>
            <div class="user-box">
                <div class="user-name">欢迎你,<strong>{$smarty.session.datas.info.account}</strong></div>
                {if $vipInfo neq null}
                    <a class="user-head user-head-vip{$vipInfo.vipLv}" id="userHead"></a>
                        <div class="vip-info">
                        {if $vipInfo.isMax}
                            <div class="vip-info-title clearfix">
                                <span class="level-current level-vip{$vipInfo.vipLv}"></span>
                            </div>
                            <div class="vip-info-progress">
                                <div class="progress-wapper"></div>
                            </div>
                            <a href="javascript:void(0);"class="vip-info-text animation flash">您目前已经是最高等级</a>
                        {else if $vipInfo.isLoadData}
                            <div class="vip-info-title clearfix">
                                <span class="level-current level-vip{$vipInfo.vipLv}"></span>
                                <span class="level-next level-vip{$vipInfo.vipLv+1}"></span>
                            </div>
                            <div class="vip-info-progress">
                                <div class="progress-wapper" title="{$vipInfo.userExp}/{$vipInfo.totExp}">
                                    <div class="progress-inner expprogress" style="width:0%" title="{$vipInfo.userExp}/{$vipInfo.totExp}"></div>
                                </div>
                            </div>
                            <a href="javascript:void(0);" id="vipExpHistBtn" class="vip-info-text animation flash">加油！升级只差<span>{$vipInfo.totExp-$vipInfo.userExp}</span>经验值了！</a>
                            <table id="vipExpHists" class="vip-detail">
                                <thead>
                                    <tr>
                                        <th>成长经验</th>
                                        <th>日期</th>
                                    </tr>
                                </thead>
                                <tbody>
                                {foreach from=$vipInfo.expHistorys item=data}
                                    <tr>
                                        <td>{$data.exp}</td><td>{$data.date}</td>
                                    </tr>
                                {/foreach}
                                </tbody>
                            </table>
                        {else}
                            <div class="vip-info-progress">
                                <div class="progress-wapper"></div>
                            </div>
                            <a href="javascript:void(0);"class="vip-info-text animation flash">经验值资料查询异常，请重新整理</a>
                        {/if}
                    </div>
                    <div class="vip-ico clearfix">
                        {foreach from=$vipInfo.privileges item=data}
                            <a {if $data.isActive}href="javascript:void(0);"{/if} 
                               class="vip-ico{$data.id} {if $data.isActive}vip-current-ico{$data.id}{/if}" 
                               {if $data.title neq null}title="{$data.title}"{/if}></a>
                        {/foreach}
                    </div>
                {/if}
            </div>
            <div id="focus" 
                 data-cycle-slides="> .item"
                 data-cycle-pager="> .cycle-pager-wrap .cycle-pager"
                 data-cycle-prev="> .prev"
                 data-cycle-next="> .next"
                 data-cycle-fx="fade"
                 data-cycle-timeout="4000"
                 data-cycle-loader="wait"
                 data-cycle-speed="800"
                 data-pause-on-hover="true"
                 >
                <div class="cycle-pager-wrap">
                    <div class="cycle-pager"></div>
                </div>
                <span class="prev_next prev">&#9664;</span>
                <span class="prev_next next">&#9654;</span>
            </div>
        </div>
        <div class="content-body">
            <div class="g_34">
                <div class="clearfix">
                    <div class="privilege-list">
                        {if $privileges neq null and count($privileges)>0}
                            {foreach from=$privileges item=data}
                                {if $data@index < 3}
                                <a {if count($data.link)>0}href="{$data.link}"{/if} {if $data.urlTarget}target="_blank"{/if}>
                                    <img src="{$dynamicroot}/{$data.src}" alt="{$data.title}">
                                </a>
                                {/if}
                            {/foreach}
                        {/if}
                    </div>
                    <div class="news-list">
                        <h3 class="news-list-hd">VIP资讯<a href="/ad/noticeList?noticeLevel=4" target="_blank" class="more">更多</a></h3>
                        <ul class="news-list-bd active">
                            {foreach from=$notices item=data}
                                <li>
                                    <a href="/ad/adNoticeQuery?id={$data.id}" target="_blank"><span>{$data.gmtEffectBegin}</span>{$data.title}</a>
                                </li>
                            {/foreach}
                        </ul>
                    </div>
                </div>
                <div class="activity-list">
                    <h3 class="activity-list-hd">您的专属活动</h3>
                    <div class="activity-list-bd">
                        {if count($activitys)>0}
                            {foreach from=$activitys item=data}
                                {if $data@index < 4}
                                <a {if count($data.link)>0}href="{$data.link}"{/if} {if $data.urlTarget}target="_blank"{/if}>
                                    <img src="{$dynamicroot}/{$data.src}" alt="{$data.title}">
                                </a>
                                {/if}
                            {/foreach}
                        {/if}
                    </div>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-info"></div>
            <p class="footer-copyright">©2003-2016 宝开彩票 All Rights Reserved</p>
        </div>
        <div class="service">
           <span class="custom custom-top"></span>
           <a href="javascript:hj5107.openChat();" class="custom custom-service" id="custom-service"></a>
        <!-- {if  $qqs[0]['qq'] neq ''} -->
            <!-- {foreach from=$qqs item=data} -->
                <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin={$data.qq}&site=qq&menu=yes" class="custom custom-qq"></a>
            <!-- {/foreach}  -->
        <!-- {/if} -->
        </div>
        <script src="{$path_js}/js/jquery.cycle2.min.js"></script>
        <script type="text/javascript">
            (function () {
                function async_load() {
                    var s = document.createElement('script');
                    s.type = 'text/javascript';
                    s.async = true;
                    s.src = "http://www.26hn.com/web/code/code.jsp?c=1&s=20";
                    var x = document.getElementsByTagName('script')[0];
                    x.parentNode.insertBefore(s, x);
                }
                if (window.attachEvent){
                    window.attachEvent('onload', async_load);
                }else{
                    window.addEventListener('load', async_load, false);
                }
            })();
        </script>
        <script>
            //焦点图绑定更新事件
            $(document).on('cycle-update-view', '#focus', function (event, opts) {
                var $dom = $(this);

                if (Number(opts['slideCount']) <= 1) {
                    $dom.find('.prev,.next,.cycle-pager-wrap').hide();
                } else {
                    $dom.find('.prev,.next,.cycle-pager-wrap').show();
                }
            });
            var pressSlideshow = $('#focus').cycle();
            {if count($banners)>0}
                {foreach from=$banners item=data}
                $('#focus').cycle('add', '<div class="item"><a {if count($data.link)>0}href="{$data.link}"{/if} {if $data.urlTarget}target="_blank"{/if}><img src="{$dynamicroot}/{$data.src}" alt="{$data.title}"></a></div>');
                {/foreach}
            {/if}

            //VIP等级进度条动画
            var prostyle = '{100*$vipInfo.userExp/$vipInfo.totExp}%';
            setTimeout(function () {
                $('.expprogress').css('width', prostyle);
            }, 500);

            $('#vipExpHistBtn').click(function () {
                $('#vipExpHists').fadeIn().delay(5000).fadeOut();
            });
        </script>
    </body>
</html>