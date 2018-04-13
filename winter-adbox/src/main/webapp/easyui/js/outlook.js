$(function() {
		initLeftMenu();
		initMenuClickEvent();
		menuTabClose();
	}
		
)

function initLeftMenu() {
	$("#nav").accordion({animate:false});
	 $.each(_menus.menus,function(i,n) {
	 		var menuContent = '<ul>';
	 		$.each(n.menus,function(j,detailMenu){
	 			menuContent += '<li><div><a  href="#" ref ="'+detailMenu.url+'" ><span class="icon '+detailMenu.icon+'" >&nbsp;</span><span class="nav">' + detailMenu.menuname + '</span></a></div></li> ';
	 		})
	 		menuContent = menuContent + '</ul>';
	 		var selecte = false;
	        if(i==0) {
	        	selecte = true;
	        }
	 		$("#nav").accordion(
	 			'add',{
	 				title:n.menuname,
					content:menuContent,
					iconCls:'icon ' + n.icon,
					 selected:selecte
	 			}
	 		)
	 });
	 
}

function initMenuClickEvent() {
	$(".easyui-accordion li a").click(function() {
//		debugger;
		var _this = $(this);
		var url = _this.attr("ref");
		var title = _this.find("span[class='nav']").text();
		var icon = _this.find("span[class^='icon']").attr("class");
		addMenuTab(url,title,icon);
		$(".easyui-accordion div").removeClass("selected");
		_this.parent().addClass("selected");
	}).hover(
		function() {
			$(this).parent().addClass("hover");
		},
		function() {
			$(this).parent().removeClass("hover");
		}
	)
}

function addMenuTab(url,title,icon) {
	if($("#tabs").tabs("exists",title)) {
		$('#tabs').tabs('select', title);
	}else {
		var content = addMenuContex(url);
		$('#tabs').tabs("add",
		{
			title:title,
			content:content,
			closable:true,
			iconCls:icon
		}
		);
	}
	menuTabClose();
}

function addMenuContex(url) {
	return '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;padding:10px"></iframe>';
}

function menuTabClose() {
	$(".tabs-inner").bind("dblclick",function() {
		var menuTitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', menuTitle);
	});
	
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}

function initMenuEvent() {
	$("mm-tabupdate").click(function() {
		var tab = $('#tt').tabs('getSelected');  
		
		
	})
	
}