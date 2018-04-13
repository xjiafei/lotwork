<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>个人中心</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/index.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title">
            <span></span>
           
            <h1 class="logo"><p class="v{$vipLvl}"></p></h1>
            <input id = 'headImg' type='hidden' value='{$smarty.session.datas.info.headImg}'>
            <p class="balance" style = "font:22px Tahoma,Arial,'宋体',sans-serif">
                <i>{$smarty.session.datas.info.account}</i><br>
            余额 : <i>{$displayAvailBal}</i></p>
            <div id = 'uptop' class="uptop" _href='/fund'></div>
            <div id = 'withdraw' class="withdraw" _href="/withdraw"></div>
        </div>


        <div id="oplist" class="login_form login_unique">
            <ol class="inputOL">
                <li _href='{$game_server}/gameUserCenter/queryOrders' class='one'>投注记录</li>
                <li _href='{$game_server}/gameUserCenter/queryPlans?time=7' class='two'>追号记录</li>
                <li _href='/bet/fuddetail' class='three'>账户明细</li>
                {if $smarty.session.datas.info.userLvl >= 0}
                    <li id = 'managerurl' _href='/applycenter/managerurl' class='four'>链接管理</li>
                {/if}
                <li id = 'chart' _href='{$game_server}/game/chart/cqssc/Wuxing' class='five'>走势图</li>
            </ol> 
        </div>

         <div id = 'logout' class="logout" _href="/login/logout">退出登录</div>
        <div class="bottom">
            <ul>
                <li _href='#'></li>
                <li id = 'downloadApp' _href='{$path_img}/dbyl/'></li>
                <li id = 'computer' _href='{$game_server}/gameUserCenter/queryOrders?userId=1302855&time=7'></li>
            </ul>
        </div>
    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/index.js"></script>
<script type="text/javascript" src="{$path_js}/js/phoenix.ga.js"></script>
<script type="text/javascript">

    var img = $('#headImg').val();
    if(img != ""){
        $('.logo').css( "background", 'url({$path_img}/images/ucenter/safe/head/head{$smarty.session.datas.info.headImg}.png) no-repeat center center' );
        $('.logo').css( "background-size", 'contain');
    }
</script>
<!--forgetPwdUname.html end-->
</body>
</html>
