/**
 * Created by asantos on 2/08/2014.
 */

'use strict';

angular.module("app.ui.services", ['ngResource']).factory('userService', function($resource){
	return $resource('/cashtrackAPI/rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'login'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
//    return{
//        login:function(user, scope) {
//            var $promise=$http.put('/mmanagerAPI/rest/user/login',user);
//            $promise.then(function (ret) {
//                if(ret.data.user != null) {
//                    scope.message = 'need to redirect.';
//                }
//                scope.message = ret.data.message;
//            })
//        }
//    }
});