/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

angular.module("app.ui.ctrls", []).controller('defaultValuesCtrl', function ($scope, $routeParams, $location, currencyService, typeClosureService) {
	
	$scope.loadDefaultsCurrency = function() {
		
		currencyService.getbylocale({locale: $scope.lang}, function(result) {
			console.log('currency list:')
			for (var int = 0; int < result.length; int++) {
				var currency = result[int];
				console.log('result['+int+']: '+currency.id+' - '+currency.name+' - '+currency.locale);
			}
			$scope.currencies = result;
		});
		
		typeClosureService.getbylocale({locale: $scope.lang}, function(result) {
			console.log('typeClosure list:')
			for (var int = 0; int < result.length; int++) {
				var typeClosure = result[int];
				console.log('result['+int+']: '+typeClosure.id+' - '+typeClosure.type+' - '+typeClosure.locale);
			}
			$scope.typeClosures = result;
		});
		
	};
})