/**
 * Created by user on 16/3/23.
 */

//特码直选玩法实现类
(function(host, GameMethod, undefined){
    var defConfig = {
            oddDom: '.game-odds ol',
            ruleDom: '.rule-text .text-box',
            exampleDom: '.prompt-text .text-box',
            name: 'tema.zhixuan',
            colorNameMap: ['red','blue','green'],
            colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
            zodiacsMap: [
                 {'enName':'shu','cnName':'鼠'},
                {'enName':'niu','cnName':'牛'},
                {'enName':'hu','cnName':'虎'},
                {'enName':'tu','cnName':'兔'},
                {'enName': 'long','cnName':'龙'},
                {'enName':'she','cnName':'蛇'},
                {'enName': 'ma','cnName':'马'},
                {'enName':'yang','cnName':'羊'},
                {'enName':'hou','cnName':'猴'},
                {'enName':'ji','cnName':'鸡'},
                {'enName': 'gou','cnName':'狗'},
                {'enName':'zhu','cnName':'猪'}
                ],
            halfMap: [
                {'enName':'da','cnName':'大'},
                {'enName':'xiao','cnName':'小'},
                {'enName':'dan','cnName':'单'},
                {'enName':'shuang','cnName':'双'}
            ],
            colorMap: [
                {'enName':'red','cnName':'红波'},
                {'enName':'blue','cnName':'蓝波'},
                {'enName':'green','cnName':'绿波'}
            ]
        },
        Games = host.Games,
        LHC = Games.LHC.getInstance();

    //定义方法
    var pros = {
        init:function(cfg){
            var me = this;
            gameConfig = Games.getCurrentGame().getGameConfig().getInstance();
            me.setOdds(gameConfig);
            me.setTips(gameConfig);
        },
        rebuildData:function(){
            var me = this;
            me.balls = [
                [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                 -1,-1,-1,-1,-1,-1,-1,-1,-1]
            ];
        },
        buildUI:function(){
            var me = this;
            me.container.html(html_all);
        },

        setOdds: function(cfg){
            var me = this, url = cfg.getStraightOddsUrl();
            //获取游戏相关数据
            $.ajax({
                url: url,
                dataType: 'json',
                success:function(result){
                    if(Number(result['isSuccess']) == 1){
                        data = result['oddsList'];
                        me.buildOdds(data);
                    }else{

                    }
                }
            })
        },
        buildOdds: function(arr){
            var $dom = this.container.find(defConfig.oddDom),
                html_li = [];

            $.each(arr,function(i,v){
                html_li.push('<li data-param="action=changeOdd&value='+ v +'">'+ v +'</li>');
            });

            $dom.html(html_li.join(''));

            //读取本地缓存 调取默认倍率
            var default_cookie = $.cookie('user_odd'),
                $li = $dom.find('li'),
                now = 0;
            $.each(arr,function(i,v){
                if(v==default_cookie){
                    now = i;
                }
            });
            $li.eq(now).trigger('click');
        },
        setTips: function(cfg){
            var me = this,
                data = null,
                arr = [];
			$.each(cfg.getGameTips(), function(i, item){
				if(item['name'] == 'tema' ){
					data = item.childs;
				}
			});	
			
            $.each(data,function(i,v){
                if(v['name'] == 'zhixuan' ){
					$.each(v.childs, function(i, v){
						var $domRule = me.container.find(defConfig.ruleDom),
							$domExample = me.container.find(defConfig.exampleDom);
						$domRule.html(v['rule']);
						$domExample.html(v['example']);
					});
                }
            });
        }
    };


    //html模板
    var html_odds = [];
    //赔率
    html_odds.push('<div class="game-top"><div class="game-odds">');
    html_odds.push('<label class="odds-hide"><span data-param="action=toggle" >-</span>赔率选择：</label>');
    html_odds.push('<ol>');
    html_odds.push('<li class="active">40</li>');
    html_odds.push('</ol>');
    html_odds.push('</div>');
    html_odds.push('<div class="game-prompt">');
    html_odds.push('<a class="rule-example"  href="javascript:;">玩法规则</a>');
    html_odds.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
    html_odds.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_odds.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_odds.push('</div></div>');
    //号码球
    var html_direct = [];
    html_direct.push('<div class="ball-list ball-direct">');

    $.each([0,1,2,3,4], function(i){
        html_direct.push('<ul class="ball-list-ul">');
        $.each([1,2,3,4,5,6,7,8,9,10],function(j,v){
            var now_value = i*10 + v,
                now_str = '';
            if(Number(now_value) > 49) return;
            now_str = Number(now_value) <10 ? "0"+now_value:""+now_value;
            html_direct.push('<li data-param="action=ball&row=0&value='+ now_value +'&type=zhixuanema"><span class="ball '+ defConfig.colorNameMap[defConfig.colorArr[now_value-1]] +'">'+now_str+'</span><span class="ride">x</span><span class="ball-odds">40</span></li>');
        });
        html_direct.push('</ul>');
    });
    html_direct.push('</div>');
    //选球工具
    var html_tool = [];
    html_tool.push('<div class="select-tool">');
    html_tool.push('<ul class="tool-animal">')
    $.each(defConfig.zodiacsMap,function(i,v){
        html_tool.push('<li data-param="action=batchSetBall&value='+ v['enName'] +'&type=zodiacs">'+ v['cnName']+'</li>');
    });
    html_tool.push('</ul>');

    html_tool.push('<ul class="tool-size">');
    $.each(defConfig.halfMap,function(i,v){
        html_tool.push('<li data-param="action=batchSetBall&value='+ v['enName'] +'&type=half">'+ v['cnName'] +'</li>');
    });
    html_tool.push('</ul>');

    html_tool.push('<ul class="tool-color">');
    $.each(defConfig.colorMap,function(i,v){
        html_tool.push('<li data-param="action=batchSetBall&value='+ v['enName'] +'&type=color">'+ v['cnName'] +'</li>');
    });
    html_tool.push('</ul>');

    html_tool.push('<div class="tool-bits">');
    html_tool.push('<span class="tool-title">尾：</span>');
    html_tool.push('<ul>');
    $.each([0,1,2,3,4,5,6,7,8,9],function(i,v){
        var now_value =  v;
        html_tool.push('<li data-param="action=batchSetBall&value='+ now_value +'&type=bits">'+now_value+'</li>');
    });
    html_tool.push('</ul></div>');

    html_tool.push('<div class="tool-ten">');
    html_tool.push('<span class="tool-title">头：</span>');
    html_tool.push('<ul>');

    $.each([0,1,2,3,4],function(i,v){
        var now_value =  v;
        html_tool.push('<li data-param="action=batchSetBall&value='+ now_value +'&type=ten">'+now_value+'</li>');
    });
    html_tool.push('</ul>');
    html_tool.push('</div>');

    html_tool.push('<div class="tool-keyboard">');
    html_tool.push('<input data-param="action=input" placeholder="键盘输入号码" type="text">');
    html_tool.push('</div>');
    html_tool.push('</div>');


    //尾部
    var html_bottom = [];
    html_bottom.push('</ul>');
    html_bottom.push('</div>');

    //拼接所有
    var html_all = [];
    html_all.push(html_odds.join(''));
    html_all.push(html_direct.join(''));

    html_all.push(html_tool.join(''));
    html_all.push(html_bottom.join(''));

    //继承GameMethod
    var Main = host.Class(pros, GameMethod);
    Main.defConfig = defConfig;
    //将实例挂在游戏管理器实例上
    LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
