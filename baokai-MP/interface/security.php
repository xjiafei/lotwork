<?php
/**
 * 文件 : /_app/controller/security.php
 * 功能 : 安全中心
 *
 * 功能:
 * + actioncheckPass    检查资金密码
 * + actionTran         频道转账
 * 
 * @author    Tom
 * @version   1.2.0
 * @package   lowproxy
 */

class controller_security extends basecontroller 
{
    /**
     * 资金密码检查
     * URL:./index.php?controller=secutity&action=checkPass
     * @author Tom
     */
    function actionCheckPass( $sController='security', $sAction='checkpass' )
    {
        if( isset($_SESSION['setsecurity']) && $_SESSION['setsecurity']=='yes' )
        {
            $aLocation  = array(
                0=>array( "title" => '返回上一页', "url" => 'javascript:history.back()' ), 
                1=>array( "title" => "跳转至: 银行大厅并在 '安全中心' 设置资金密码", "url" => '../', "target"=>'top'  ),
            );
            sysMsg( "您还未设置 '资金密码', 无法使用转账功能 ", 2, $aLocation );
        }
        // 生成随即码
        $_SESSION['checkcode'] = rand( 1, 1000 );
        if( empty($_POST['flag']) || $_POST['flag'] != 'check' )
        {
            $GLOBALS['oView']->assign( "ur_here",           "资金密码检查" );
            $GLOBALS['oView']->assign( "nextcontroller",    $sController );
            $GLOBALS['oView']->assign( "nextaction",        $sAction );
            $GLOBALS['oView']->display( "security_checkpass.html" );
            EXIT;
        }
        else
        {
            if( empty($_POST['nextcon']) || empty($_POST['nextact']) )
            {
                sysMsg( "非法操作", 2 );
            }
            if( empty($_POST['secpass']) )
            {
                sysMsg( "请输入资金密码", 2 );
            }
            /*if( FALSE === model_user::checkUserPass($_POST['secpass']) )
            { // 尽量严格的检查用户提交的密码, 避免 API 被频繁调用
                sysMsg( "资金密码不符合规则", 2 );
            }*/
            $oConfig     = new model_config();
            $iApiRunTime = $oConfig->getConfigs("apiruntime");
            $iApiRunTime = intval($iApiRunTime) > 0 ? $iApiRunTime : 30;
            $aTranfer   = array( 'iUserId'=> $_SESSION['userid'], 'sFundPwd'=> $_POST['secpass'] );
            $oChannelApi = new channelapi( 0, 'checkSecurityPass', TRUE );
            $oChannelApi->setTimeOut($iApiRunTime);            // 整个转账过程的超时时间, 可能需要微调
            $oChannelApi->sendRequest( $aTranfer );  // 发送转账请求给调度器
            $mAnswers = $oChannelApi->getDatas();    // 获取转账 API 返回的结果
            if( empty($mAnswers) || !is_array($mAnswers) || $mAnswers['status'] == 'error' )
            {
                sysMsg( "资金密码效验失败", 2 );
            }
            else
            {
                redirect( url($_POST['nextcon'], $_POST['nextact'], array('check'=>$_SESSION['checkcode'])) );
            }
        }
    }



