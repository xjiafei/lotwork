var RootPath = 'js/';

var baseJsList = [
    RootPath+'game/util/jquery-1.9.1.js',
    RootPath+'game/util/jquery.tmpl.js',
    RootPath+'game/util/phoenix.base.js',
    RootPath+'game/util/phoenix.Class.js',
    RootPath+'game/util/phoenix.Event.js',
    RootPath+'game/util/phoenix.util.js',
    RootPath+'game/util/phoenix.cookie.js',
    RootPath+'game/util/phoenix.Tab.js',
    RootPath+'game/util/phoenix.Slider.js',
    RootPath+'game/util/phoenix.Hover.js',
    RootPath+'game/util/phoenix.Select.js',
    RootPath+'game/util/phoenix.Countdown.js',
    RootPath+'game/util/phoenix.Timer.js',
    RootPath+'game/util/phoenix.Mask.js',
    RootPath+'game/util/phoenix.MiniWindow.js',
    RootPath+'game/util/phoenix.Tip.js',
    RootPath+'phoenix.ga.js'
]
baseJsList.concatName = 'base-all.js'; //输出文件名称
baseJsList.distPath = 'js/game'; //输出文件目录

var gameJsList = [
    RootPath+'game/phoenix.Games.js',
    RootPath+'game/phoenix.Game.js',
    RootPath+'game/phoenix.GameMethod.js',
    RootPath+'game/phoenix.GameMessage.js',
    RootPath+'game/phoenix.GameTypes.js',
    RootPath+'game/phoenix.GameStatistics.js',
    RootPath+'game/phoenix.GameOrder.js',
    RootPath+'game/phoenix.GameTrace.js',
    RootPath+'game/phoenix.GameSubmit.js'
]
gameJsList.concatName = 'game-parent.js'; //输出文件名称
gameJsList.distPath = 'js/game'; //输出文件目录

var llsscgameJsList = [
    RootPath+'game/llssc/phoenix.Games.js',
    RootPath+'game/llssc/phoenix.Game.js',
    RootPath+'game/llssc/phoenix.GameMethod.js',
    RootPath+'game/llssc/phoenix.GameMessage.js',
    RootPath+'game/llssc/phoenix.GameTypes.js',
    RootPath+'game/llssc/phoenix.GameStatistics.js',
    RootPath+'game/llssc/phoenix.GameOrder.js',
    RootPath+'game/llssc/phoenix.GameTrace.js',
    RootPath+'game/llssc/phoenix.GameSubmit.js',
]
llsscgameJsList.concatName = 'game-parent.js'; //输出文件名称
llsscgameJsList.distPath = 'js/game/llssc'; //输出文件目录


var lln115gameJsList = [
    RootPath+'game/lln115/phoenix.Games.js',
    RootPath+'game/lln115/phoenix.Game.js',
    RootPath+'game/lln115/phoenix.GameMethod.js',
    RootPath+'game/lln115/phoenix.GameMessage.js',
    RootPath+'game/lln115/phoenix.GameTypes.js',
    RootPath+'game/lln115/phoenix.GameStatistics.js',
    RootPath+'game/lln115/phoenix.GameOrder.js',
    RootPath+'game/lln115/phoenix.GameTrace.js',
    RootPath+'game/lln115/phoenix.GameSubmit.js'
]
lln115gameJsList.concatName = 'game-parent.js'; //输出文件名称
lln115gameJsList.distPath = 'js/game/lln115'; //输出文件目录

var sscJsList = [
    RootPath+'game/ssc/phoenix.Games.SSC.js',
    RootPath+'game/ssc/phoenix.Games.SSC.Danshi.js',
    RootPath+'game/ssc/phoenix.Games.SSC.Message.js',
]
sscJsList.concatName = 'game-ssc-sub.js'; //输出文件名称
sscJsList.distPath = 'js/game/ssc'; //输出文件目录

var sslJsList = [
    RootPath+'game/shssl/ssl/phoenix.Games.SSC.js',
    RootPath+'game/shssl/ssl/phoenix.Games.SSC.Danshi.js',
    RootPath+'game/shssl/ssl/phoenix.Games.SSC.Message.js',
]
sslJsList.concatName = 'game-ssl-sub.js'; //输出文件名称
sslJsList.distPath = 'js/game/shssl/ssl'; //输出文件目录

var n115JsList = [
    RootPath+'game/n115/phoenix.Games.N115.js',
    RootPath+'game/n115/phoenix.Games.N115.Danshi.js',
    RootPath+'game/n115/phoenix.Games.N115.Message.js',
]
n115JsList.concatName = 'game-n115-sub.js'; //输出文件名称
n115JsList.distPath = 'js/game/n115'; //输出文件目录

var fc3dJsList = [
    RootPath+'game/fc3d/phoenix.Games.FC3D.js',
    RootPath+'game/fc3d/phoenix.Games.FC3D.Danshi.js',
    RootPath+'game/fc3d/phoenix.Games.FC3D.Message.js',
]
fc3dJsList.concatName = 'game-fc3d-sub.js'; //输出文件名称
fc3dJsList.distPath = 'js/game/fc3d'; //输出文件目录

