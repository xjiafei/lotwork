<html>
    <head>
        <meta charset="utf-8" />
        <title>活动管理后台</title>
    </head>
    <body style="text-align:center;">
        <div style="margin:0 auto;line-height: 30px">
            <form action="/event160125/index/2" method="POST">
                <table align="center">
                    <tr>
                        <td>用户查询</td>
                        <td><input type="text" name="u" /></td>
                        <td><input type="submit" value="搜寻" /></td>
                        <td><input type="button" value="下载表格" onclick="window.open('/event160125/download2?<?php echo $download; ?>')" /></td>
                    </tr>
                    <tr>
                        <td>日期</td>
                        <td colspan="3">
                            <select name="d" onchange="this.form.submit()">
                                <?php
                                foreach ($eventday as $row)
                                   echo '<option value="'.$row["eventday"].'">'.$row["eventday"].'</option>';
                                ?>
                            </select>
                        </td>
                    </tr>
                </table>
                <table style="border: solid 1px black;" align="center">
                    <tr style="background: black;color: white;"><td>用户名称</td><td>当日销量</td><td>奖金金额</td><td>X2奖金</td><td>领取奖金</td><td>日期</td></tr>
                    <?php
                        foreach ($record as $row)
                           echo '<tr><td>'.$row["username"].
                                '</td><td>'.$row["amount"].
                                '</td><td>'.$row["prize"].
                                '</td><td>'.$row["prize2"].
                                '</td><td>'.($row["claim"] == "1" ? "v" : "").
                                '</td><td>'.$row["eventday"].'</td></tr>';
                    ?>
                </table>
            </form>
        </div>

        <div style="position: absolute;z-index: 5;top: 10px;left: 10px;width: 120px;height: 100%;text-align: left;border-right: solid 1px #000000;">
            <a href="/event160125">每日销量上传</a><br/>
            用户查询<br/>
            <a href="/event160125/index/3">使用者管理</a><br/>
        </div>
    </body>
</html>