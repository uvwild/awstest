/**
 * Created by uv on 24.06.2016.
 */
module.exports = function (grunt) {

    // loads every task/dependency of the package.json
    require('load-grunt-tasks')(grunt, {
        scope: ['dependencies','devDependencies']
    });

    // process time of every executed task
    require("grunt-timer").init(grunt, {
        color: 'blue',
        deferLogs: true,
        friendlyTime: true
    });

    // to use names below
    var include_all = [
        '**'
    ];

    //grunt.loadNpmTasks('grunt-contrib-jshint');
    //grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.initConfig({
            pkg: grunt.file.readJSON('package.json'),

            // path properties
            properties: {
                src_path: 'src/main/resources/static',
                target_path: 'target/static'
            },


            jshint: {
                files: ['Gruntfile.js', '<%= properties.src_path %>/**/*.js', 'src/test/resource/static/**/*.js'],
                options: {
                    globals: {
                        jQuery: true
                    }
                }
            },

            //
            watch : {
                appsync: {
                    files: [
                        '<%= properties.src_path %>/**/*',
                    ],
                    tasks: [
                        'smart_copy:main',
                    ]
                }
            },
            // Copy sources to target folders
            smart_copy: {
                main: {
                    files: [{
                        cwd: '<%= properties.src_path %>',
                        src: include_all,
                        dest: '<%= properties.target_path %>'
                    }]
                }
            },
        }
    );

    // run the linter
//    grunt.registerTask('default', 'jshint');
    // just copy the files
    grunt.registerTask('default', 'smart_copy:main');

    // watch for changes on javascript code
    grunt.registerTask('backend-watch', 'watch:appsync');

};
