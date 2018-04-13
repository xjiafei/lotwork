<!DOCTYPE html>
<html lang="en" id="html">
    <head>
        <meta charset="UTF-8">
        <title>RichMan</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <meta content="telephone=no" name="format-detection"/>
        <link href="{$path_img}/images/activity/octActivity/week4/mb/css.css" rel="stylesheet">
    </head>
    <body>
        <div class="model">
            <div class="ModelBody">
                <a class="btn_later" href="javascript:"></a>
                <a class="btn_add" href="http://ask.25ik.com:8080/q.php?qname=diaocha_47&qlang=cn" target="_blank"></a>
            </div>
        </div>
        <div class="main">
            <div class="banner">
                <div class="signup">
                    <a href="javascript:;" class="singupBtn"></a>
                </div>
            </div>
            <div class="com2">
                <div class="TodayDeposit" id="TodayDeposit">0</div>
                <div class="TodayBets" id="TodayBets">0</div>
                <div class="TodayPrize" id="TodayPrize">0</div>
            </div>
            <div class="com3" id="com3">
                <div class="sign1">
                    <div class="sign1num">0</div>
                </div>
                <div class="gift1"></div>
                <div class="sign2">
                    <div class="sign2num">0</div>
                </div>
                <div class="gift2"></div>
                <div class="sign3">
                    <div class="sign3num">0</div>
                </div>
                <div class="gift3"></div>
				<div class="sign4"></div>
                <div class="btn_ChangeAddress"><a class="goCA" target="_blank" href="http://ask.25ik.com:8080/q.php?qname=diaocha_47&qlang=cn"><img src="{$path_img}/images/activity/octActivity/week4/mb/btn-ca.png" alt=""></a></div>
            </div>
            <div class="com4" id="com4"></div>
            <div class="copyrigit"></div>
        </div>
    <script>
        var data = 'token='+encodeURIComponent ('{$token}');
        var fitPage = function(){
            var w = document.body.clientWidth;
            w = w > 720 ? 720: w;
            w = (w / 720) * 100 ;
            document.getElementById('html').style.fontSize = w + 'px';
        }
        fitPage();
        var t;
        var func = function(){
            clearTimeout(t);
            t = setTimeout(fitPage, 25);
        }
        window.onresize = func();

    </script>
    <script type="text/javascript" src="{$path_img}/js/activity/octActivity/week4/mb/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="{$path_img}/js/activity/octActivity/week4/mb/rich.js"></script>
 </body>
</html>