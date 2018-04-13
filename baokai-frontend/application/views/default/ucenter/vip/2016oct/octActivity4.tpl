<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>RichMan</title>
        <link href="{$path_img}/images/activity/octActivity/week4/web/css.css" rel="stylesheet">
    </head>
    <body id="rich">
        <div class="model">
            <div class="ModelBody">
                <a class="btn_later" href="javascript:"></a>
                <a id='add_addr' class="btn_add" href="http://ask.25ik.com:8080/q.php?qname=diaocha_47&qlang=cn" target="_blank"></a>
            </div>
        </div>
        <div class="SideNav">
            <div class="SideNavBody">
                <div class="GOcom2 nav-btn" id="Go_com2">一</div>
                <div class="GOcom3 nav-btn" id="Go_com3">二</div>
                <div class="GOcom4 nav-btn" id="Go_com4">說明</div>
                <div class="GOtop nav-btn" id="Go_rich" style="margin-bottom:0px;">TOP</div>   
            </div>
        </div>      
        <div class="main">
            <div class="com1" id="com1">
                <div class="signup">
                    <a href="javascript:;" id='singbtn' class="singupBtn"></a>
                </div>
            </div>
            <div class="com2" id="com2">
                <div class="TodayDeposit" id="TodayDeposit">0</div>
                <div class="TodayBets" id="TodayBets">0</div>
                <div class="TodayPrize" id="TodayPrize">0</div>
                <div class="btn_deposit"><a id='chargebtn' class="goDP" target="_blank" href="/fund"><img src="{$path_img}/images/activity/octActivity/week4/web/btn-deposit.png" alt=""></a></div>
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
                <div class="btn_ChangeAddress"><a id='modify_addr' class="goCA" target="_blank" href="http://ask.25ik.com:8080/q.php?qname=diaocha_47&qlang=cn"><img src="{$path_img}/images/activity/octActivity/week4/web/btn-ca.png" alt=""></a></div>
            </div>
            <div class="com4" id="com4" style="margin-left:50px;"></div>
        </div>
<script type="text/javascript" src="{$path_js}/js/activity/octActivity/week4/web/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/activity/octActivity/week4/web/rich.js"></script>
<script type="text/javascript" src="{$path_js}/js/phoenix.GameGa.js"></script>
    </body>
</html>