    /**
     * 频道转帐
     * URL:./index.php?controller=security&action=tran
     * @author Tom
     */
    function actionTran()
    {
        /**
         * 1, 根据 URL $_GET['currentChannelId'] 获取当前频道ID, 默认为 0 (银行)
         * Post => 
         *     [toChannelId] => 0
         *     [fmoney] => 751294
         *     [submit] => 提交
         *     SYS_CHANNELID => 当前频道ID
         */
        $oConfig     = new model_config();
        $iApiRunTime = $oConfig->getConfigs("apiruntime");
        $iApiRunTime = intval($iApiRunTime) > 0 ? $iApiRunTime : 30;
        if( !empty($_POST['flag']) && $_POST['flag'] == 'doTranfer' )
        { // 处理转账过程
            // 1.1 简单判断
            if( !isset($_POST['toChannelId']) || !isset($_POST['fmoney'])
                || !is_numeric($_POST['toChannelId']) || !is_numeric($_POST['fmoney'])||
                $_POST['fmoney'] <= 0 )
            {
                sysMsg( "转账相关数据不符合规范", 2 );
            }

            // 2.1 初始化数据, 并启用 "转账调度器"  model_transferdispatcher()
            $aTranfer['iUserId']         = intval( $_SESSION['userid'] );
            $aTranfer['iFromChannelId']  = intval( SYS_CHANNELID ); // 银行平台默认 0
            $aTranfer['iToChannelId']    = intval( $_POST['toChannelId'] );
            $aTranfer['fMoney']          = floatval( $_POST['fmoney'] );
            $aTranfer['sMethod']         = 'USER_TRAN'; // 用户转账
            
            // 2.2 进行资金密码检查
            /* @var $oUserFund model_userfund */
            $oUserFund = A::singleton("model_userfund");
			if( intval($_SESSION["usertype"])==2 )
			{//管理员
				$oUser = A::singleton('model_user');
				$aTranfer['iUserId'] = $oUser->getTopProxyId(  $aTranfer['iUserId'], FALSE ); //获取总代
			}
            $sFields   = " ut.`userid`,ut.`username`,uf.`availablebalance` ";
            $aUserinfo = $oUserFund->getFundByUser( $aTranfer['iUserId'], $sFields, SYS_CHANNELID );
            if( empty($aUserinfo) )
            {
                sysMsg( "您的资金帐户因为其他操作被锁定，请稍后重试", 2 );
            }
            if( $aUserinfo['availablebalance']<=0 || $_POST['fmoney']>$aUserinfo['availablebalance'] )
            {
                sysMsg( "您的帐户资金不足，无法完成转账", 2 );
            }

            // 2.3 调用 PASSPORT平台 API.转账调度器
            $oChannelApi = new channelapi( 0, 'channelTransitionDispatcher', TRUE );
            $oChannelApi->setTimeOut($iApiRunTime);            // 整个转账过程的超时时间, 可能需要微调
            $oChannelApi->sendRequest( $aTranfer );  // 发送转账请求给调度器
            $mAnswers = $oChannelApi->getDatas();    // 获取转账 API 返回的结果
            if( empty($mAnswers) || !is_array($mAnswers) || $mAnswers['status'] == 'error' )
            {
                // TODO: 需要进行日志记录, 成功执行第一个API事务, 执行第二个失败
                $sErrorMsg = isset($mAnswers['data']) ? $mAnswers['data'] : '';
                sysMsg( "抱歉, 转账失败.请留意您的账变信息\\n Develop Debug [$sErrorMsg]", 2 );
            }
            else 
            {
                $aLocation  = array( 0=>array( "title" => "继续: 频道转账", "url" => url( 'security', 'tran' ) ));
                sysMsg( "您的转账操作已完成", 1, $aLocation );
            }
        }

        /**
         * 平台转账(确认页)
         */
        elseif( !empty($_POST['flag']) && $_POST['flag'] == 'showTranfer' )
        {
        	if( !isset($_POST['toChannelId']) || !isset($_POST['fmoney'])
                || !is_numeric($_POST['toChannelId']) || !is_numeric($_POST['fmoney']) )
            {
                sysMsg( "转账相关数据不符合规范", 2 );
            }
            // STEP 01: 进行资金密码检查
            /* @var $oUserFund model_userfund */
            $oUserFund = A::singleton("model_userfund");
			$iUserId = intval($_SESSION['userid']);

			if( intval($_SESSION["usertype"])==2 )
			{//管理员
				$oUser = A::singleton('model_user');
				$iUserId = $oUser->getTopProxyId( $iUserId, FALSE ); //获取总代
			}
            $sFields   = " ut.`userid`,ut.`username`,uf.`availablebalance` ";
            $aUserinfo = $oUserFund->getFundByUser( $iUserId, $sFields, SYS_CHANNELID );
            if( empty($aUserinfo) )
            {
                sysMsg( "您的资金帐户因为其他操作被锁定，请稍后重试", 2 );
            }

        	if( $aUserinfo['availablebalance']<=0 || $_POST['fmoney']>$aUserinfo['availablebalance'] )
            {
            	sysMsg( "您的帐户资金不足，无法完成转账", 2 );
            }
            $aChannelData[0] = '银行大厅';
            $GLOBALS['oView']->assign( "ur_here",       '频道转账确认' );
            $GLOBALS['oView']->assign( "aChannelData",  $aChannelData );
            $GLOBALS['oView']->assign( "user",          $aUserinfo );
            $GLOBALS['oView']->assign( "s",             $_POST );
            $GLOBALS['oView']->display("security_transfer2.html");
            EXIT;
        }

        /**
         * 平台转账 (显示页) 
         */
        if( empty($_POST) )
        {
            // STEP 01: 进行资金密码检查
            /* @var $oUserFund model_userfund */
            $oUserFund = A::singleton("model_userfund");
            $sFields   = " ut.`userid`,ut.`username`,uf.`availablebalance` ";
			$iUserId = intval($_SESSION['userid']);

			if( intval($_SESSION["usertype"])==2 )
			{//管理员
				$oUser = A::singleton('model_user');
				$iUserId = $oUser->getTopProxyId( $iUserId, FALSE ); //获取总代
			}
            $aUserinfo = $oUserFund->getFundByUser( $iUserId, $sFields, SYS_CHANNELID );
            if( empty($aUserinfo) )
            {
                sysMsg( "您的资金帐户因为其他操作被锁定，请稍后重试", 2 );
            }
        	if( $aUserinfo['availablebalance'] <= 0 )
            {
            	$aUserinfo['availablebalance'] = 0;
            }
            // 对4位精度的金额进行4位精度取整, 不进行四舍五入
            $aUserinfo['availablebalance'] = floor($aUserinfo['availablebalance']*10000) / 10000;

            $aChannelData[0] = '银行大厅';
            
            // 获取用户银行大厅可用余额
            $aTranfer = array();
            $maxPickUpMoney = 0.00;
            $aTranfer['iUserId'] = $iUserId;
            $oChannelApi = new channelapi( 0, 'getUserCash', TRUE );
    	    $oChannelApi->setTimeOut($iApiRunTime);            // 整个转账过程的超时时间, 可能需要微调
            $oChannelApi->sendRequest( $aTranfer );  // 发送转账请求给调度器
    	    $mAnswers = $oChannelApi->getDatas();    // 获取转账 API 返回的结果
    	    if( empty($mAnswers) || !is_array($mAnswers) || $mAnswers['status'] == 'error' )
    	    {
    	        $maxPickUpMoney = 0.00;
    	    }
    	    else 
    	    {
    	        $maxPickUpMoney = $mAnswers['data'];
    	    }
            
            $GLOBALS['oView']->assign( "ur_here",       '资金在不同频道间的分配' );
            $GLOBALS['oView']->assign( "aChannelData",  $aChannelData );
            $GLOBALS['oView']->assign( "user",          $aUserinfo );
            $GLOBALS['oView']->assign( "maxpickup", $maxPickUpMoney );
            $GLOBALS['oView']->display( "security_transfer.html" );
            EXIT;
        }
        EXIT; // 未捕获的操作
    }
    
    
    
