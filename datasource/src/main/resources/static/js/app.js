/**
 * Created by uv on 20.06.2016.
 */

'use strict';

console.log('Hello, npm!');

var dataSourceMod = angular.module('dataSourceMod',[]);

dataSourceMod.run(function($rootScope) {
    $rootScope.name = "datasource";
    // talk to home only
    $rootScope.baseurl = location.protocol + '//' + location.host;
    $rootScope.date = new Date().toDateString();
    $rootScope.threadCount = 10;
});
