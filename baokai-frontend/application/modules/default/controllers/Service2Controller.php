<?php
class Default_Service2Controller extends CustomControllerAction {

    protected $_message;
    protected $_service;
    protected $_noticeRedis;

    public function init() {
        parent::init();
        //parent::viashlUrl();
        $this->_message = new Message2();
        $this->_service = new Service();
        $this->_noticeRedis = new Rediska_Key_Hash(md5('ANVO_UserNoticeTask' . $this->_sessionlogin->info['id']));
        $this->_noticeRedis->expire(intval($this->_sessionlogin->info['loginCtrl']['overTime'] * 60)); //session有效期内有效
    }

    /* 显示收件箱 */
    public function queryunreadmessageAction() {

        $dataUnread = array(
            "body" => array(
                "pager" => array(
                    'startNo' => 0,
                    "endNo" => 4
                )
            )
        );
        // 取未读数量
        $unReadValue = $this->_message->messages($dataUnread);

        if ($unReadValue ['body'] ['result']['unreadCnt'] == NULL) {
            $unReadV = 0;
        } else {
            $unReadV = $unReadValue ['body'] ['result']['unreadCnt'];
        }
        $data = array(
            "body" => array(
                "pager" => array(
                    'startNo' => 1,
                    "endNo" => 5
                )
            )
        );
        // 取未读5条站内信
        $encodedValue = $this->_message->queryUnReadMessageList($data);
        header('Content-Type: application/json;charset=utf-8');
        $encodedValue ['body'] ['result']['unreadCnt'] = $unReadV;
        echo json_encode($encodedValue ['body'] ['result']);
    }

    public function inboxAction() {
        $jdata = null;
        /**
         * ******************************************
         */
        //页数大小
        $pageSize = 10;
        //当前页吗
        $page = (0 == intval($this->_request->getParam("page"))) ? 1 : intval($this->_request->getParam("page"));

        $start = ($page - 1) * $pageSize;
        $data = array(
            "body" => array(
                "pager" => array(
                    'startNo' => $start,
                    "endNo" => $start + $pageSize - 1
                )
            )
        );

        $encodedValue = $this->_message->messages($data);
        $this->view->unreadCnt = $encodedValue ['body'] ['result'] ['unreadCnt'];
        $this->view->receives = $encodedValue ['body'] ['result'] ['receives'];
        $pages = CommonPages::getPages($encodedValue ['body']['pager']['total'], $page, $pageSize);
        $this->view->assign('pages', $pages);
        $this->view->assign('title', '站内信');
        $this->view->display('default/ucenter/service2/inbox.tpl');
    }

    /* 4.8.1.11	回复站内信聚合请求 */
    public function replymessageAction() {
        $rootId = intval(getSecurityInput($this->_request->getPost("rootId")));
        $parentId = intval(getSecurityInput($this->_request->getPost("parentId")));
        $content = getSecurityInput($this->_request->getPost("content"));
        $sendAccount = $this->_sessionlogin->info['account'];

        if (getStrLen($content) > 300) {
            echo Zend_Json::encode(array('status' => '1', 'data' => '站内信字符长度超限制'));
            exit;
        }

        $data = array(
            "body" => array(
                "param" => array(
                    "rootId" => $rootId, //1251, 
                    "parentId" => $parentId, //1089,
                    "content" => trim($content),
                    "sendAccount" => trim(strtolower($sendAccount)), //"R00002"
                ),
                "pager" => array(
                    'startNo' => 1,
                    "endNo" => 3
                )
            )
        );

        $arrtmp = $this->_message->replyMessage($data);
        if (isset($arrtmp['head']['status'])) {
            if ($arrtmp['head']['status'] == '0') {
                echo Zend_Json::encode(array('status' => '0', 'data' => '回复成功'));
                exit;
            } else {
                echo Zend_Json::encode(array('status' => '1', 'data' => '回复失败,请重试!'));
                exit;
            }
        } else {
            echo Zend_Json::encode(array('status' => '1', 'data' => '网络错误,请重试!'));
            exit;
        }
    }

