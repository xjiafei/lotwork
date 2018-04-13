<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>找回密码</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/findPwdBySecuQues.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title"><span></span>找回密码</div>
        <!--logo 部分-->
        <h1 class="logo"><!--<p class="v"></p>--></h1>

        <div class="step"></div>

        <p class="tipinput">请填写您的安全问题</p>
            <input class = 'quest0' type = 'hidden' value="{$questList[0].Id}" placeholder='{$questList[0].quest}'></input>
            <input class = 'quest1' type = 'hidden' value="{$questList[1].Id}" placeholder='{$questList[1].quest}'></input>
            <input class = 'quest2' type = 'hidden' value="{$questList[2].Id}" placeholder='{$questList[2].quest}'></input>
        <div class="login_form login_unique">
            <ol class="inputOL">
                <li>
                    <span>问题一</span>
                    <p value="{$questList[2].Id}" id="q1">{$questList[2].quest}</p>
                    <i></i> 
                </li>
                <li>
                    <span>答案</span>
                    <input type="text" name="an1" placeholder="请输入答案" id="an1"/>

                </li>
            </ol>
        </div>
        <div class="login_tips">
            <span id="tip1"></span>
        </div>
        <div class="login_form login_unique resetPwd">
            <ol class="inputOL">
                <li>
                    <span>问题二</span>
                    <p value="{$questList[1].Id}" id="q2">{$questList[1].quest}</p>
                    <i class="last"></i>
                </li>
                <li>
                    <span>答案</span>
                    <input type="text" name="an2" placeholder="请输入答案" id="an2"/>
                </li>
            </ol>
        </div>
        <div class="login_tips">
            <span id="tip2"></span>
            <span id="tip3"></span>
        </div>
           
        <!--登录按钮-->
        <button class="btn_login" id="next">下一步</button>
    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/findPwdBySecuQues.js"></script>
<!--forgetPwdUname.html end-->
</body>
</html>