    /**
   	 * 高频平台直接向银行大厅提款，转入到高频平台
   	 *
   	 * @version		 v1.0	2010-08-20
   	 * @author 		 louis
   	 * 
   	 */
    public function actionPickUp(){
        $oConfig     = new model_config();
        $iApiRunTime = $oConfig->getConfigs("apiruntime");
        $iApiRunTime = intval($iApiRunTime) > 0 ? $iApiRunTime : 30;
    	if( !empty($_POST['flag']) && $_POST['flag'] == 'doTranfer' ){ // 处理转账过程
    		if( !isset($_POST['toChannelId']) || !isset($_POST['fmoney'])|| !is_numeric($_POST['toChannelId'])
    			 || !is_numeric($_POST['fmoney']) || $_POST['fmoney'] <= 0 )
            {
                sysMsg( "转账相关数据不符合规范", 2 );
            }
            $iUserId = intval($_SESSION['userid']);
            if( intval($_SESSION["usertype"])==2 )
			{//管理员
				$oUser = A::singleton('model_user');
				$iUserId = $oUser->getTopProxyId( $iUserId, FALSE ); //获取总代
			}
            // 2.1 初始化数据, 并启用 "转账调度器"  model_transferdispatcher()
	        $aTranfer['iUserId']         = $iUserId;
	        $aTranfer['iFromChannelId']  = intval( $_POST['toChannelId'] ); 
	        $aTranfer['iToChannelId']    = intval( SYS_CHANNELID );
	        $aTranfer['fMoney']          = floatval( $_POST['fmoney'] );
	        $aTranfer['sMethod']         = 'USER_TRAN'; // 用户转账
            
            // 检查目标平台是否有足够的资金
	        $aUserInfo = array();
	        $aUserInfo['iUserId'] = $iUserId;
	        $aUserInfo['iCheckLock'] = 1;
	        $oChannelApi = new channelapi( 0, 'getUserCash', TRUE );
		    $oChannelApi->setTimeOut($iApiRunTime);            // 整个转账过程的超时时间, 可能需要微调
	        $oChannelApi->sendRequest( $aUserInfo );  // 发送转账请求给调度器
		    $mAnswers = $oChannelApi->getDatas();    // 获取转账 API 返回的结果
		    if( empty($mAnswers) || !is_array($mAnswers) || $mAnswers['status'] == 'error' )
		    {
		        sysMsg( "资金转出平台资金帐户可能因为其他操作被锁定，请稍后重试", 2 );
		    }
		    
		    $fMoney = number_format($_POST['fmoney'], 2, '.', '');
		    if ($fMoney <= 0 || $fMoney > $mAnswers['data']){
		    	sysMsg( "您的资金转出平台帐户资金不足，无法完成转账", 2 );
		    }
            
            
             // 2.3 调用 PASSPORT平台 API.转账调度器
            $oChannelApi = new channelapi( 0, 'channelTransitionDispatcher', TRUE );
            $oChannelApi->setTimeOut($iApiRunTime);            // 整个转账过程的超时时间, 可能需要微调
            $oChannelApi->sendRequest( $aTranfer );  // 发送转账请求给调度器
            $mAnswers    = $oChannelApi->getDatas();    // 获取转账 API 返回的结果
            if( empty($mAnswers) || !is_array($mAnswers) || $mAnswers['status'] == 'error' )
            {
                // 需要进行日志记录, 成功执行第一个API事务, 执行第二个失败
                $sErrorMsg = isset($mAnswers['data']) ? $mAnswers['data'] : '';
                sysMsg( "抱歉, 转账失败.请留意您的账变信息\\n Develop Debug [$sErrorMsg]", 2 );
            }
            else 
            {
                $aLocation  = array( 0=>array( "title" => "继续: 频道转账", "url" => url( 'security', 'tran' ) ));
                sysMsg( "您的转账操作已完成", 1, $aLocation );
            }
	    }
	    
	    // 转账确认页
    	if( !isset($_POST['toChannelId']) || !isset($_POST['fmoney']) || !is_numeric($_POST['toChannelId'])
    		 || !is_numeric($_POST['fmoney']) || intval($_POST['kind']) !== 1 )
    		 {
            	sysMsg( "转账相关数据不符合规范", 2 );
    		 }
    	$iUserId = intval($_SESSION['userid']);
    	if( intval($_SESSION["usertype"])==2 )
		{//管理员
			$oUser = A::singleton('model_user');
			$iUserId = $oUser->getTopProxyId( $iUserId, FALSE ); //获取总代
		}
    	// 检查目标平台是否有足够的资金
        $aTranfer = array();
        $aTranfer['iUserId'] = $iUserId;
        $aTranfer['iCheckLock'] = 1;
        $oChannelApi = new channelapi( 0, 'getUserCash', TRUE );
	    $oChannelApi->setTimeOut($iApiRunTime);            // 整个转账过程的超时时间, 可能需要微调
        $oChannelApi->sendRequest( $aTranfer );  // 发送转账请求给调度器
	    $mAnswers = $oChannelApi->getDatas();    // 获取转账 API 返回的结果
	    if( empty($mAnswers) || !is_array($mAnswers) || $mAnswers['status'] == 'error' )
	    {
	        sysMsg( "资金转出平台资金帐户可能因为其他操作被锁定，请稍后重试", 2 );
	    }
	    
	    $fMoney = number_format($_POST['fmoney'], 2, '.', '');
	    if ($fMoney <= 0 || $fMoney > $mAnswers['data']){
	    	sysMsg( "您的资金转出平台帐户资金不足，无法完成转账", 2 );
	    }
	    
	    $oChannel = new model_channels();
		$aChannel = $oChannel->channelGetAll( ' `id`, `channel` ', ' `isdisabled`=0 AND `pid`=0 ' );
		$aChannelData = array();
        if( SYS_CHANNELID == 0 )
        { // 如果为银行平台, 则显示其他所有开放的频道
            foreach( $aChannel AS $v )
    		{
    		    $aChannelData[ $v['id'] ] = $v['channel'];
    		}
        }
        else
        {
            $aChannelData[0] = '银行大厅';
        }
        
        if( empty($aChannelData) ) 
        {
            sysMsg( "转账平台未开启, 请稍后再试", 1 );
        }
        
	    $GLOBALS['oView']->assign( "ur_here",  '频道转账确认' );
	    $GLOBALS['oView']->assign( "aChannelData",  $aChannelData );
	    $GLOBALS['oView']->assign( "sign",     'in' );
	    $GLOBALS['oView']->assign( "s",        $_POST );
	    $GLOBALS['oView']->display("security_transfer2.html");
		EXIT;
    }
}
?>