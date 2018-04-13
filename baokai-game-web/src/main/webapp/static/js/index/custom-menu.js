/**
 * Created by user on 15/11/26.
 * 从服务器获取菜单列表
 * {
 * "menu":
 * "ssl1,ssl2,ssl3,ssl4,ssl5,ssl6,ssl7"
 * }
 */
(function($){
    var customMenu = {
        $popBox : $('#lotteryMenu'),
        $currentBox : $('#currentBox'),
        $popConfirm : $('#selectConfirm'),
        $popCancle : $('#selectCancle'),
        $menuTitle : $('#menu-main'),
        baseUrl: gameserver + '/gameBet/', // 游戏链接地址前缀
        baseImg: global_path_url + '/images/indexv2/lottery/',  // 自定义菜单图片地址前缀
		
        modelMenu: [],
        nowEditN: 0,
        //初始化
        init: function(){
            this.setClickBind();
			//this.setpic8ClickBind();
            this.serviceMenu();
        },
        //从服务器获取用户菜单字符串
        serviceMenu: function(){
			
            var _this = this;
		
            var url = '/index/lotterymenu',
                 data = '';
                _this.request(data,function(res){
				    
				});
			 
		//	 _this.plusClickBind();
			
        },
        //提交用户菜单字符串到服务器
        uploadMenu: function(){
            var _this = this;
            var url = '/index/lotterymenu',
            data = {};
            data.menu = '';
            var arr = [];
            var $all = this.$currentBox.find('a');
            $all.each(function(n,value){
				
                arr.push($(this).data('id'));
            });
             data.menu = arr.join(',');
			 _this.send(data.menu,function(res){
            });
			 
        },
        modelToMenu: function(arr){
			var _this = this;
            $.each(arr,function(n,value){
                _this.addNewlink(n,value);
                _this.addNewImg(n,value);
            });
        },
        updateMenu: function(){
            var _this = this;
            var $linkList = this.$currentBox.find('a');
            $linkList.each(function(n,ele){
                _this.modelMenu[n] = $(ele).data('id');
            });
        },
        //查找当前执行添加位置
        searchIndex: function(){
            var $add = this.$currentBox.find('.add-new').length;
            if(!$add) return 0;
            return (this.$currentBox.find('.add-new').index());
        },
        //设置按钮 点击
        setClickBind: function(){
			
            var _this = this;
            var $btn = this.$currentBox.find('.lottery-setting');

            $btn.click(function(){
                _this.startEdit(_this.searchIndex());
            });
        },
		setpic8ClickBind: function(){
            var _this = this;
            var $btn1 = this.$currentBox.find('.lottery-pic8');
            
            $btn1.click(function(){
                _this.startEdit(_this.searchIndex());
            });
        },
        //添加按钮 点击
        plusClickBind: function(){
			
			var _this = this;
            var $add = this.$currentBox.find('.add-new');
            $add.on('click',function(){
				
                _this.startEdit(_this.searchIndex());
            });
        },
        //缩略菜单项 初始化
        menuInit: function(){
			
            var _this = this;
            var $li = this.$popBox.find('.menu-con li');
            $li.on('click',function(){
                if(!$(this).hasClass('active')){
                    var id = $(this).data('menuid');
                    $(this).addClass('active');
                    _this.addNewImg(_this.nowEditN,id);
                    _this.updateMenu();
                    _this.updateActive();
                    _this.changeEdit(_this.nowEditN+1);
                }
            });
            this.$popCancle.on('click',function(){
                //_this.modelToMenu(_this.modelMenu);
				//_this.endEdit();
				data = '';
                _this.request(data,function(res){
				    
				});
                _this.closeMenu();
				

            });
            this.$popConfirm.on('click',function(){
                _this.modelToMenu(_this.modelMenu);
				_this.endEdit();
                _this.uploadMenu();
	            _this.closeMenu();
            });
			
        },
        //添加新自定义链接
        addNewlink: function(n,id){
            var _this = this;
            var $now = this.$currentBox.find('a').eq(n);
            if(id != ''){
                $now.attr('href',_this.baseUrl+id);
            }
        },
        //添加图片
        addNewImg: function(n,id){
            var _this = this;
            var $now = this.$currentBox.find('a').eq(n);
            if(id != ''){
                var newImg = $("<img/>").attr('src', _this.baseImg + id + '.png');
				$now.html('').removeClass('add-new').data('id',id).append(newImg);
            }else {
                $now.data('id','');
            }
        },
        //开始编辑菜单
        startEdit: function(index){
			
            var _this = this;
            var $all = this.$currentBox.find('a');
            $all.attr('href',"javascript:;");
            $all.on('click',function(){
                var nowNumber = $(this).index();
                _this.changeEdit(nowNumber);
            });
            this.changeEdit(_this.searchIndex());
		    
            this.popMenu();
			
        },
        //改变菜单操作对象
        changeEdit: function(n){
			var _this = this;
            if(n>=8) n = 0;
            this.nowEditN = n;
            this.$popBox.attr('class','lottery-menu').addClass('lm-pos'+ Number(n+1));
            this.$currentBox.find('a').removeClass('active').eq(n).addClass('active');
			var bottomScroll = $(document).height() - $(window).height();
            if(n > 3) {
                if($('body').scrollTop() < bottomScroll) {
                    $('body').animate({
                        scrollTop: bottomScroll
                    })
                }
            }else {
                if($('body').scrollTop() > (bottomScroll-200)) {
                    $('body').animate({
                        scrollTop: bottomScroll-200
                    })
                }
            }
        },
        endEdit: function(){
            var $all = this.$currentBox.find('a');
			$all.off('click');
			//this.plusClickBind();
        },
        updateActive: function(){
			var _this = this;
			var $li = this.$popBox.find('.menu-con li');
			//$li.removeClass('active');
			$li.removeClass('active').each(function(n,ele){
		        var $thisli = $(ele);
			    var menuid = $(ele).data('menuid');
			    $.each(_this.modelMenu,function(index,value){
				    if(menuid == value) {
						 $thisli.addClass('active');
                        return;
                    }
                });
            });
        },

        //弹出选择菜单
        popMenu: function(){
			
            var _this = this;
            this.menuInit();
            this.updateActive();

            this.$popBox.fadeIn();
        },
        //关闭选择菜单
        closeMenu: function(){
            var _this = this;
            var $li = this.$popBox.find('.menu-con li');
            this.$currentBox.find('a').removeClass('active');
            this.$popBox.fadeOut(function(){
                _this.$popBox.attr('class','lottery-menu');
            });
        },
        /**
         * ajax请求
         * @param url
         * @param data
         * @param succ
         * @param callback
         */
        request: function(data,callback){
			
			var _this = this;
			
			var menuarr = new Array(8)
			
			$.ajax( {
                url: '/index/lotterymenuquery',// 跳转到 action
                data: '',
                type:'post',
                dataType:'json',
                
				success:function(data) {
					
					
                   
				    var data_tmp = JSON.parse(data['data']);
					
					if(data_tmp == false) 
					{
					
						$('#game0').attr('href',_this.baseUrl+'cqssc');
						$('#game1').attr('href',_this.baseUrl+'slmmc');
				        $('#game2').attr('href',_this.baseUrl+'jlffc');
				        $('#game3').attr('href',_this.baseUrl+'jxssc'); 
				        $('#game4').attr('href',_this.baseUrl+'sd115');
				        $('#game5').attr('href',_this.baseUrl+'jsk3');
				        $('#game6').attr('href',_this.baseUrl+'fc3d');
						
						var newImg0= $("<img/>").attr('src', _this.baseImg + 'cqssc.png');
				        var newImg1= $("<img/>").attr('src', _this.baseImg + 'slmmc.png');
				        var newImg2= $("<img/>").attr('src', _this.baseImg + 'jlffc.png');
				        var newImg3= $("<img/>").attr('src', _this.baseImg + 'jxssc.png');
				        var newImg4= $("<img/>").attr('src', _this.baseImg + 'sd115.png');
				        var newImg5= $("<img/>").attr('src', _this.baseImg + 'jsk3.png');
				        var newImg6= $("<img/>").attr('src', _this.baseImg + 'fc3d.png');
				        //var newImg7= $("<img/>").attr('src', _this.baseImg + data_tmp['game7'] + '.png');
					
				        $('#game0').html('').removeClass('add-new').data('id','cqssc').append(newImg0);
					    $('#game1').html('').removeClass('add-new').data('id','slmmc').append(newImg1);
					    $('#game2').html('').removeClass('add-new').data('id','jlffc').append(newImg2);
					    $('#game3').html('').removeClass('add-new').data('id','jxssc').append(newImg3);
					    $('#game4').html('').removeClass('add-new').data('id','sd115').append(newImg4);
					    $('#game5').html('').removeClass('add-new').data('id','jsk3').append(newImg5);
					    $('#game6').html('').removeClass('add-new').data('id','fc3d').append(newImg6);
					
					    //$('#game7').addClass('add-new');
						$('#game7').html('').addClass('add-new');
						//$('#game7').html('').addClass("add-new");
						
						menuarr [0] = "cqssc"
                        menuarr [1] = "slmmc" 
                        menuarr [2] = "jlffc"
                        menuarr [3] = "jxssc"
                        menuarr [4] = "sd115"
				        menuarr [5] = "jsk3"
                        menuarr [6] = "fc3d"
						
						_this.setpic8ClickBind();
						
						
					}
					
				
				    else
					{
						
				       
					    $('#game0').attr('href',_this.baseUrl+data_tmp['game0']);
				        $('#game1').attr('href',_this.baseUrl+data_tmp['game1']);
				        $('#game2').attr('href',_this.baseUrl+data_tmp['game2']);
				        $('#game3').attr('href',_this.baseUrl+data_tmp['game3']); 
				        $('#game4').attr('href',_this.baseUrl+data_tmp['game4']);
				        $('#game5').attr('href',_this.baseUrl+data_tmp['game5']);
				        $('#game6').attr('href',_this.baseUrl+data_tmp['game6']);
						
						var newImg0= $("<img/>").attr('src', _this.baseImg + data_tmp['game0'] + '.png');
				        var newImg1= $("<img/>").attr('src', _this.baseImg + data_tmp['game1'] + '.png');
				        var newImg2= $("<img/>").attr('src', _this.baseImg + data_tmp['game2'] + '.png');
				        var newImg3= $("<img/>").attr('src', _this.baseImg + data_tmp['game3'] + '.png');
				        var newImg4= $("<img/>").attr('src', _this.baseImg + data_tmp['game4'] + '.png');
				        var newImg5= $("<img/>").attr('src', _this.baseImg + data_tmp['game5'] + '.png');
				        var newImg6= $("<img/>").attr('src', _this.baseImg + data_tmp['game6'] + '.png');
						//this.setpic8ClickBind();
				        
				        $('#game0').html('').removeClass('add-new').data('id',data_tmp['game0']).append(newImg0);
					    $('#game1').html('').removeClass('add-new').data('id',data_tmp['game1']).append(newImg1);
					    $('#game2').html('').removeClass('add-new').data('id',data_tmp['game2']).append(newImg2);
					    $('#game3').html('').removeClass('add-new').data('id',data_tmp['game3']).append(newImg3);
					    $('#game4').html('').removeClass('add-new').data('id',data_tmp['game4']).append(newImg4);
					    $('#game5').html('').removeClass('add-new').data('id',data_tmp['game5']).append(newImg5);
					    $('#game6').html('').removeClass('add-new').data('id',data_tmp['game6']).append(newImg6);
					   // $('#game7').html('').removeClass('add-new').data('id',data_tmp['game7']).append(newImg7);
						
						if(data_tmp['game7'])
						{
							$('#game7').attr('href',_this.baseUrl+data_tmp['game7']);
							var newImg7= $("<img/>").attr('src', _this.baseImg + data_tmp['game7'] + '.png');
							$('#game7').html('').removeClass('add-new').data('id',data_tmp['game7']).append(newImg7);
						}
						else
						{
							 $('#game7').html('').addClass('add-new');
							_this.setpic8ClickBind();
						}
						
						menuarr [0] = data_tmp['game0']
                        menuarr [1] = data_tmp['game1'] 
                        menuarr [2] = data_tmp['game2']
                        menuarr [3] = data_tmp['game3']
                        menuarr [4] = data_tmp['game4']
				        menuarr [5] = data_tmp['game5']
                        menuarr [6] = data_tmp['game6']
						menuarr [7] = data_tmp['game7']
					}
				  
				    _this.modelMenu = menuarr;
					
                    //_this.modelToMenu(_this.modelMenu);
					
				},
				
				error:function(e) {
                    alert('異常');
                },
                
            });
        },
		
		send: function(data,succ,callback){
			
			var _this = this;
			
			menudata = data;
			
			$.ajax( {
                url: '/index/lotterymenusend',// 跳转到 action
                data: {menudata:menudata},
                type:'post',
                dataType:'json',
                
				success:function(data) {
					
					var data_tmp = JSON.parse(data['data']);
					
					if(data_tmp == false) 
					
					{
						
						$('#game0').attr('href',_this.baseUrl+'cqssc');
						$('#game1').attr('href',_this.baseUrl+'slmmc');
				        $('#game2').attr('href',_this.baseUrl+'jlffc');
				        $('#game3').attr('href',_this.baseUrl+'jxssc'); 
				        $('#game4').attr('href',_this.baseUrl+'sd115');
				        $('#game5').attr('href',_this.baseUrl+'jsk3');
				        $('#game6').attr('href',_this.baseUrl+'fc3d');
						
						var newImg0= $("<img/>").attr('src', _this.baseImg + 'cqssc.png');
				        var newImg1= $("<img/>").attr('src', _this.baseImg + 'slmmc.png');
				        var newImg2= $("<img/>").attr('src', _this.baseImg + 'jlffc.png');
				        var newImg3= $("<img/>").attr('src', _this.baseImg + 'jxssc.png');
				        var newImg4= $("<img/>").attr('src', _this.baseImg + 'sd115.png');
				        var newImg5= $("<img/>").attr('src', _this.baseImg + 'jsk3.png');
				        var newImg6= $("<img/>").attr('src', _this.baseImg + 'fc3d.png');
				        //var newImg7= $("<img/>").attr('src', _this.baseImg + data_tmp['game7'] + '.png');
					
					
				        $('#game0').html('').removeClass('add-new').data('id','cqssc').append(newImg0);
					    $('#game1').html('').removeClass('add-new').data('id','slmmc').append(newImg1);
					    $('#game2').html('').removeClass('add-new').data('id','jlffc').append(newImg2);
					    $('#game3').html('').removeClass('add-new').data('id','jxssc').append(newImg3);
					    $('#game4').html('').removeClass('add-new').data('id','sd115').append(newImg4);
					    $('#game5').html('').removeClass('add-new').data('id','jsk3').append(newImg5);
					    $('#game6').html('').removeClass('add-new').data('id','fc3d').append(newImg6);
					    $('#game7').html('').addClass('add-new');
						
						
						
						_this.setpic8ClickBind();
					}
					
				
				    else
					{
						
				        
						$('#game0').attr('href',_this.baseUrl+data_tmp['game0']);
				        $('#game1').attr('href',_this.baseUrl+data_tmp['game1']);
				        $('#game2').attr('href',_this.baseUrl+data_tmp['game2']);
				        $('#game3').attr('href',_this.baseUrl+data_tmp['game3']); 
				        $('#game4').attr('href',_this.baseUrl+data_tmp['game4']);
				        $('#game5').attr('href',_this.baseUrl+data_tmp['game5']);
				        $('#game6').attr('href',_this.baseUrl+data_tmp['game6']);
					   // $('#game7').attr('href',_this.baseUrl+data_tmp['game7']);
						
						
				        var newImg0= $("<img/>").attr('src', _this.baseImg + data_tmp['game0'] + '.png');
				        var newImg1= $("<img/>").attr('src', _this.baseImg + data_tmp['game1'] + '.png');
				        var newImg2= $("<img/>").attr('src', _this.baseImg + data_tmp['game2'] + '.png');
				        var newImg3= $("<img/>").attr('src', _this.baseImg + data_tmp['game3'] + '.png');
				        var newImg4= $("<img/>").attr('src', _this.baseImg + data_tmp['game4'] + '.png');
				        var newImg5= $("<img/>").attr('src', _this.baseImg + data_tmp['game5'] + '.png');
				        var newImg6= $("<img/>").attr('src', _this.baseImg + data_tmp['game6'] + '.png');
				        //var newImg7= $("<img/>").attr('src', _this.baseImg + data_tmp['game7'] + '.png');
					
					
				        $('#game0').html('').removeClass('add-new').data('id',data_tmp['game0']).append(newImg0);
					    $('#game1').html('').removeClass('add-new').data('id',data_tmp['game1']).append(newImg1);
					    $('#game2').html('').removeClass('add-new').data('id',data_tmp['game2']).append(newImg2);
					    $('#game3').html('').removeClass('add-new').data('id',data_tmp['game3']).append(newImg3);
					    $('#game4').html('').removeClass('add-new').data('id',data_tmp['game4']).append(newImg4);
					    $('#game5').html('').removeClass('add-new').data('id',data_tmp['game5']).append(newImg5);
					    $('#game6').html('').removeClass('add-new').data('id',data_tmp['game6']).append(newImg6);
					    //$('#game7').html('').removeClass('add-new').data('id',data_tmp['game7']).append(newImg7);
						
						
						if(data_tmp['game7'])
						{
						
							$('#game7').attr('href',_this.baseUrl+data_tmp['game7']);
							var newImg7= $("<img/>").attr('src', _this.baseImg + data_tmp['game7'] + '.png');
							$('#game7').html('').removeClass('add-new').data('id',data_tmp['game7']).append(newImg7);
						}
						else
						{
							
							$('#game7').html('').addClass('add-new');
							_this.setpic8ClickBind();
						}
						
					}
				  
                },
				
				error:function(e) {
                    alert('異常');
                },
                
            });
        },
		
	
	}
    
	customMenu.init();
	
})(jQuery)


/*$('#selectConfirm').click(function() {
	
	var _this = this;
	
	var id = 'test'
    $.ajax( {
        url: '/index/lotterymenutest',// 跳转到 action
        data: $('#menudefind').serialize(),
        type:'post',
        dataType:'json',
        success:function(data) {
                  
			var data_tmp = JSON.parse(data['data']);
				   
		    $('#game5').attr('href',_this.baseUrl+data_tmp['game5']);
		    $('#game6').attr('href',_this.baseUrl+data_tmp['game6']);
		    $('#game7').attr('href',_this.baseUrl+data_tmp['game7']);
			$('#game8').attr('href',_this.baseUrl+data_tmp['game8']);
        },
				
		error:function(e) {
            alert('異常');
        },
                
    });
	
});*/
