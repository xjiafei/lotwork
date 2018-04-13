<?php

class Default_VipexpController extends CustomControllerAction {

    public $_clientobject;

    public function init() {
        parent::init();
        $this->_clientobject = new Client($this->_request->getModuleName(), $this->_request->getControllerName(), $this->_request->getActionName());
    }

    public function indexAction() {
        $vipLvl = $this->_sessionlogin->info['vipLvl'];
        if ($vipLvl == null || $vipLvl < 1) {
            $this->_redirect('/');
        }
        $indexInfo = $this->queryIndexInfo($this->_sessionlogin->info['id']);
        $notices = $indexInfo['lastNotice'];
        $qqs = $indexInfo['qqs'];
        $vipInfo = $this->queryVipInfo($this->_sessionlogin->info['account']);
        $vipInfo['privileges'] = $this->mappingPrivileges($vipInfo['privilege']);
        $adverts = $this->queryAdverts();
        $defaultBanners = $this->getDefaultBanner();
        $banners = $this->mergeAdvertAndBanners($defaultBanners['vip_banner_center'], $adverts['vip_banner_center'], 1);
        $activitys = $this->mergeAdvertAndBanners($defaultBanners['vip_activity_bottom'], $adverts['vip_activity_bottom'], 4);
        $privileges = $this->mergeAdvertAndBanners($defaultBanners['vip_privilege_center'], $adverts['vip_privilege_center'], 3);
        $this->view->assign('vipInfo', $vipInfo);
        $this->view->assign('banners', $banners);
        $this->view->assign('activitys', $activitys);
        $this->view->assign('privileges', $privileges);
        $this->view->assign('notices', $notices);
        $this->view->assign('qqs', $qqs);
        $this->view->display('default/ucenter/vip/vip2.tpl');
    }

    private function queryIndexInfo($userId) {
        $aUri['index'] = 'queryIndexInfo';
        $data['param']['userId'] = $userId;
        $res = $this->_clientobject->inRequestV2($data, $aUri);
        $result = array();
        if (isset($res['head']['status']) && $res['head']['status'] == '0') {
            if (isset($res['body']['result']) && count($res['body']['result']) > 0) {
                //公告
                if (isset($res['body']['result']['lastNotice']) && count($res['body']['result']['lastNotice']) > 0) {
                    foreach ($res['body']['result']['lastNotice'] as $key => $value) {
                        if ($value['noticelevel'] == 4) {
                            $result['lastNotice'][$key]['id'] = $value['id'];
                            $result['lastNotice'][$key]['title'] = mb_substr($value['title'], 0, 14, 'utf8');
                            $result['lastNotice'][$key]['gmtEffectBegin'] = date('m-d', getSrtTimeByLong($value['gmtEffectBegin']));
                            $result['lastNotice'][$key]['noticelevel'] = $value['noticelevel'];
                        }
                        if(count($result['lastNotice'])>=5){
                            break;
                        }
                    }
                }
                if (isset($res['body']['result']['qqs']) && count($res['body']['result']['qqs']) > 0) {
                    foreach ($res['body']['result']['qqs'] as $key => $value) {
                        $result['qqs'][$key]['qq'] = $value['qq'];
                    }
                }
            }
        }
        return $result;
    }

    private function getDefaultBanner() {
        $member = new Member();
        $res = $member->getAllAdSpace();
        $result = array();
        if (isset($res['head']['status']) && $res['head']['status'] == '0') {
            if (isset($res['body']['result']) && count($res['body']['result']) > 0) {
                foreach ($res['body']['result']as $key => $value) {
                    $name = $value['name'];
                    $result[$name] = array(
                        dftImgs => array(),
                        isDftUsed => $value['isDftUsed'],
                        isUsed => $value['isUsed'],
                        urlTarget => $value['urlTarget']
                    );
                    array_push($result[$name]['dftImgs'], $value['dftImg']);
                    if ($value['dftImgs'] != null) {
                        $imgs = preg_split("/;/", $value['dftImgs']);
                        $result[$name]['dftImgs'] = array_merge($result[$name]['dftImgs'], $imgs);
                    }
                }
            }
        }
        return $result;
    }

