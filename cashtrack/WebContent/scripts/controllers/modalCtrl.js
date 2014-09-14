/**
 * Created by asantos on 14/09/2014.
 */

'use strict';

appControllers.controller("modalCtrl",[
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
	]).controller("modalInstCtrl",
		[ "$scope", "$modalInstance", "items",
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