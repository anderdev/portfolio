/**
 * Created by asantos on 2/08/2014.
 */

'use strict';

angular.module("app.ui.services", []).factory('userService', function($http){
    return{
        login:function(user, scope) {
            var $promise=$http.put('/mmanagerAPI/rest/user/login',user);
            $promise.then(function (ret) {
                if(ret.data.user != null) {
                    scope.message = 'need to redirect.';
                }
                scope.message = ret.data.message;
            })
        }
    }
});