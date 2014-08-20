/**
 * Created by asantos on 2/08/2014.
 */

'use strict';

angular.module("app.ui.services", ['ngResource']).factory('userService', function($resource){
	console.log('get user service.');
	return $resource('/cashtrackAPI/rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'login'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
				getbyusername: {
					method: 'PUT',
					params: {'action' : 'getbyusername'},
					headers : {'Content-Type': 'application/json'}
				}
			}
		);
});