var p5JsList = [
    RootPath+'game/p5/phoenix.Games.P5.js',
    RootPath+'game/p5/phoenix.Games.P5.Danshi.js',
    RootPath+'game/p5/phoenix.Games.p5.GameTypes.js',
    RootPath+'game/p5/phoenix.Games.P5.Message.js',
]
p5JsList.concatName = 'game-p5-sub.js'; //输出文件名称
p5JsList.distPath = 'js/game/p5'; //输出文件目录

var ssqJsList = [
    RootPath+'game/ssq/phoenix.Games.SSQ.js',
    RootPath+'game/ssq/phoenix.Games.SSQ.Danshi.js',
    RootPath+'game/ssq/phoenix.Games.SSQ.Message.js',
]
ssqJsList.concatName = 'game-ssq-sub.js'; //输出文件名称
ssqJsList.distPath = 'js/game/ssq'; //输出文件目录

var bjkl8JsList = [
    RootPath+'game/bjkl8/phoenix.Games.BJKL8.js',
    RootPath+'game/bjkl8/phoenix.Games.BJKL8.Message.js',
    RootPath+'game/bjkl8/phoenix.Games.BJKL8.Danshi.js',
    RootPath+'game/bjkl8/phoenix.Games.BJKL8.Renxuan.js',
    RootPath+'game/bjkl8/phoenix.Games.BJKL8.Charts.js',
]
bjkl8JsList.concatName = 'game-bjkl8-sub.js'; //输出文件名称
bjkl8JsList.distPath = 'js/game/bjkl8'; //输出文件目录

var k3JsList = [
    RootPath+'game/k3/phoenix.Games.K3.js',
    RootPath+'game/k3/phoenix.Games.K3.Message.js',
    RootPath+'game/k3/phoenix.Games.K3.Danshi.js',
]
k3JsList.concatName = 'game-k3-sub.js'; //输出文件名称
k3JsList.distPath = 'js/game/k3'; //输出文件目录


var ffcJsList = [
    RootPath+'game/ffc/phoenix.GameFFCTypes.js',
    RootPath+'game/ffc/phoenix.Games.FFC.js',
    RootPath+'game/ffc/phoenix.Games.FFC.Message.js',
    RootPath+'game/ffc/phoenix.Games.FFC.Danshi.js',
]
ffcJsList.concatName = 'game-ffc-sub.js'; //输出文件名称
ffcJsList.distPath = 'js/game/ffc'; //输出文件目录

var llsscJsList = [
    RootPath+'game/llssc/llssc/phoenix.Games.LLSSC.js',
    RootPath+'game/llssc/llssc/phoenix.Games.LLSSC.Message.js',
    RootPath+'game/llssc/llssc/phoenix.Games.LLSSC.Video.js',
    RootPath+'game/llssc/llssc/phoenix.Games.LLSSC.Danshi.js',
]
llsscJsList.concatName = 'game-llssc-sub.js'; //输出文件名称
llsscJsList.distPath = 'js/game/llssc/llssc'; //输出文件目录

var lln115JsList = [
    RootPath+'game/lln115/lln115/phoenix.Games.LLN115.js',
    RootPath+'game/lln115/lln115/phoenix.Games.LLN115.Message.js',
    RootPath+'game/lln115/lln115/phoenix.Games.LLN115.Video.js',
    RootPath+'game/lln115/lln115/phoenix.Games.LLN115.Danshi.js',
]
lln115JsList.concatName = 'game-lln115-sub.js'; //输出文件名称
lln115JsList.distPath = 'js/game/lln115/lln115'; //输出文件目录

var gulp = require('gulp'),
    //minifyHTML = require('gulp-minify-html'),
    minifycss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat'),
    rename = require('gulp-rename'),
    del = require('del');

//压缩合并css
gulp.task('minifycss', function() {
    return gulp.src('css')//文件源
        .pipe(minifycss())//执行压缩
        .pipe(concat("main.css"))//执行合并
        .pipe(rename({suffix: '.min'}))//重命名
        .pipe(gulp.dest('destcss'))//输出文件夹
});

gulp.task('minifyBaseCss', function() {
    return gulp.src('images/common/base.css')//文件源
        .pipe(minifycss())//执行压缩
        //.pipe(concat("main.css"))//执行合并
        .pipe(rename({suffix: '.min'}))//重命名
        .pipe(gulp.dest('images/common/'))//输出文件夹
});

gulp.task('minifyGameCss', function() {
    return gulp.src('images/game/game.css')//文件源
        .pipe(minifycss())//执行压缩
        //.pipe(concat("main.css"))//执行合并
        .pipe(rename({suffix: '.min'}))//重命名
        .pipe(gulp.dest('images/game/'))//输出文件夹
});

