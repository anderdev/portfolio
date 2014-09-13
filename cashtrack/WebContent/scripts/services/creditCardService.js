/**
 * New node file
 */

'use strict';

appServices.factory('creditCardService', function($resource){
	return $resource('/cashtrackAPI/rest/creditcard/:userId:id', {},
			{
				get: {
					method: 'GET',
					params: {userId : '@userId'},
					isArray:true
				},
				save: {
					method: 'POST',
					params: {'action' : ''},
					headers : {'Content-Type': 'application/json'}
				},
				exclude: {
					method: 'DELETE',
					params: {id : '@id'}
				}
			}
		);
});
