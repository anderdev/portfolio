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
				
			});
		};
	    
	    $scope.back = function() {
			$location.path("/dashboard");
		};
    	
    	angular.extend($scope, {
    		cards: [],
    		load: function() {}
    	});
//    	
//    	load();
		
		$scope.cards = load(), 
		
//		return $scope.cards = [{
//	        name: "Nijiya Markets",
//	        lastDay: "$$",
//	        billPayDay: 4
//	    }, {
//	        name: "Eat On Monday Truck",
//	        lastDay: "$",
//	        billPayDay: 4.3
//	    }, {
//	        name: "Tea Era",
//	        lastDay: "$",
//	        billPayDay: 4
//	    }, {
//	        name: "Rogers Deli",
//	        lastDay: "$",
//	        billPayDay: 4.2
//	    }, {
//	        name: "MoBowl",
//	        lastDay: "$$$",
//	        billPayDay: 4.6
//	    }, {
//	        name: "The Milk Pail Market",
//	        lastDay: "$",
//	        billPayDay: 4.5
//	    }, {
//	        name: "Nob Hill Foods",
//	        lastDay: "$$",
//	        billPayDay: 4
//	    }, {
//	        name: "Scratch",
//	        lastDay: "$$$",
//	        billPayDay: 3.6
//	    }, {
//	        name: "Gochi Japanese Fusion Tapas",
//	        lastDay: "$$$",
//	        sales: 56,
//	        billPayDay: 4.1
//	    }, {
//	        name: "Cost Plus World Market",
//	        lastDay: "$$",
//	        billPayDay: 4
//	    }, {
//	        name: "Bumble Bee Health Foods",
//	        lastDay: "$$",
//	        billPayDay: 4.3
//	    }, {
//	        name: "Costco",
//	        lastDay: "$$",
//	        billPayDay: 3.6
//	    }, {
//	        name: "Red Rock Coffee Co",
//	        lastDay: "$",
//	        billPayDay: 4.1
//	    }, {
//	        name: "99 Ranch Market",
//	        lastDay: "$",
//	        billPayDay: 3.4
//	    }, {
//	        name: "Mi Pueblo Food Center",
//	        lastDay: "$",
//	        billPayDay: 4
//	    }, {
//	        name: "Cucina Venti",
//	        lastDay: "$$",
//	        billPayDay: 3.3
//	    }, {
//	        name: "Sufi Coffee Shop",
//	        lastDay: "$",
//	        billPayDay: 3.3
//	    }, {
//	        name: "Dana Street Roasting",
//	        lastDay: "$",
//	        billPayDay: 4.1
//	    }, {
//	        name: "Pearl Cafe",
//	        lastDay: "$",
//	        billPayDay: 3.4
//	    }, {
//	        name: "Posh Bagel",
//	        lastDay: "$",
//	        billPayDay: 4
//	    }, {
//	        name: "Artisan Wine Depot",
//	        lastDay: "$$",
//	        billPayDay: 4.1
//	    }, {
//	        name: "Hong Kong Chinese Bakery",
//	        lastDay: "$",
//	        billPayDay: 3.4
//	    }, {
//	        name: "Starbucks",
//	        lastDay: "$$",
//	        billPayDay: 3.7
//	    }, {
//	        name: "Tapioca Express",
//	        lastDay: "$",
//	        billPayDay: 3
//	    }, {
//	        name: "House of Bagels",
//	        lastDay: "$",
//	        billPayDay: 4.4
//	    }],
		$scope.searchKeywords = "", $scope.filteredCards = [], $scope.row = "", $scope.select = function(page) {
			var end, start;
			return start = (page - 1) * $scope.numPerPage, end = start + $scope.numPerPage, $scope.currentPageCards = $scope.filteredCards.slice(start, end);
		}, $scope.onFilterChange = function() {
	        return $scope.select(1), $scope.currentPage = 1, $scope.row = ""
	    }, $scope.onNumPerPageChange = function() {
	        return $scope.select(1), $scope.currentPage = 1
	    }, $scope.onOrderChange = function() {
	        return $scope.select(1), $scope.currentPage = 1
	    }, $scope.search = function() {
	        return $scope.filteredCards = $filter("filter")($scope.cards, $scope.searchKeywords), $scope.onFilterChange()
	    }, $scope.order = function(rowName) {
	        return $scope.row !== rowName ? ($scope.row = rowName, $scope.filteredCards = $filter("orderBy")($scope.cards, rowName), $scope.onOrderChange()) : void 0
	    }, $scope.numPerPageOpt = [5, 10, 25, 50], $scope.numPerPage = $scope.numPerPageOpt[1], $scope.currentPage = 1, $scope.currentPageCards = [], (init = function() {
	    	console.log('currentPage: '+$scope.currentPage);
	    	console.log('numPerPage: '+$scope.numPerPage);
	    	console.log('cards: '+$scope.cards.length);
	        return $scope.search(), $scope.select($scope.currentPage);
	    })();
    }
]);