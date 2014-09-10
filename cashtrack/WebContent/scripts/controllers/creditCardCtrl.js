/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('creditCardCtrl', [
    '$scope', '$location', 'creditCardService', 'logger', 'localize',
	function ($scope, $location, creditCardService, logger, localize) {

		function load() {
			
			currencyService.getbylocale({locale: $scope.user.language}, function(result) {
				console.log('Select: '+localize.getLocalizedString('lb_select'));
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
				if(result.config != null){
					return logger.logSuccess(result.message);
				} else {
					return logger.error(result.message);
				}
				
			})
		}
		
		$scope.back = function() {
			$location.path("/dashboard");
		};
    	
    	angular.extend($scope, {
    		currencies: [],
    		typeClosures:[],
    		loadDefaultsValues: function() {}
    	});
    	
    	load();
    }
]);