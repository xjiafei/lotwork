/**
 * Created by user on 16/3/23.
 */

//特码直选玩法实现类
(function(host, GameMethod, undefined){
    var defConfig = {
            //选球倍率值容器
            ballOddDom:'.ball-odds',
            ruleDom: '.rule-text .text-box',
            exampleDom: '.prompt-text .text-box',
            name: 'tema.quwei',
            colorNameMap: ['red','blue','green'],
            colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
            zodiacsMap: [
                 {'enName':'shu','cnName':'鼠','value':'1'},
                {'enName': 'ma','cnName':'马','value':'2'},
                {'enName':'niu','cnName':'牛','value':'3'},
                {'enName':'yang','cnName':'羊','value':'4'},
                {'enName':'hu','cnName':'虎','value':'5'},
                {'enName':'hou','cnName':'猴','value':'6'},
                {'enName':'tu','cnName':'兔','value':'7'},
                {'enName':'ji','cnName':'鸡','value':'8'},
                {'enName': 'long','cnName':'龙','value':'9'},
                {'enName': 'gou','cnName':'狗','value':'10'},
                {'enName':'she','cnName':'蛇','value':'11'},
                {'enName':'zhu','cnName':'猪','value':'12'}
                ],
            halfMap: [
                {'enName':'da','cnName':'大','value':'1'},
                {'enName':'xiao','cnName':'小','value':'2'},
                {'enName':'heda','cnName':'和大','value':'3'},
                {'enName':'hexiao','cnName':'和小','value':'4'},
                {'enName':'dan','cnName':'单','value':'5'},
                {'enName':'shuang','cnName':'双','value':'6'},
                {'enName':'hedan','cnName':'和单','value':'7'},
                {'enName':'heshuang','cnName':'和双','value':'8'},
                {'enName':'daxiao','cnName':'大肖','value':'9'},
                {'enName':'xiaoxiao','cnName':'小肖','value':'10'},
                {'enName':'weida','cnName':'尾大','value':'11'},
                {'enName':'weixiao','cnName':'尾小','value':'12'}
            ],
            colorMap: [
                {'enName':'red','cnName':'红','value':'1'},
                {'enName':'blue','cnName':'蓝','value':'2'},
                {'enName':'green','cnName':'绿','value':'3'}
            ]
        },
        Games = host.Games,
        zoadiacList = Games.getCurrentGame().getGameConfig().getInstance().getZoadiacList(),
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
                [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
                [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
                [-1,-1,-1]
            ];
        },
        buildUI:function(){
            var me = this;
            me.container.html(html_all);
        },
        setOdds: function(cfg){
            var me = this,
                data = cfg.getGameOdds(),
                arr = [];
            $.each(data,function(i,v){
                if(v['name'] == 'quwei' ){
                    arr = v['childs'];
                }
            });
            me.buildOdds(arr);
        },
        buildOdds: function(arr){
            var me = this;
            $.each(arr,function(i,v){
                var $dom = me.container.find('.'+v['name']).find(defConfig.ballOddDom);
                $dom.html(v['odds']);
            });

        },
        setTips: function(cfg){
            var me = this,
                data = cfg.getGameTips()[1]['childs'],
                arr = [];
            $.each(data,function(i,v){
                if(v['name'] == 'quwei' ){
                    arr = v['childs'];
                }
            });
            $.each(arr,function(i,v){
                var $domRule = me.container.find('.'+v['name']).find(defConfig.ruleDom),
                    $domExample = me.container.find('.'+v['name']).find(defConfig.exampleDom);
                    $domRule.html(v['rule']);
                    $domExample.html(v['example']);
            });
        }
    };


    //html模板
    var html_shengxiao = [];
    //生肖
    html_shengxiao.push('<div class="ball-list shengxiao">');
    html_shengxiao.push('<div class="game-title"><h3>生肖</h3>');
    html_shengxiao.push('<div class="game-prompt">');
    html_shengxiao.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
    html_shengxiao.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
    html_shengxiao.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_shengxiao.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_shengxiao.push('</div>');
    html_shengxiao.push('</div>');
    html_shengxiao.push('<ul class="ball-list-ul">');
    $.each(defConfig.zodiacsMap,function(i,v){
        var arrBall = [];
        $.each(zoadiacList,function(j,value){
            if(value['zodiacName'] == v['enName']){
                arrBall = value['number'].split(',');
                return;
            }
        });
        html_shengxiao.push('<li data-param="action=ball&row=0&value='+ v['value'] +'&type=shengxiao"><span class="balls">'+v['cnName']+'</span>');

        $.each(arrBall,function(j,value){
            html_shengxiao.push('<span class="ball-group">'+value+'</span>');
        });
        html_shengxiao.push('<span class="ride">x</span><span class="ball-odds">1</span></li>');
    });
    html_shengxiao.push('</ul>');
    html_shengxiao.push('</div>');

    //两面
    var html_half = [];
    html_half.push('<div class="ball-list liangmian">');
    html_half.push('<div class="game-title"><h3>两面</h3>');
    html_half.push('<div class="game-prompt">');
    html_half.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
    html_half.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
    html_half.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_half.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_half.push('</div>');
    html_half.push('</div>');
    html_half.push('<ul class="ball-list-ul">');
    $.each(defConfig.halfMap,function(j,v){
        html_half.push('<li data-param="action=ball&row=1&value='+ v['value'] +'&type=liangmian"><span class="balls">'+ v['cnName'] +'</span><span class="ride">x</span><span class="ball-odds">1</span></li>');
    });
    html_half.push('</ul></div>');

    //色波
    var html_color = [];
    html_color.push('<div class="ball-list saibo">');
    html_color.push('<div class="game-title"><h3>色波</h3>');
    html_color.push('<div class="game-prompt">');
    html_color.push('<a class="rule-example"  href="javascript:;">玩法规则</a>');
    html_color.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
    html_color.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_color.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
    html_color.push('</div>');
    html_color.push('</div>');

    html_color.push('<ul class="ball-list-ul">')

    $.each(defConfig.colorMap,function(i,v){
        html_color.push('<li data-param="action=ball&row=2&value='+ v['value'] +'&type=saibo">'+ '<span class="ball '+ v['enName'] +'">'+ v['cnName']+'</span><span class="ride">x</span><span class="ball-odds">1</span></li>');
    });
    html_color.push('</ul></div>');


    //拼接所有
    var html_all = [];
    html_all.push(html_shengxiao.join(''));
    html_all.push(html_half.join(''));
    html_all.push(html_color.join(''));

    //继承GameMethod
    var Main = host.Class(pros, GameMethod);
    Main.defConfig = defConfig;
    //将实例挂在游戏管理器实例上
    LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
