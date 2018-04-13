<html>
    <head>
        <meta charset="utf-8" />
        <title>活动管理后台</title>
    </head>
    <body style="text-align:center;margin-top: 10%">
        <form action="/event160125/verify" method="POST">
            <div style="margin:0 auto;line-height: 30px">
                活动管理后台<br/>
                管理帐号<input type="text" name="user" /><br/>
                管理密码<input type="password" name="pass" /><br/>
                <div style="color:red"><?php echo $msg; ?></div>
                <input type="submit" value="提交" />
            </div>
        </form>
    </body>
</html>