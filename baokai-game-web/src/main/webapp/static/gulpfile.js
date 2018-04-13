var RootPath = 'js/';

var baseJsList = [
    RootPath+'game/game-parent.js',
    RootPath+'game/base-all.js',
]
baseJsList.concatName = 'base-all.js'; //输出文件名称
baseJsList.distPath = 'js/game'; //输出文件目录


var gulp = require('gulp'),
    //minifyHTML = require('gulp-minify-html'),
    minifycss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat'),
    rename = require('gulp-rename'),
    del = require('del');


gulp.task('minifyGameCss', function() {
    return gulp.src('images/game/game.css')//文件源
        .pipe(minifycss())//执行压缩
        //.pipe(concat("main.css"))//执行合并
        .pipe(rename({suffix: '.min'}))//重命名
        .pipe(gulp.dest('images/game/'))//输出文件夹
});


gulp.task('baseJs', function() {
    return gulp.src(baseJsList)
        .pipe(rename({suffix: '.min'}))//rename压缩后的文件名
        .pipe(uglify()) //压缩
        .pipe(gulp.dest(baseJsList.distPath)); //输出
});


gulp.task('default',['minifyGameCss','baseJs']);
