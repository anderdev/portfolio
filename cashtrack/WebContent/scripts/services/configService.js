/**
 * New node file
 */

'use strict';

appServices.factory('currencyService', function($resource){
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