    private function queryAdverts() {
        $aUri['vipData'] = 'queryAdverts';
        $data['param'] = array('vip_banner_center', 'vip_activity_bottom', 'vip_privilege_center');
        $res = $this->_clientobject->inRequestV2($data, $aUri);
        $result = array();
        if (isset($res['head']['status']) && $res['head']['status'] == '0') {
            if (isset($res['body']['result']) && count($res['body']['result']) > 0) {
                foreach ($res['body']['result']as $key => $value) {
                    $result[$value['name']] = $value['list'];
                }
            }
        }
        return $result;
    }

    private function mergeAdvertAndBanners($defaultBanner, $adverts, $needCount) {
        $result = array();
        $count = count($adverts);
        if ($defaultBanner['isUsed']) {
            for ($i = 0; $i < $count; $i++) {
                $advert = $adverts[$i];
                $advert['urlTarget'] = $defaultBanner['urlTarget'];
                array_push($result, $advert);
            }
            if($count<$needCount && $defaultBanner['isDftUsed']){
                for($i=$count;$i<$needCount;$i++){
                    $advert = array();
                    $src = $defaultBanner['dftImgs'][$i];
                    if ($src == null || strlen($src) == 0) {
                        $src = $defaultBanner['dftImgs'][0];
                    }
                    $advert['id'] = 0;
                    $advert['src'] = $src;
                    $advert['urlTarget'] = $defaultBanner['urlTarget'];
                    array_push($result, $advert);
                }
            }
        }
        return $result;
    }

    private function queryVipInfo($account) {
        $vipInfo = array(
            vipLv => $this->_sessionlogin->info['vipLvl'], 
            userExp => 0, 
            totExp => 0, 
            privilege => array(), 
            expHistorys => array() , 
            isMax =>false , 
            isLoadData => false
        );
        $aUri = $this->_config->vip_api.'/?controller=experienceforfh4&action=getexperience&username='.$account;
        $maxLevelConfig = $this->getconfigvaluebykey('vip', 'max_level');
        $maxLevel = $maxLevelConfig['val'];
        $vipInfo['isMax'] = $vipInfo['vipLv']>=$maxLevel;
        $res = $this->_clientobject->outRequest(null, $aUri);
        if ($res != null && $res['vipLv'] != null) {
            $vipInfo['vipLv'] = $res['vipLv'];
            $vipInfo['userExp'] = $res['userExp'];
            $vipInfo['totExp'] = $res['totExp'];
            $vipInfo['privilege'] = $res['privilege'];
            $vipInfo['expHistorys'] = $res['expHistorys'];
            $vipInfo['isMax'] = $vipInfo['vipLv']>=$maxLevel;
            $vipInfo['isLoadData'] = true;
        }
        return $vipInfo;
    }

    private function mappingPrivileges($userPrivileges) {
        $privilegeConfig = $this->getconfigvaluebykey('vip', 'privileges_setting');
        $privilegeSetting = json_decode($privilegeConfig['val'], true);
        $privileges = array();
        foreach ($privilegeSetting as $key => $value) {
            $privilege = array(
                id => $value['id'],
                title => $value['title'],
                isActive => in_array($value['id'], $userPrivileges)
            );
            array_push($privileges, $privilege);
        }
        return $privileges;
    }

    //获取通用配置接口数据
    private function getconfigvaluebykey($module, $function) {
        $aUri['vipData'] = 'getconfigvaluebykey';
        $data = array(
            'param' => array(
                'module' => $module,
                'function' => $function
            )
        );
        $result = $this->_clientobject->inRequestV2($data, $aUri);
        if (isset($result['body']['result']) && count($result['body']['result']) > 0) {
            return $result['body']['result'];
        } else {
            return false;
        }
    }

}
