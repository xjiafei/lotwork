var RootPath = 'js/';
 
var gameJsList = [ 
]
gameJsList.srcFile = 'js/game/game-parent.js'; //输出文件名称
gameJsList.distPath = 'js/game'; //输出文件目录

var llsscgameJsList = [ 
]
llsscgameJsList.srcFile = 'js/game/llssc/game-parent.js'; //输出文件名称
llsscgameJsList.distPath = 'js/game/llssc'; //输出文件目录


var lln115gameJsList = [ 
]
lln115gameJsList.srcFile = 'js/game/lln115/game-parent.js'; //输出文件名称
lln115gameJsList.distPath = 'js/game/lln115'; //输出文件目录

var gulp = require('gulp'),
    //minifyHTML = require('gulp-minify-html'),
    minifycss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat'),
    rename = require('gulp-rename'),
    del = require('del');

gulp.task('llsscgameJs', function() {
    return gulp.src(llsscgameJsList.srcFile) 		
        .pipe(uglify()) //压缩
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(gulp.dest(llsscgameJsList.distPath)); //输出
}); 
gulp.task('gameJs', function() {
   return gulp.src(gameJsList.srcFile) 		
        .pipe(uglify()) //压缩
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(gulp.dest(gameJsList.distPath)); //输出
}); 
gulp.task('lln115gameJs', function() {
    return gulp.src(lln115gameJsList.srcFile) 		
        .pipe(uglify()) //压缩
        .pipe(rename({suffix: '.min'})) //rename压缩后的文件名
        .pipe(gulp.dest(lln115gameJsList.distPath)); //输出
}); 


gulp.task('default',['llsscgameJs','gameJs','lln115gameJs']);
