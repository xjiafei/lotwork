/**
 * Created by user on 16/1/28.
 */

$(function(){
    var imgLoadNum = 0;
    //load动画
    var loadProcess = function() {
        var imgList = ['images/1950.png', 'images/1960.png', 'images/1980.png', 'images/allbg.jpg', 'images/circle.png', 'images/circlebg.png', 'images/light.png', 'images/logo.png', 'images/planet.png', 'images/planet2.png', 'images/slogan.png', 'images/title.png'];
        for (var i = 0; i < imgList.length; i++) {
            (function(s) {
                setTimeout(function() {
                    loadImg(imgList[s], loadAnimate, imgList.length);
                }, 200 * s);
            })(i);
        }
    };
    var loadAnimate = function(num) {
        imgLoadNum++;
        if (num == imgLoadNum) {
            //图片静态资源加载完毕
            startAnimate();
        }
    };

    var loadImg = function(src, callback, num) {
        var img = new Image();
        img.src = src;
        if (img.complete) {
            callback(num);
            return;
        }
        function get() {
            if (img.complete) {
                callback(num);
                //循环求值
                if (getTimer) {
                    clearInterval(getTimer);
                    getTimer = null;
                }
            } else if (img.error) {
                callback();
            }
        }

        var getTimer = setInterval(get, 100);
    };

    var startAnimate = function(){
        $('.loading').hide();
        $('.space').show().addClass('active');
    };

    //load动画
    loadProcess();

    var  mouseTimer = null,
         mouseTimer2 = null,
         $space = $('.space'),
        $container = $('.container'),
        sHeight = $space.height();
    $space.bind('mousewheel', function(event, delta, deltaX, deltaY) {
        if(!mouseTimer){
            mouseTimer = setTimeout(function(){
                if(delta <= -1){
                    $container.show();
                    $space.animate({'marginTop': -sHeight},function(){
                        $(this).hide();
                    });
                }
                clearTimeout(mouseTimer);
                mouseTimer = null;
            },1000);
        }
    });
    $container.bind('mousewheel', function(event, delta, deltaX, deltaY) {
        if(!mouseTimer2){
            mouseTimer2 = setTimeout(function(){
                if(delta >= 1){
                    if($('body').scrollTop() <= 10) {

                        $space.animate({'marginTop': 0},function(){
                            $container.hide();
                        }).show();
                    }
                }
                clearTimeout(mouseTimer2);
                mouseTimer2 = null;
            },1000);
        }
    });
});
