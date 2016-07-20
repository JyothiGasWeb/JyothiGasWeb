'use strict';
module.exports = function(grunt) {
    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);

    var appConfig = {
        src: require('./bower.json').appPath || 'app',
        dist: 'dist'
    };

    grunt.initConfig({

        jyothiGas: appConfig,
        less: {
            compile: {
                files: {
                    'app/styles/index.css': 'app/less/index.less',
                }
            }
        },

        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp',
                        '<%= jyothiGas.dist %>/{,*/}*',
                        '!<%= jyothiGas.dist %>/.git{,*/}*'
                    ]
                }]
            },
            server: '.tmp'
        },
        bowercopy: {
            options: {
                runbower: false,
                srcPrefix: 'bower_components'
            },
            libs: {
                options: {
                    destPrefix: '<%= jyothiGas.src %>/scripts/libs'
                },
                files: {
                    'jquery.js': 'jquery/dist/jquery.min.js',
                    'angular.js': 'angular/angular.js',
                    'angular-resource.js': 'angular-resource/angular-resource.js',
                    'angular-ui-router.js': 'angular-ui-router/release/angular-ui-router.js',
                    'angular-messages.js': 'angular-messages/angular-messages.js',
                    'angular-animate.js': 'angular-animate/angular-animate.js',
                    'angular-aria.js': 'angular-aria/angular-aria.js',
                    'angular-material.js': 'angular-material/angular-material.js',
                    'loading-bar.min.js': 'angular-loading-bar/build/loading-bar.min.js',
                    'angular-cookies.js': 'angular-cookies/angular-cookies.js',
                    'angular-local-storage.min.js':'angular-local-storage/dist/angular-local-storage.min.js',
                    'angular-material-calendar.js':'material-calendar/dist/angular-material-calendar.js',
                    'angular-sanitize.js':'angular-sanitize/angular-sanitize.js',
                    'ng-file-upload.min.js':'ng-file-upload/ng-file-upload.min.js',
                    'ng-file-upload-shim.min.js':'ng-file-upload/ng-file-upload-shim.min.js'

                    //NOTE: bower_component styles are not copied since we may custom less implementations of them with variables
                    //which means we need to include them by hand in styles/plugins
                }
            },
            pluginStyles: {
                options: {
                    destPrefix: '<%= jyothiGas.src %>/styles/libs'
                },
                files: {
                    'angular-material.css': 'angular-material/angular-material.css',
                    'loading-bar.min.css': 'angular-loading-bar/build/loading-bar.min.css'

                }
            }

        },

        connect: {
            options: {
                port: 9090,
                hostname: 'localhost'
            },
            localServer: {
                options: {
                    open: true
                }
            }
        },


        watch: {
            less: {
                files: ['app/less/*.less'],
                tasks: ['less']
            }
        }
    });


    // Load  all grunt plugins
    grunt.loadNpmTasks("grunt-contrib-uglify");
    grunt.loadNpmTasks("grunt-contrib-jshint");
    grunt.loadNpmTasks("grunt-contrib-watch");
    grunt.loadNpmTasks("grunt-contrib-clean");
    // grunt.loadNpmTasks("grunt-contrib-less");
    grunt.loadNpmTasks("grunt-bowercopy");

    // Default Task (that can be run by typing only "grunt" in cmd)
    grunt.registerTask("default", ["build"]);
    grunt.registerTask("cleanBuild", ["clean:dist"]);
    grunt.registerTask("build", ["clean:dist", "bowercopy"]);
    grunt.registerTask("dev", ["less:dev"]);

    grunt.registerTask('serve', [
        'less',
        //'bowercopy',
        'connect:localServer',
        'watch'
    ]);
};
