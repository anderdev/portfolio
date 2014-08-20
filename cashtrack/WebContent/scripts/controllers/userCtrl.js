/**
 * Created by asantos on 2/08/2014.
 */

'use strict';

angular.module("app.ui.ctrls", []).controller("signinCtrl", ["$scope", function($scope) {
    var original;
    return $scope.user = {
        username: "x",
        password: ""
    }, $scope.showInfoOnSubmit = !1, original = angular.copy($scope.user), $scope.revert = function() {
        return $scope.user = angular.copy(original), $scope.form_signin.$setPristine()
    }, $scope.canRevert = function() {
        return !angular.equals($scope.user, original) || !$scope.form_signin.$pristine
    }, $scope.canSubmit = function() {
        return $scope.form_signin.$valid && !angular.equals($scope.user, original)
    }, $scope.submitForm = function() {
        return $scope.showInfoOnSubmit = !0, $scope.user.password=''
    }
}
]).controller('userCtrl', function ($scope, $rootScope, $location, $cookieStore, userService) {
	
	$scope.rememberMe = false;
	
	$scope.login = function(user) {
		userService.authenticate($.param({username: user.username, password: user.password}), function(result) {
			$scope.message = '';
			console.log(result.message);
			
			if(result.tokenTransfer == null){
				$scope.message = result.message;
				
				var username = user.username;
				
				console.log("username: "+username);
				
			} else{
				var authToken = result.tokenTransfer.token;
				
				console.log("token: "+authToken);
				
				$rootScope.authToken = authToken;
				$scope.rememberMe = user.rememberMe;
				
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
					console.log("token created");
				}
				
				$scope.message = result.message;
				
				console.log("user: "+result.user);
				
				$rootScope.user = result.user;
				$location.path("/dashboard");
			}
		});
	};
})