/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('creditCardCtrl', [
    '$scope', '$filter', '$location', 'creditCardService', 'logger', 'localize',
	function ($scope, $filter, $location, creditCardService, logger, localize) {
    	var init;
    	
		function load() {
			creditCardService.get({userId: $scope.user.id}, function(result) {
				$scope.cards = result;
			});
		};
		
		$scope.save = function(creditCard) {
			var token = $scope.authToken;
			console.log("token to use:"+token);
			creditCard.user = $scope.user;
			configService.save(creditCard, function(result) {
				if(result.creditCard != null){
					return logger.logSuccess(result.message);
				} else {
					return logger.error(result.message);
				}
				
			})
		};
		
		$scope.back = function() {
			$location.path("/dashboard");
		};
		
		$scope.onFilterChange = function() {
            return $scope.select(1), $scope.currentPage = 1, $scope.row = ""
        };
		
		$scope.onNumPerPageChange = function() {
            return $scope.select(1), $scope.currentPage = 1
        };
        
        $scope.onOrderChange = function() {
            return $scope.select(1), $scope.currentPage = 1
        };
        
        $scope.search = function() {
            return $scope.filteredCards = $filter("filter")($scope.cards, $scope.searchKeywords), $scope.onFilterChange()
        };
        
        $scope.order = function(rowName) {
            return $scope.row !== rowName ? ($scope.row = rowName, $scope.filteredCards = $filter("orderBy")($scope.stores, rowName), $scope.onOrderChange()) : void 0
        };
        
        $scope.numPerPageOpt = [3, 5, 10, 20], $scope.numPerPage = $scope.numPerPageOpt[2], $scope.currentPage = 1, $scope.currentPageCards = [], (init = function() {
            return $scope.search(), $scope.select($scope.currentPage);
        });
    	
    	angular.extend($scope, {
    		cards: [],
    		load: function() {}
    	});
    	
    	load();
    }
]);