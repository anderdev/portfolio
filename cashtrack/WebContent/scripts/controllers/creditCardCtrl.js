/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('creditCardCtrl', [
    '$rootScope', '$scope', '$filter', '$location', 'creditCardService', 'logger', 'localize', 
	function ($rootScope, $scope, $filter, $location, creditCardService, logger, localize) {
    	
		function load() {
			console.log('loading credit cards');
			creditCardService.get({userId: $scope.user.id}).$promise.then(function(result) {
				$scope.cards = result;
				$scope.searchKeywords = "";
				$scope.filteredCards = [];
				$scope.row = "";

				$scope.select = function(page) {
					var end, start;
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
			    
				$scope.numPerPageOpt = [5, 10, 25, 50];
				$scope.numPerPage = $scope.numPerPageOpt[1];
				$scope.currentPage = 1;
				$scope.currentPageCards = [];
		        return $scope.search(), $scope.select($scope.currentPage);
			});
		};
		
		$scope.cardsToDelete = [];
		
		$scope.toggleSelection = function toggleSelection(id) {
			var idx = $scope.cardsToDelete.indexOf(id);
			if (idx > -1) {
				$scope.cardsToDelete.splice(idx, 1);
			}
			else {
				$scope.cardsToDelete.push(id);
			}
		};
		  
		$scope.save = function(card) {
			var token = $scope.authToken;
			console.log("token to use:"+token);
			card.user = $scope.user;
			creditCardService.save(card, function(result) {
				if(result.creditCard != null){
					$rootScope.card = null;
					$location.path("setup/creditcard");
					return logger.logSuccess(result.message);
				} else {
					return logger.logError(result.message);
				}
				
			});
		};
		
		$scope.exclude = function(){
			for(var x = 0; x < $scope.cardsToDelete.length; x++){
				var userId = $scope.cardsToDelete[x];
				creditCardService.exclude({id: userId}, function(result) {
					return logger.logSuccess(result.message);
				});
			}
			$scope.cardsToDelete.length = 0;
			load();
		}
		
	    $scope.back = function() {
			$location.path("/dashboard");
		};
		
		$scope.cancel = function() {
			$rootScope.card = null;
			$location.path("setup/creditcard");
		};
		
		$scope.newCard = function() {
			$location.path("setup/creditcardform");
		};
		
		$scope.edit = function(card){
			$rootScope.card = card;
			$location.path("setup/creditcardform");
		}
		
    	angular.extend($scope, {
    		cards: [],
    		load: function() {}
    	});
    	
    	load();
    }
]).controller("creditCardFormCtrl", ["$scope",
	function($scope) {
	    var original;
	    return $scope.cardTemp = {
    		name: "",
    		expire: "",
	        payday: "",
	        lastDayToBuy: ""
	    }, original = angular.copy($scope.cardTemp), 
	       $scope.revert = function() {
	        return $scope.card = angular.copy(original), $scope.creditCardForm.$setPristine()
	    }, $scope.canRevert = function() {
	        return !angular.equals($scope.card, original) || !$scope.creditCardForm.$pristine
	    }, $scope.canSubmit = function() {
	        return $scope.creditCardForm.$valid && !angular.equals($scope.card, original);
	    }
	}
]).directive("validateEquals2", [
	function() {
	    return {
	        require: "ngModel",
	        link: function(scope, ele, attrs, ngModelCtrl) {
	            var validateEqual2;
	            return validateEqual2 = function(value) {
	                var valid;
	                return valid = value === scope.$eval(attrs.validateEquals2), ngModelCtrl.$setValidity("equal", valid), "function" == typeof valid ? valid({
	                    value: void 0
	                }) : void 0;
	            }, ngModelCtrl.$parsers.push(validateEqual2), ngModelCtrl.$formatters.push(validateEqual2), scope.$watch(attrs.validateEquals2, function(newValue, oldValue) {
	                return newValue !== oldValue ? ngModelCtrl.$setViewValue(ngModelCtrl.$ViewValue) : void 0
	            })
	        }
	    }
	}
]);