$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	$('.col-side .nav dd:eq(2)').attr("class","current");
		var msgBox = {
			dom:$('#J-msgbox'),
			//消息模板
			tpl:$('#J-msg-tpl').html(),
			//发送者id
			userid:Number($('#J-userid').val()),
			//消息id
			msgid:Number($('#J-msgid').val()),
			//每次加载条数
			loadNum:10,
			startid:1,
			//是否已经到达记录末尾
			isEnd:false,
			init:function(){
				var me = this;
				var scrollTimer = null;
				me.dom.on( 'scroll', function () {
				    if ( scrollTimer ) {
				        clearTimeout(scrollTimer);
				    }
				    scrollTimer = setTimeout(function(){
				        me.isPositionBottom();
				    }, 200);
				});
			},
			//判断滚动条是否到达底部
			isPositionBottom:function(){
				var me = this,dom = me.dom;
				if(!me.isEnd && (dom.outerHeight() + dom.scrollTop() >= dom.get(0).scrollHeight)){
					me.loadData();
				}
			},
			scrollPanel:function(num){
				this.dom.scrollTop(num);
			},
			loadData:function(){
				var me = this,params = {};
				params = {
					userid:me.userid,
					msgid:me.msgid,
					startid:me.startid,
					num:me.loadNum
				};
				$.ajax({
					url:baseUrl+"/noticeAdmin/searchUserMsgInfo",
					dataType:'json',
					data:{id:me.msgid,pageNo:me.startid},
					success:function(data){
						var list = [],tpl = me.tpl,strArr = [],cls,i = 0,len,template = phoenix.util.template;
						if(Number(data['isSuccess']) == 1){
							list = data['data'];
							//已经读取完毕，不需要再发送请求
							if(list.length < me.loadNum){
								me.isEnd = true;
							}
							tpl = me.tpl;
							for(len = list.length;i < len;i++){
								list[i]['className'] = list[i]['type'] == 1 ? 'dia-list-left' : 'dia-list-right';
								strArr.push(template(tpl, list[i]));
							}
							me.startid++;
							$(strArr.join('')).appendTo(me.dom);
							// me.dom.html(strArr.join(''));
						}else{
							alert(data['msg']);
						}
					}
				});
			},
			//添加消息
			addMsg:function(){
			}
		};
		msgBox.init();
		msgBox.loadData();
})