    /*     * **********站内信详情页面{系统和用户站内信}********** */
    public function sysmessagesAction() {
        $msgTpye = 'uMsg';
        $id = getSecurityInput($this->_request->get("id"));
        $unread = intval(getSecurityInput($this->_request->get("unread")));
        $pro = getSecurityInput($this->_request->get("pro"));
        $data = array(
            "body" => array(
                "param" => array(
                    "rootId" => $id
                )
            )
        );
        $resMsg = $this->_message->queryMessageDetail($data);
        $result = array();
        $detailFrontUrl = getGameDomain(getWebSiteDomain());
        if (isset($resMsg ['body'] ['result'] ['receives'])) {
            foreach ($resMsg ['body'] ['result'] ['receives'] as $key => $value) {
                $result[$key]['sendAccount'] = $value['sendAccount'];
                $result[$key]['sender'] = $value['sender'];
                $result[$key]['content'] = str_replace('{$detailFrontUrl}', $detailFrontUrl, $value['content']);
                $result[$key]['sendMsgRout'] = $value['sendMsgRout'];
                $result[$key]['id'] = $value['id'];
                $result[$key]['title'] = $value['title'];
                $result[$key]['sendTime'] = date('Y-m-d H:i:s', getSrtTimeByLong($value['sendTime']));
            }
        }
        //非本人站内信 不准读取
        if ($result[0]['id'] != $id) {
            $this->_redirect('/Service2/inbox/');
        }
        $parentid = $this->_sessionlogin->info ['parentId'];
        $res = $this->_message->getAccountINfoById($parentid);
        $this->view->parentaccount = $res ['body'] ['result'] ['userStruc'] ['account'];
        $this->view->unread = $unread;
        $this->view->result = $result;
        $this->view->messageInfo = $result[count($result)-1];
        $this->view->pro = $pro; 
        $this->view->display('default/ucenter/service2/userinbox.tpl');
    }

    /* 给下级发短信 */
    public function servicesubAction() {

        $this->view->unread = $this->_request->get("unread");
        $this->view->assign('title', '给下级发信');
        $this->view->display('default/ucenter/service2/sub.tpl');
    }

    /* 给上级发短信 */
    public function servicesupAction() {

        $this->view->unread = $this->_request->get("unread");
        $this->view->assign('title', '给上级发信');
        $this->view->display('default/ucenter/service2/sup.tpl');
    }

    /* 4.8.1.3	标记为已读请求  flag=1 收件人删除  flag=1 发件人删除 */
    public function markreadAction() {

        $msgIds = array();
        $ids = array();
        $pros = array();
        $id = getSecurityInput($this->_request->getPost("id"));
        $pro = getSecurityInput($this->_request->getPost("pro"));
        if ($id != null) {
            $ids = explode(',', $id);
            $pros = explode(',', $pro);
        }

        for ($index = 0; $index < count($ids); $index++) {
            if ($pros[$index] == $this->_sessionlogin->info['account']) {
                $msgIds[$index] = array("id" => (int) $ids[$index], "flag" => 0);
            } else {
                $msgIds[$index] = array("id" => (int) $ids[$index], "flag" => 1);
            }
        }
        $data = array(
            "body" => array(
                "param" => array(
                    'msgIds' => $msgIds,
                ),
                "pager" => array(
                    'startNo' => 13,
                    "endNo" => 3
                )
            )
        );
        //echo json_encode($data);exit;
        $arrtmp = $this->_message->markRead($data);
        header('Content-Type: application/json;charset=utf-8');
        echo json_encode($arrtmp['head']);
    }

