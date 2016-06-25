/**
 * Created by uv on 20.06.2016.
 */

'use strict';

console.log('Hello, npm!');

require ('angular')

var dataSourceMod = angular.module('dataSourceMod',[]);

dataSourceMod.run(function($rootScope) {
    $rootScope.name = "datasource";
    $rootScope.baseurl = "http://localhost:8088";
    $rootScope.date = 12345;
    $rootScope.threadCount = 10;
});
