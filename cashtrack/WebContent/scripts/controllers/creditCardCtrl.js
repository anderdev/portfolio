/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

appControllers.controller('creditCardCtrl', [
    '$scope', '$filter', '$location', 'creditCardService', 'logger', 'localize',
	function ($scope, $filter, $location, creditCardService, logger, localize) {
    	var init;
    	
    	$scope.cardsToDelete = [];
    	
		function load() {
			console.log('loading credit cards');
			console.log('User ID: '+$scope.user.id);
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
		
		$scope.toggleSelection = function toggleSelection(id) {
			var idx = $scope.cardsToDelete.indexOf(id);
			if (idx > -1) {
				$scope.cardsToDelete.splice(idx, 1);
			}
			else {
				$scope.cardsToDelete.push(id);
			}
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
		
		$scope.ok = function() {
			$modalInstance.close($scope.selected.item)
		};
		
		$scope.cancel = function() {
			$modalInstance.dismiss("cancel");
		};
    	
    	angular.extend($scope, {
    		cards: [],
    		load: function() {}
    	});
    	
    	load();
    }
]).controller("modalCtrl",[
	"$scope", "$modal", "$log",
   	function($scope, $modal, $log) {
   		$scope.items = [ "item1", "item2", "item3" ],
		$scope.open = function() {
			var modalInstance;
			modalInstance = $modal.open({
				templateUrl : "creditCardModalContent.html",
				controller : "modalInstCtrl",
				resolve : {
					items : function() {
						return $scope.items;
					}
				}
			}), modalInstance.result.then(function(
					selectedItem) {
				$scope.selected = selectedItem;
			}, function() {
				$log.info("Modal dismissed at: "+ new Date);
			});
		}
	} 
]).controller("modalInstCtrl",[ 
	"$scope", "$modalInstance", "items",
	function($scope, $modalInstance, items) {
		$scope.items = items, $scope.selected = {
			item : $scope.items[0]
		}, $scope.ok = function() {
			$modalInstance.close($scope.selected.item)
		}, $scope.cancel = function() {
			$modalInstance.dismiss("cancel");
		}
	} 
]);