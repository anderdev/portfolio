/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('defaultValuesCtrl', [
    '$scope', '$routeParams', '$location', 'currencyService', 'typeClosureService', 'configService',
	function ($scope, $routeParams, $location, currencyService, typeClosureService, configService) {

		function loadDefaultsValues() {
			
			console.log('user id:'+$scope.user.id);
			
			
			currencyService.getbylocale({locale: $scope.user.language}, function(result) {
				$scope.currencies = result;
			});
			
			typeClosureService.getbylocale({locale: $scope.user.language}, function(result) {
				$scope.typeClosures = result;
			});
			
		}
		
		$scope.save = function(config) {
			var token = $scope.authToken;
			console.log("token to use:"+token);
			config.user = $scope.user;
			configService.save(config, function(result) {
				$scope.message = result.message;
				console.log("Saving message: "+$scope.message);
			});
		}
		
		$scope.back = function() {
			$location.path("/dashboard");
		};
    	
    	angular.extend($scope, {
    		currencies: [],
    		typeClosures:[],
    		loadDefaultsValues: function() {}
    	});
    	
    	loadDefaultsValues();

    }
]);