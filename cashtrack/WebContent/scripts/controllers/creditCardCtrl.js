/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('creditCardCtrl', [
    '$scope', '$filter', '$location', 'creditCardService', 'logger', 'localize',
	function ($scope, $filter, $location, creditCardService, logger, localize) {
    	var init;
    	
		function load() {
			console.log('loading credit cards');
			console.log('User ID: '+$scope.user.id);
			creditCardService.get({userId: $scope.user.id}, function(result) {
				$scope.cards = result;
				for(var x = 0; x < $scope.cards.length; x++){
					console.log('credit card - ID: '+$scope.cards[x].id+' NAME: '+$scope.cards[x].name);
				}
			});
			return $scope.cards;
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
		
		$scope.searchKeywords = "", $scope.filteredCards = [], $scope.row = "", $scope.select = function(page) {
			var end, start;
			console.log('end: '+end+' start: '+start);
			return start = (page - 1) * $scope.numPerPage, end = start + $scope.numPerPage, $scope.currentPageCards = $scope.filteredCards.slice(start, end);
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
            return $scope.row !== rowName ? ($scope.row = rowName, $scope.filteredCards = $filter("orderBy")($scope.cards, rowName), $scope.onOrderChange()) : void 0
        };
        
        $scope.numPerPageOpt = [5, 10, 25, 50], $scope.numPerPage = $scope.numPerPageOpt[1], $scope.currentPage = 1, $scope.currentPageCards = [], (init = function() {
//        	load();
        	console.log('currentPage: '+$scope.currentPage);
        	console.log('numPerPage: '+$scope.numPerPage);
        	console.log('cards: '+$scope.cards);
        	console.log('currentPageCards: '+$scope.currentPageCards);
            return $scope.search(), $scope.select($scope.currentPage);
        });
    	
    	angular.extend($scope, {
    		cards: [],
    		load: function() {}
    	});
    	
    	load();
    }
]);