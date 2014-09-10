/**
 * New node file
 */

'use strict';

appServices.factory('userService', function($resource){
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
});
