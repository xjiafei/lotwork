/**
 * Created by user on 16/3/23.
 */

(function(host, name, Event, undefined){
    var defConfig = {
            //主面板dom
            mainPanel : '#J-gameType-panel',
            //面板数据
            data : '',
            //html结构
            html : $('<div class="game-type-box"></div>')

        },
    //渲染实例
        instance,
    //游戏实例
        Games = host.Games;

    //渲染方法
    var pros = {
        init:function(cfg){
            var me = this;
            //缓存方法
            Games.setCurrentGameTypes(me);
            me.container = $(cfg.mainPanel);
            //计数
            me.count = 0;
            //渲染计数
            me.showCount = 0;
            //面板数据
            me.data = Games.getCurrentGame().getGameConfig().getInstance().getTypes();
            //面板DOM
            me.html = cfg.html;
            //执行渲染
            setTimeout(function() {
                me._showMainHTML(me.data);
            }, 0);

        },
        //获取外部容器DOM
        getContainerDom: function(){
            return this.container;
        },
        //数据解析
        _showMainHTML: function(data, inner) {
            var me = this;
            for(var i = 0, c, l = data.length; i < l; i++) {
                c = data[i];
                if (Object.prototype.toString.call(c.childs) == '[object Array]' && c.childs.length != 0) {
                    me._bulidHTMl(c, 'top');
                    //末级列表
                    me._showMainHTML(c.childs, true);
                } else {
                    //末级列表
                    me._bulidHTMl(c, 'terminal');
                }
            }
            //遍历结束输出html结果
            if (!inner) {
                me._appendHtml(me.html);
            }
        },
        //输出dom结构
        //@type 需要指定的层级
        _bulidHTMl: function(data, type) {
            var me = this;
            switch(type){
                //一级
                case 'top':
                    me.html.append('<dl><dt class="'+data.name+'">' + data.title + '</dt></dl>');
                    break;
                //二级
                case 'terminal':
                    me.html.find('.' + data.parent).parent().append('<dd class="'+data.name+'">'+data.title+'</dd>');
                    break;
            }
        },
        _appendHtml: function(html) {
            var me = this;
            $(me.defConfig.mainPanel).prepend(html);
            me._bindTagSelect();
            //定时器队列
            setTimeout(function(){
                me.fireEvent('endShow');
            },10);
        },
        _bindTagSelect: function() {
            var me = this;
            this.container.on('click', 'dd',function() {
                me._getMode($(this));
            });
        },
        _getMode: function(dom) {
            var me = this,
                name = dom.attr('class').replace(/\s.*/g, ''),
                modeName = dom.parent('dl').find('dt').attr('class'),
                full = modeName + '.' + name;
            me.changeMode(full);
        },
        //切换事件
        changeMode: function(modeName){
            var me = this,
                name = modeName.split('.'),
                container = me.getContainerDom(),
                cls = 'active',
                titles;
            try{
                if(modeName == Games.getCurrentGame().getCurrentGameMethod().getGameMethodName()){
                    return;
                }
            }catch(e){
            }

            //执行自定义事件
            me.fireEvent('beforeChange', me.container, modeName);
            //执行切换
            Games.getCurrentGame().switchGameMethod(modeName);

            //执行高亮
            titles = container.find('dd');
            titles.removeClass(cls);
            container.find('dd.' + name[1]).addClass(cls);

            //执行自定义事件
            me.fireEvent('endChange');
        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function(cfg){
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;
})(phoenix, "GameTypes", phoenix.Event);
