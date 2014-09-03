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
				},
				save: {
					method: 'POST',
					params: {'action' : ''},
					headers : {'Content-Type': 'application/json'}
				}
			}
		);
}).factory('currencyService', function($resource){
	console.log('get currency service.');
	return $resource('/cashtrackAPI/rest/currency/:locale', {},
			{
				getbylocale: {
					method: 'GET',
					locale: '@locale',
					headers : {'Content-Type': 'application/json'},
					isArray:true
				}
			}
		);
});