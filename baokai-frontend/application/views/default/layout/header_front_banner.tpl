{*
/**
 * 功能名稱 : 使用者介面 Banner
 * 功能說明 : 前端 tool bar 整合 tool bar 共用頁面，利用參數化的方式整合不同介面之 tool bar 
 * 
 * RIVISION HISTORY
 * --------------------------------------------------------
 * 2016.03.14 David Create
 * 
 *  */
*}
<script type="text/javascript">
    //banner script
    var userName = "{$smarty.session.datas.info.account}";              {* user name *}
    var indexServer ="";                                                {* php server url 首頁 *}
    var userServer = indexServer;                                       {* user server url *}
    var gameServer = "{$game_server}";                                  {* game server url *}
    var ptgameServer = "{$ptgame_server}";                              {* PTgame server url *}
    var wagameServer = "{$wggame_server}";         					    {* wagame server url *} 
    var fhxgameServer = indexServer + "/fhx/index";                     {* fhxgame server url *}
    var userbal = "/index/getuserbal";                                  {* get userbal url 餘額查詢 *}
    var permisssion = "";                                               {* get available user menu 使用者選單 *}
    var support_service_link_a = "{$smarty.session.datas.info.account}";{* get user accunt for support_service *}
    var support_service_link_aid = "{$smarty.session.datas.info.id}";   {* get user id for support_service *}
    var support_service_link_l = "{$smarty.session.datas.info.vipLvl}"; {* get user vipLvl for support_service *}
    var displayAvailBal = "{$displayAvailBal}";                         {* displayAvailBal *}
    var bannerHeadClass = "g_34";                                       {* <div class="'+bannerHeadClass+'"> banner head class *}
    var bannerlogotitle='';                                             {* banner logo attribute : title Banner logo的文字 *}
    var bannerlogohref=userServer + "/index";                           {* banner logo attribute : href  Banner logo的超連結 *}
    var userClassId = "J-user-center";                                  {* user panel class id 使用者選單 *}
    var msgClassId = "J-top-user-message";                              {* message panel class id 站內信 *}
    var betHistory = gameServer + "/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7";{* bet history 投注記錄查詢 *}
    var quickloginTarget = 'target="_blank"';                           {* quick login attribute : target 快速登入超連結屬性 target *}
    {if $smarty.session.datas.info.userLvl neq '-1' and $smarty.session.datas.info.isAllZero neq '0'}{* get available user menu 取得使用者選單 *}
        permisssion = '<a  href="'+indexServer+'/proxy/index" id="proxyCenter">代理中心</a>';
        permisssion +='<a  href="'+gameServer+'/gameUserCenter/queryCurrentUserReport?userId={$smarty.session.datas.info.id}" id="queryReport">报表查询</a>';
    {/if}
    var lhcStatus = "{$smarty.session.datas.info.lhcStatus}";              {* lhc_status *}    
</script>  
<!-- 聚合頁入口 -->
<script type="text/javascript" src="{$path_js}/js/activity/2016_DecRedBow/group/js/Tdrag.min.js"></script>	
<!--loading JS Banner -->
<script type="text/javascript" src="{$path_js}/html/toolBar/frontend-banner.js" defer></script>
<!-- toolbar Start --> 
<div id = "topbar" class="topbar"></div>
<!-- toolbar end --> 
<!-- header start -->   
<div id = "header" class="header"></div>
<!-- header end --> 

<script defer="true" src="/support/support_service?p=FF4"></script>
<script type="text/javascript" src="{$path_js}/js/operations/removeIssue/removeIssueforGame.js" defer></script>