    /* 4.8.1.5	删除站内信请求 */
    public function deletemessagesAction() {

        //cantus
        $pro = $this->_request->getPost("pro");

        $msgIds = array();
        $ids = array();
        $pros = array();
        $id = $this->_request->getPost("id");
        if ($id != null && $pro != null) {
            $ids = explode(',', $id);
            $pros = explode(',', $pro);
        }

        for ($index = 0; $index < count($ids); $index++) {
            if ($pros[$index] == $this->_sessionlogin->info['account']) {
                $msgIds[$index] = array("id" => (int) $ids[$index], "flag" => 0);
            } else {
                $msgIds[$index] = array("id" => (int) $ids[$index], "flag" => 1);
            }
        }
        $data = array(
            "body" => array(
                "param" => array(
                    'msgIds' => $msgIds,
                ),
                "pager" => array(
                    'startNo' => 13,
                    "endNo" => 3
                )
            )
        );
        $arrtmp = $this->_message->deleteMessages($data);
        header('Content-Type: application/json;charset=utf-8');
        echo json_encode($arrtmp['head']);
    }

    //客户管理页面 发送站内信
    public function sendcustmessageAction() {

        $account = $this->_sessionlogin->info['account'];
        $title = $this->_request->getPost("title");
        $content = $this->_request->getPost("content");
        $subId = floatval(getSecurityInput($this->_request->getPost("subId")));

        try {
            if (getStrLen($content) > 300 || getStrLen($title) > 30) {
                echo Zend_Json::encode(array('status' => 1, 'data' => '站内信字符长度超限制'));
                exit;
            }
        } catch (Exception $e) {
            $this->log(Zend_log::ERR, '站内信字符长度超限制');
            echo Zend_Json::encode(array('status' => 1, 'data' => '站内信字符长度超限制'));
            exit;
        }
        $title = getSecurityInput($title);
        $content = getSecurityInput($content);

        $res = $this->_message->getAccountINfoById($subId);
        if (!isset($res["body"]["result"]["userStruc"]["parentId"]) || $res["body"]["result"]["userStruc"]["parentId"] != $this->_sessionlogin->info['id']) {
            echo Zend_Json::encode(array('status' => 1, 'data' => '没有权限给此用户发送站内信'));
            exit;
        }

        $data = array(
            "body" => array(
                "param" => array(
                    'sendAccount' => $account,
                    'title' => $title,
                    'content' => $content,
                    'receivesList' => array(
                        array(
                            "receiveId" => $subId,
                        ),
                    )
                ),
                "pager" => array(
                    'startNo' => 13,
                    "endNo" => 3
                )
            )
        );
        $arrtmp = $this->_message->sendMessage($data);
        header('Content-Type: application/json;charset=utf-8');
        if (isset($arrtmp['head']['status']) && $arrtmp['head']['status'] == '0') {
            echo Zend_Json::encode(array('status' => 0, 'data' => '发送成功'));
            exit;
        } else {
            echo Zend_Json::encode(array('status' => 1, 'data' => '发送失败，请重试'));
            exit;
        }
    }

