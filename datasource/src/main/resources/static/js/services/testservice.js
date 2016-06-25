'use strict';
var dataSourceMod = angular.module('dataSourceMod');
dataSourceMod.factory('TestService', function($http, $q){
    return {

        startThreads: function() {
            return $http.get('{{App.baseurl}}restart')
                .then(
                function(response){
                    return response.data;
                },
                function(errResponse){
                    console.error('Error while fetching users');
                    return $q.reject(errResponse);
                }
            );
        }

        //fetchAllUsers: function() {
        //    return $http.get('http://localhost:8080/Spring4MVCAngularJSExample/user/')
        //        .then(
        //        function(response){
        //            return response.data;
        //        },
        //        function(errResponse){
        //            console.error('Error while fetching users');
        //            return $q.reject(errResponse);
        //        }
        //    );
        //},
        //
        //createUser: function(user){
        //    return $http.post('http://localhost:8080/Spring4MVCAngularJSExample/user/', user)
        //        .then(
        //        function(response){
        //            return response.data;
        //        },
        //        function(errResponse){
        //            console.error('Error while creating user');
        //            return $q.reject(errResponse);
        //        }
        //    );
        //},
        //
        //updateUser: function(user, id){
        //    return $http.put('http://localhost:8080/Spring4MVCAngularJSExample/user/'+id, user)
        //        .then(
        //        function(response){
        //            return response.data;
        //        },
        //        function(errResponse){
        //            console.error('Error while updating user');
        //            return $q.reject(errResponse);
        //        }
        //    );
        //},
        //
        //deleteUser: function(id){
        //    return $http.delete('http://localhost:8080/Spring4MVCAngularJSExample/user/'+id)
        //        .then(
        //        function(response){
        //            return response.data;
        //        },
        //        function(errResponse){
        //            console.error('Error while deleting user');
        //            return $q.reject(errResponse);
        //        }
        //    );
        //}

    };

});