//压缩合并js
gulp.task('minifyjs', function() {
    return gulp.src('src')
        .pipe(concat('main.js')) //合并所有js到main.js 
        .pipe(gulp.dest('dest')) //输出main.js到文件夹
        .pipe(rename({suffix: '.min'}))//rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest('destjs')); //输出
});

gulp.task('baseJs', function() {
    return gulp.src(baseJsList)
        .pipe(concat(baseJsList.concatName)) //合并所有js到base-all.js 
        .pipe(gulp.dest(baseJsList.distPath)) //输出base-all.js到文件夹
        .pipe(rename({suffix: '.min'}))//rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(baseJsList.distPath)); //输出
});

gulp.task('gameJs', function() {
    return gulp.src(gameJsList)
        .pipe(concat(gameJsList.concatName)) //合并所有js到game-all.js
        .pipe(gulp.dest(gameJsList.distPath)) //输出game-all.js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(gameJsList.distPath)); //输出
});

gulp.task('llsscgameJs', function() {
    return gulp.src(llsscgameJsList)
        .pipe(concat(llsscgameJsList.concatName)) //合并所有js
        .pipe(gulp.dest(llsscgameJsList.distPath)) //输出js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(llsscgameJsList.distPath)); //输出
});
gulp.task('lln115gameJs', function() {
    return gulp.src(lln115gameJsList)
        .pipe(concat(lln115gameJsList.concatName)) //合并所有js
        .pipe(gulp.dest(lln115gameJsList.distPath)) //输出js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(lln115gameJsList.distPath)); //输出
});

gulp.task('gameSscJs', function() {
    return gulp.src(sscJsList)
        .pipe(concat(sscJsList.concatName)) //合并所有js到game-all.js
        .pipe(gulp.dest(sscJsList.distPath)) //输出game-all.js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(sscJsList.distPath)); //输出
});

gulp.task('gameSslJs', function() {
    return gulp.src(sslJsList)
        .pipe(concat(sslJsList.concatName)) //合并所有js到game-all.js
        .pipe(gulp.dest(sslJsList.distPath)) //输出game-all.js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(sslJsList.distPath)); //输出
});

gulp.task('gameN115Js', function() {
    return gulp.src(n115JsList)
        .pipe(concat(n115JsList.concatName)) //合并所有js到game-all.js
        .pipe(gulp.dest(n115JsList.distPath)) //输出game-all.js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(n115JsList.distPath)); //输出
});

gulp.task('gameFC3DJs', function() {
    return gulp.src(fc3dJsList)
        .pipe(concat(fc3dJsList.concatName)) //合并福彩3d js到fc3d-sub.js
        .pipe(gulp.dest(fc3dJsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(fc3dJsList.distPath)); //输出
});
gulp.task('gameP5Js', function() {
    return gulp.src(p5JsList)
        .pipe(concat(p5JsList.concatName)) //合并排列5 js到p2-sub.js
        .pipe(gulp.dest(p5JsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(p5JsList.distPath)); //输出
});
gulp.task('gameSSQJs', function() {
    return gulp.src(ssqJsList)
        .pipe(concat(ssqJsList.concatName)) //合并双色球 js到ssq-sub.js
        .pipe(gulp.dest(ssqJsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(ssqJsList.distPath)); //输出
});
gulp.task('gameBJKL8Js', function() {
    return gulp.src(bjkl8JsList)
        .pipe(concat(bjkl8JsList.concatName)) //合并北京快乐8 js到bjkl8-sub.js
        .pipe(gulp.dest(bjkl8JsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(bjkl8JsList.distPath)); //输出
});
gulp.task('gameK3Js', function() {
    return gulp.src(k3JsList)
        .pipe(concat(k3JsList.concatName)) //合并快三 js到k3-sub.js
        .pipe(gulp.dest(k3JsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(k3JsList.distPath)); //输出
});

gulp.task('ffcJs', function() {
    return gulp.src(ffcJsList)
        .pipe(concat(ffcJsList.concatName)) //合并快三 js到k3-sub.js
        .pipe(gulp.dest(ffcJsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(ffcJsList.distPath)); //输出
});

gulp.task('llsscJs', function() {
    return gulp.src(llsscJsList)
        .pipe(concat(llsscJsList.concatName)) //合并快三 js到k3-sub.js
        .pipe(gulp.dest(llsscJsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(llsscJsList.distPath)); //输出
});
gulp.task('lln115Js', function() {
    return gulp.src(lln115JsList)
        .pipe(concat(lln115JsList.concatName)) //合并快三 js到k3-sub.js
        .pipe(gulp.dest(lln115JsList.distPath)) //输出压缩js到文件夹
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(lln115JsList.distPath)); //输出
});

gulp.task('default',['minifyBaseCss','minifyGameCss','minifyjs','baseJs'
					,'gameJs','llsscgameJs','lln115gameJs','gameSscJs','gameSslJs'
					,'gameN115Js','gameFC3DJs','gameP5Js','gameSSQJs','gameBJKL8Js'
					,'gameK3Js','ffcJs','llsscJs','lln115Js']);