    /* 4.8.1.7	给上下级发信请求 */
    public function sendmessageAction() {
        $account = $this->_sessionlogin->info['account'];
        $title = $this->_request->getPost("title","no title");
        $content = $this->_request->getPost("content");
        $typeid = $this->_request->getPost("typeid");

        try {
            if (getStrLen($content) > 300 || getStrLen($title) > 30) {
                echo Zend_Json::encode(array('status' => 1, 'data' => '站内信字符长度超限制'));
                exit;
            }
        } catch (Exception $e) {
            $this->log(Zend_log::ERR, '站内信字符长度超限制');
            echo Zend_Json::encode(array('status' => 1));
            exit;
        }
        $title = getSecurityInput($title);
        $content = getSecurityInput($content);

        if ($typeid == '2') { //上级
            $parentId = $this->_sessionlogin->info['parentId'];
            $data = array(
                "body" => array(
                    "param" => array(
                        'sendAccount' => $account,
                        'title' => $title,
                        'content' => $content,
                        'receivesList' => array(
                            array(
                                "receiveId" => $parentId,
                            ),
                        )
                    ),
                    "pager" => array(
                        'startNo' => 13,
                        "endNo" => 3
                    )
                )
            );
            $arrtmp = $this->_message->sendMessage($data);
        } elseif ($typeid == '1') { //下级	
            $receivesList = null;
            $receiveAccount = getSecurityInput($this->_request->getPost("sendAccount"));
            $receiveId = getSecurityInput($this->_request->getPost("receiveId"));
            if ($receiveAccount != null && $receiveId != null) {
                $receiveAccounts['receiveAccount'] = explode(',', $receiveAccount);
                $receiveIds['receiveId'] = explode(',', $receiveId);
            }
            for ($index = 0; $index < count($receiveAccounts['receiveAccount']); $index++) {
                $receivesList[$index] = array("receiveAccount" => $receiveAccounts['receiveAccount'][$index], "receiveId" => $receiveIds['receiveId'][$index]);
            }

            $data = array(
                "body" => array(
                    "param" => array(
                        'sendAccount' => $account,
                        'title' => $title,
                        'content' => $content,
                        'receivesList' => $receivesList,
                    ),
                    "pager" => array(
                        'startNo' => 13,
                        "endNo" => 3
                    )
                )
            );
            $arrtmp = $this->_message->sendMessage($data);
        }
        if (isset($arrtmp['head']['status'])) {
            if ($arrtmp['head']['status'] == '0') {
                echo Zend_Json::encode(array('status' => 0, 'data' => '发送成功'));
                exit;
            } else {
                echo Zend_Json::encode(array('status' => 1, 'data' => '发送失败,请重试'));
                exit;
            }
        } else {
            echo Zend_Json::encode(array('status' => 1, 'data' => '网络错误,请重试'));
            exit;
        }
    }
    
    public function hometipmessageAction() {
        $data = array(
            "body" => array(
                "pager" => array(
                    'startNo' => 1,
                    "endNo" => 5
                )
            )
        );
        // 取未读5条站内信
        $encodedValue = $this->_message->queryUnreadNoticePushMsgs($data);
        $receives['message'] = array();
        foreach ($encodedValue['body']['result']['receives'] as $k => $data) {
            if ('1' === (string) $data['messagePush']) {
                $receives['message'][] = $this->_getHomeMessageData($data, '0');
            } else if ('2' === (string) $data['messagePush']) {
                $receives['message'][] = $this->_getHomeMessageData($data, '1');
            } else if ('3' === (string) $data['messagePush']) {
                $receives['message'][] = $this->_getHomeMessageData($data, '2');
            }
        }
        header('Content-Type: application/json;charset=utf-8');
        echo json_encode($receives);
    }
    
    public function _getHomeMessageData($data, $type) {
        $sendTime = date('Y-m-d H:i:s', ceil($data['sendTime'] / 1000));
        $res = array(
            'status' => $type
            , 'mid' => $data['id']
            , 'title' => $data['title']
            , 'author' => $data['sendAccount']
            , 'time' => $sendTime
            , 'content' => $data['content']
        );
        return $res;
    }

    public function marknoticereadAction() {

        $msgIds = array();
        $ids = array();
        $pros = array();
        $id = getSecurityInput($this->_request->getPost("id"));
        $pro = getSecurityInput($this->_request->getPost("pro"));
        if ($id != null) {
            $ids = explode(',', $id);
            $pros = explode(',', $pro);
        }

        for ($index = 0; $index < count($ids); $index++) {
            if ($pros[$index] == $this->_sessionlogin->info['account']) {
                $msgIds[$index] = array("id" => (int) $ids[$index], "flag" => 0);
            } else {
                $msgIds[$index] = array("id" => (int) $ids[$index], "flag" => 1);
            }
        }
        $data = array(
            "body" => array(
                "param" => array(
                    'msgIds' => $msgIds,
                ),
                "pager" => array(
                    'startNo' => 13,
                    "endNo" => 3
                )
            )
        );
        $arrtmp = $this->_message->markNoticeRead($data);
        header('Content-Type: application/json;charset=utf-8');
        echo json_encode($arrtmp['head']);
    }
}
