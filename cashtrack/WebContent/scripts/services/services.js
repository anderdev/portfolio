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
	return $resource('/cashtrackAPI/rest/currency/:locale', {},
			{
				getbylocale: {
					method: 'GET',
					params: {locale : '@locale'},
					isArray:true
				}
			}
		);
}).factory('typeClosureService', function($resource){
	return $resource('/cashtrackAPI/rest/typeclosure/:locale', {},
			{
				getbylocale: {
					method: 'GET',
					params: {locale : '@locale'},
					isArray:true
				},
				save: {
					method: 'POST',
					params: {'action' : ''},
					headers : {'Content-Type': 'application/json'}
				}
			}
		);
}).factory('configService', function($resource){
	return $resource('/cashtrackAPI/rest/config/:action', {},
			{
				get: {
					method: 'GET',
					params: {'action' : ''},
					isArray:true
				},
				save: {
					method: 'POST',
					params: {'action' : ''},
					headers : {'Content-Type': 'application/json'}
				}
			}
		);
});