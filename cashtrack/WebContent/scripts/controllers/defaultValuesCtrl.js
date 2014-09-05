/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('defaultValuesCtrl', [
    '$scope', '$routeParams', '$location', 'currencyService', 'typeClosureService',
	function ($scope, $routeParams, $location, currencyService, typeClosureService) {

		function loadDefaultsValues() {
			
			console.log('user id:'+$scope.user.id);
			
			
			currencyService.getbylocale({locale: $scope.user.language}, function(result) {
				console.log('currency list:')
				$scope.currencies = result;

				var list = $scope.currencies;
				for (var int = 0; int < list.length; int++) {
					var currency = list[int];
					console.log('result['+int+']: '+currency.id+' - '+currency.name+' - '+currency.locale);
				}
			});
			
			typeClosureService.getbylocale({locale: $scope.user.language}, function(result) {
				console.log('typeClosure list:')
				$scope.typeClosures = result;
				for (var int = 0; int < result.length; int++) {
					var typeClosure = result[int];
					console.log('result['+int+']: '+typeClosure.id+' - '+typeClosure.type+' - '+typeClosure.locale);
				}
				
			});
			
		}
    	
    	angular.extend($scope, {
    		currencies: [],
    		typeClosures:[],
    		loadDefaultsValues: function() {}
    	});
    	
    	loadDefaultsValues();

    }
]);