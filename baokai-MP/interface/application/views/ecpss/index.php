<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>充值</title>
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery-1.9.1.min.js"></script>
</head>
<body>
    <form id="ecpss" method="post" action="https://gwapi.yemadai.com/pay/sslpayment">
        <input type="hidden" name="BillNo" value="<?php echo $BillNo;?>"/>
        <input type="hidden" name="MerNo" value="<?php echo $MerNo;?>"/>
        <input type="hidden" name="Amount" value="<?php echo $Amount;?>"/>
        <input type="hidden" name="OrderTime" value="<?php echo $OrderTime;?>"/>
        <input type="hidden" name="ReturnURL" value="<?php echo $ReturnURL;?>"/>
        <input type="hidden" name="AdviceURL" value="<?php echo $AdviceURL;?>"/>
        <input type="hidden" name="SignInfo" value="<?php echo $SignInfo;?>"/>
        <input type="hidden" name="defaultBankNumber" value="<?php echo $defaultBankNumber;?>"/>
        <input type="hidden" name="payType" value="<?php echo $payType;?>"/>
     </form>
<script>
    $(document).ready(function($){
            //thpay.logSubmit();
            document.getElementById("ecpss").submit();
            }
    );
</script>
</body>
</html>
