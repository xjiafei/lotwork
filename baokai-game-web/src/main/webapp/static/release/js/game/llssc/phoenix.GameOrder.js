!function(a,b,c){var d,e={containerDom:"#J-balls-order-container",totalLotterysNumDom:"#J-gameOrder-lotterys-num",totalAmountDom:"#J-gameOrder-amount",selectedClass:"game-order-current",rowTemplate:'<li data-param="action=reselect&id=<#=id#>" id="gameorder-<#=id#>"><div class="result"><span style="font-family: Arial,verdana;"><span ><dfn>&yen;</dfn></span><#=amountText#><#=moneyUnitText#></span><span class="bet"><#=num#>注</span><span class="multiple"><#=multiple#>倍</span><span class="close"><a data-param="action=del&id=<#=id#>" href="javascript:void(0);">删除</a></span></div><span>[<#=typeText#>]</span><span style="position:relative" ><#=lotterysText#></span></li>',lotterysTextLength:20,addOrderDom:"#J-add-order"},f=a.Games,g=1,h=(Object.prototype.toString,function(a){for(var b,c=$.trim(a).split("&"),d=0,e=c.length,f={};e>d;d++)b=c[d].split("="),b.length>0&&(f[b[0]]=2==b.length?b[1]:"");return f}),j={init:function(a){var b=this,a=b.defConfig;b.cacheData={},b.cacheData.detailPostParameter={},b.orderData=[],f.setCurrentGameOrder(b),b.container=$(a.containerDom),b.totalLotterysNum=0,b.totalLotterysNumDom=$(a.totalLotterysNumDom),b.totalAmount=0,b.totalAmountDom=$(a.totalAmountDom),b.currentSelectId=0,b.eventProxy(),b.addEvent("afterAdd",function(){f.getCurrentGameTrace().getRowTableType();f.getCurrentGameTrace().isOpen()&&f.getCurrentGameTrace().updateOrder()}),b.addEvent("afterRemoveData",function(){f.getCurrentGameTrace().getRowTableType();f.getCurrentGameTrace().isOpen()&&f.getCurrentGameTrace().updateOrder()}),b.addEvent("afterResetData",function(){f.getCurrentGameTrace().getRowTableType();f.getCurrentGameTrace().updateOrder(!0)}),f.getCurrentGameTypes().addEvent("endChange",function(){b.cancelSelectOrder()})},setTotalLotterysNum:function(a){var b=this;b.totalLotterysNum=Number(a),b.totalLotterysNumDom.html(a)},setTotalAmount:function(a){var b=this;b.totalAmount=Number(a),b.totalAmountDom.html(b.formatMoney(a))},addData:function(a){var b=this;b.orderData.unshift(a)},getOrderById:function(a){var b=this,a=Number(a),c=b.orderData,d=0,e=c.length;for(d=0;e>d;d++)if(Number(c[d].id)==a)return c[d]},removeData:function(a){for(var b=this,a=Number(a),c=b.orderData,d=0,e=c.length;e>d;d++)if(c[d].id==a){b.fireEvent("beforeRemoveData",c[d]),b.orderData.splice(d,1),b.updateData(),b.fireEvent("afterRemoveData");break}$("#gameorder-"+a).remove(),b.fireEvent("afterRemoveData")},reSet:function(){var a=this;return a.container.empty(),a.orderData=[],a.updateData(),a.fireEvent("afterResetData"),a},updateData:function(){var a=this,b=a.getTotal();a.setTotalLotterysNum(b.count),a.setTotalAmount(b.amount)},getTotal:function(){for(var a=this,b=a.orderData,c=0,d=b.length,e=0,f=0;d>c;c++)e+=b[c].num,f+=b[c].num*b[c].onePrice*b[c].moneyUnit*b[c].multiple;return{count:e,amount:f,orders:b}},getOrderMaxMultiple:function(){for(var a,b,c,d,e=this,g=f.getCurrentGame().getDynamicConfig().gamelimit,h=e.getTotal().orders,i=0,j=h.length,k=[],l="";j>i;i++)a=h[i].type,b=h[i].multiple,d=Number(h[i].moneyUnit),g[a]?1==d?(c=Number(g[a].maxmultiple)<0?99999:Number(g[a].maxmultiple),k.push({gameMethod:a,maxnum:Math.floor(c/b)})):(c=Number(g[a].maxmultiple)<0?999999:Number(g[a].maxmultiple),k.push({gameMethod:a,maxnum:Math.floor(10*c/b)})):(l=f.getCurrentGame().getGameConfig().getInstance().getTitleByName(a),1==d?(c=99999,k.push({gameMethod:a,maxnum:Math.floor(c/b)})):(c=999999,k.push({gameMethod:a,maxnum:Math.floor(10*c/b)})));return k.sort(function(a,b){return a.maxnum-b.maxnum}),k.length>0?k[0]:{gameMethod:"",maxnum:1e5}},add:function(a){var b,c=this,d="",e=-1,h=c.defConfig.rowTemplate,i=0,j=f.getCurrentGameTrace().isOpen();if(c.fireEvent("beforeAdd",a),!(a.lotterys&&a.lotterys.length>0)){var k=f.getCurrentGameMessage();return void((!a.original||a.original.length<=0)&&k.show({type:"mustChoose",msg:'<span class="color-red">&nbsp;号码选择不完整，请重新选择！</span>',data:{tplData:{msg:'<span class="color-red">&nbsp;号码选择不完整，请重新选择！</span>'}}}))}if(c.currentSelectId>0)a.id=c.currentSelectId;else{if(e=c.checkData(a),-1!=e)return c.addMultiple(a.multiple,e),f.getCurrentGameMessage().show({type:"normal",closeText:"关闭",closeFun:function(){this.hide()},data:{tplData:{msg:"您选择的号码在号码篮已存在，将直接进行倍数累加"}}}),f.getCurrentGame().getCurrentGameMethod().reSet(),f.getCurrentGameStatistics().reSet(),c.updateData(),void c.fireEvent("afterAdd",a);a.id=g++}if(a.multiple=j?1:a.multiple,a.amountText=c.formatMoney(a.num*a.moneyUnit*a.multiple*a.onePrice),j&&("yingli"==f.getCurrentGameTrace().getRowTableType()||"yinglilv"==f.getCurrentGameTrace().getRowTableType()))for(i=0,b=c.orderData.length;b>i;i++)if(c.orderData[i].type!=a.type||c.orderData[i].moneyUnit!=a.moneyUnit)return void alert("盈利追号和盈利率追号不允许混投，\n 请确保玩法类型和元角模式一致");a.postParameter=f.getCurrentGame().getCurrentGameMethod().makePostParameter(a.original,a),a.oldMultiple=a.multiple,d=c.formatRow(h,c.rebuildData(a)),c.currentSelectId>0?c.replaceOrder(a.id,a):c.addData(a),c.currentSelectId>0?($(d).replaceAll($("#gameorder-"+c.currentSelectId)),c.cancelSelectOrder()):$(d).prependTo(c.container),f.getCurrentGame().getCurrentGameMethod().reSet(),f.getCurrentGameStatistics().reSet(),c.updateData(),c.fireEvent("afterAdd",a)},replaceOrder:function(a,b){for(var c=this,d=c.orderData,e=0,f=d.length;f>e;e++)if(d[e].id==a)return void(d[e]=b)},render:function(){for(var a=this,b=a.getTotal().orders,c=0,d=b.length,e=[],f=a.defConfig.rowTemplate;d>c;c++)e[c]=a.formatRow(f,a.rebuildData(b[c]));a.updateData(),a.container.html(e.join(""))},rebuildData:function(a){var b=this,c=b.defConfig,d=f.getCurrentGame().getGameConfig().getInstance(),e=d.getTitleByName(a.type);return a.typeText=e.join("_"),a.lotterysText=a.postParameter.length>c.lotterysTextLength?a.postParameter.substr(0,c.lotterysTextLength)+'... <span data-param="action=detail&id='+a.id+'" class="lottery-details">详情</span><div class="lottery-details-area"><div class="num"><span class="multiple"></span><em data-param="action=detailhide" class="close">×</em></div><div class="list"></div></div>':a.postParameter,a.moneyUnitText="元",a},formatRow:function(a,b){var c,d,e=b;for(c in e)e.hasOwnProperty(c)&&(d=RegExp("<#="+c+"#>","g"),a=a.replace(d,e[c]));return a},originalData:function(a){for(var b=this,c=[],d=0;d<a.length;d++)for(var e=0;e<a[d].length;e++)c[e]=c[e]||[],b.arrIndexOf(a[d][e],c[e])||c[e].push(a[d][e]);return c},arrIndexOf:function(a,b){for(var c,d=0;d<b.length;d++)b[d]==a&&(c=!0);return c||!1},checkData:function(a){var b,c,d=this,e=[],f=0;c=a.type,b=a.original;for(var f=0;f<b.length;f++)e.push(b[f].join(""));return moneyUnit=a.moneyUnit,d.searchSameResult(c,e.join(),moneyUnit)},eventProxy:function(){var a=this,b=a.container;b.click(function(b){var c,d=b.target.getAttribute("data-param");d&&""!=$.trim(d)&&(c=h(d),$.isFunction(a["exeEvent_"+c.action])&&a["exeEvent_"+c.action].call(a,c,b.target))})},exeEvent_del:function(a){var b=this,c=Number(a.id);b.currentSelectId==c&&(f.getCurrentGame().getCurrentGameMethod().reSet(),b.cancelSelectOrder()),b.removeData(c)},exeEvent_detailhide:function(a,b){$(b).parents(".lottery-details-area").eq(0).hide()},exeEvent_detail:function(a,b){var c=this,b=$(b),d=Number(a.id),e=d,f=b.next(),g=f.find(".multiple"),h=f.find(".list"),i=c.getTotal().orders,j="";c.cacheData.currentDetailId&&$("#gameorder-"+c.cacheData.currentDetailId+" .lottery-details-area").hide();for(var k=i.length-1;k>=0;k--)if(i[k].id==d){i=i[k];break}g.text("共 "+i.num+" 注"),j=i.postParameter,c.cacheData.currentDetailId=e,f.css({left:f.position().left+f.width()+5}),h.html(j),f.show()},exeEvent_reselect:function(a){var b=this,c=Number(a.id);b.selectOrderById(c)},updateDomStatus:function(){var a=this,b="important",c=a.currentSelectId,d=$(a.defConfig.addOrderDom);c>0?d.addClass(b):d.removeClass(b)},selectOrderById:function(a){var b=this,c=b.getOrderById(a),d=c.original,e=c.type,g=b.defConfig.selectedClass,h=$("#gameorder-"+a);-1==b.getOrderById(a).type.indexOf("danshi")&&(h.parent().children().removeClass(g),h.addClass(g),f.getCurrentGameTypes().changeMode(e),f.getCurrentGameStatistics().getMoneyUnitDom().setValue(c.moneyUnit),f.getCurrentGameStatistics().getMultipleDom().setValue(c.multiple),f.getCurrentGame().getCurrentGameMethod().reSelect(d),b.currentSelectId=a,b.updateDomStatus())},cancelSelectOrder:function(){{var a=this,b=a.currentSelectId;$(a.defConfig.addOrderDom)}b>0&&($("#gameorder-"+b).removeClass(a.defConfig.selectedClass),a.currentSelectId=0,a.updateDomStatus(),f.getCurrentGame().getCurrentGameMethod().reSet())},formatMoney:function(a){var a=Number(a),b=/(-?\d+)(\d{3})/;for(a=Number.prototype.toFixed?a.toFixed(2):Math.round(100*a)/100,a=""+a;b.test(a);)a=a.replace(b,"$1,$2");return a},searchSameResult:function(a,b,c){for(var d,e=this,f=0,g=[],h=e.getTotal().orders;f<h.length;f++){g=[],d=h[f],ordersLotteryText=d.original;for(var i=0;i<ordersLotteryText.length;i++)g.push(ordersLotteryText[i].join(""));if(d.type==a&&b==g.join()&&d.moneyUnit==c)return f}return-1},addMultiple:function(a,b){var c,d=this,e=d.getTotal().orders,g=e[b],h=g.type,i=999999;if(!f.getCurrentGameTrace().isOpen()){if(c=f.getCurrentGame().getDynamicConfig().gamelimit,c[h]&&(i=1==g.moneyUnit?c[h].maxmultiple:10*c[h].maxmultiple,i=0>i?99999:i),g.multiple+a>i)return void setTimeout(function(){f.getCurrentGameMessage().show({type:"normal",closeText:"确定",closeFun:function(){console.log(f),e[b].multiple=i,e[b].oldMultiple=e[b].multiple,e[b].amount=e[b].num*e[b].moneyUnit*e[b].multiple*e[b].onePrice,e[b].amountText=d.formatMoney(e[b].num*e[b].moneyUnit*e[b].multiple*e[b].onePrice),d.render(),f.getCurrentGame().getCurrentGameMethod().reSet(),f.getCurrentGame().getCurrentGameMethod().ballsErrorTip(),f.getCurrentGameStatistics().reSet(),this.hide()},data:{tplData:{msg:"该组号码倍数已经超过最大限制("+i+"倍)，将调整为系统支持的最大倍数进行添加"}}})},100);e[b].multiple+=a,e[b].oldMultiple=e[b].multiple,e[b].amount=e[b].num*e[b].moneyUnit*e[b].multiple*e[b].onePrice,e[b].amountText=d.formatMoney(e[b].num*e[b].moneyUnit*e[b].multiple*e[b].onePrice),d.render(),f.getCurrentGame().getCurrentGameMethod().reSet(),f.getCurrentGame().getCurrentGameMethod().ballsErrorTip(),d.cancelSelectOrder()}},editMultiples:function(a){for(var b=this,c=b.getTotal().orders,d=0,e=c.length;e>d;d++)c[d].multiple=a,c[d].amount=c[d].num*c[d].moneyUnit*c[d].multiple*c[d].onePrice,c[d].amountText=b.formatMoney(c[d].amount);b.render(),b.cancelSelectOrder()},editMultiple:function(a,b){var c=this,d=c.getTotal().orders;d[b].multiple=a,d[b].amount=d[b].num*d[b].moneyUnit*d[b].multiple*d[b].onePrice,d[b].amountText=c.formatMoney(d[i].amount),c.render(),c.cancelSelectOrder()},restoreMultiples:function(){for(var a=this,b=a.getTotal().orders,c=0,d=b.length;d>c;c++)b[c].multiple=b[c].oldMultiple,b[c].amount=b[c].num*b[c].moneyUnit*b[c].multiple*b[c].onePrice,b[c].amountText=a.formatMoney(b[c].amount);a.render(),a.cancelSelectOrder()}},k=a.Class(j,c);k.defConfig=e,k.getInstance=function(a){return d||(d=new k(a))},a[b]=k}(phoenix,"GameOrder",phoenix.Event);