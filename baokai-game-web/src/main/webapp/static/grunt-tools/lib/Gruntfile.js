'use strict';

module.exports = function(grunt) {

  grunt.initConfig({

    //路径配置
    pkg: grunt.file.readJSON('../toolsCMD/config.json'),
    //js文件配置
    jsFileConfig: grunt.file.readJSON('../config/JS-file.json'),
    //CSS文件配置
    cssFileConfig: grunt.file.readJSON('../config/CSS-file.json'),
    /*文件合并功能
    该功能和文件压缩功能重复暂时不使用
    concat: {},*/

    //自动补全功能
   /* autoprefixer: {
      options: {
        // Task-specific options go here.
      },
      multiple_files: {
        expand: true,
        flatten: true,
        src: '<%= pkg.cssReleaseDir %>/*.css', // -> src/css/file1.css, src/css/file2.css
        dist: '<%= pkg.cssReleaseDir %>'
      }
    },*/

    //文件压缩
    uglify: {
      options: {
        //启用代码地图功能
        //支持调试
        //MAP链接会引入JS文件中
        sourceMap: false,
        sourceMapIncludeSources: false,
        mangle: {
          except: ['jQuery']
        }
      },
      multiple_files: {
        files: '<%= jsFileConfig %>'
      },
      //压缩所有的子类玩法
      buildall: {
          files: [{
              expand:true,
              cwd:'<%= pkg.developDir %>js/game',//js目录下
              src:'*/*.js',//所有js文件
              dest: '<%= pkg.jsReleaseDir %>/game'//输出到此目录下
          }]
      }
    },

    //css压缩合并
    cssmin: {
      combine: {
        options: {
          //是否清除注释
          //1保留第一行
          //0完全清除
          keepSpecialComments: 0
        },
        multiple_files: {
          files: '<%= cssFileConfig>'
        }
      }
    },

    //单元测试
    // jshint: {
    //   files: ['Gruntfile.js', 'src/**/*.js', 'test/**/*.js'],
    //   options: {
    //     //这里是覆盖JSHint默认配置的选项
    //     globals: {
    //       jQuery: true,
    //       console: true,
    //       module: true,
    //       document: true
    //     }
    //   }
    // },

    //监测文件
    watch: {
      all: {
        files:['<%= pkg.developDir %>js/**/*.js', '<%= pkg.developDir %>images/**/*.css', '<%= pkg.developDir %>css/**/*.css'],
        tasks: ['newer:uglify','newer:cssmin']
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-autoprefixer');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-newer');

  //执行任务
  grunt.registerTask('default', ['newer:cssmin','newer:uglify','watch']);
};