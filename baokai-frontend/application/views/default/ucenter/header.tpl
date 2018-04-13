{include file='/default/layout/header_front_banner.tpl'}

{literal}

<script>
// 顶部导航需要的脚本
(function($){
	$('[name="Page_firefrog_index"]').length>0?$('.back-top-home').hide():$('.back-top-home').show();
	new phoenix.Hover({triggers:'#J-top-game-menu2',panels:'#J-top-game-menu2 .game-menu-panel',hoverDelayOut:300,currClass:'game-menu-current'});
})(jQuery);
</script>

{/literal}
<!-- 公告通知 -->
<script type="text/javascript" src="{$path_js}/js/phoenix.Notice.js" ></script>
