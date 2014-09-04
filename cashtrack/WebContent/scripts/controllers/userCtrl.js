/**
 * Created by asantos on 2/08/2014.
 */

'use strict';

angular.module("app.ui.ctrls", []).controller("signinCtrl", ["$scope", function($scope) {
    var original;
    return $scope.user = {
        username: "",
        password: ""
    }, $scope.showInfoOnSubmit = !1, original = angular.copy($scope.user), $scope.revert = function() {
        return $scope.user = angular.copy(original), $scope.form_signin.$setPristine()
    }, $scope.canRevert = function() {
        return !angular.equals($scope.user, original) || !$scope.form_signin.$pristine
    }, $scope.canSubmit = function() {
        return $scope.form_signin.$valid && !angular.equals($scope.user, original)
    }, $scope.canSubmitNewUser = function() {
        return $scope.form_signup.$valid && !angular.equals($scope.user, original)
    }, $scope.submitForm = function() {
        return $scope.showInfoOnSubmit = !0, $scope.user.password=''
    }
}
]).controller("signupCtrl", ["$scope", function($scope) {
    var original;
    return $scope.user = {
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
        birthDate: "",
        secretPhrase: "",
        language:""
    }, $scope.showInfoOnSubmit = !1, original = angular.copy($scope.user), $scope.revert = function() {
        return $scope.user = angular.copy(original), $scope.form_signup.$setPristine(), $scope.form_signup.confirmPassword.$setPristine()
    }, $scope.canRevert = function() {
        return !angular.equals($scope.user, original) || !$scope.form_signup.$pristine
    }, $scope.canSubmit = function() {
        return $scope.form_signup.$valid && !angular.equals($scope.user, original)
    }, $scope.submitForm = function() {
        return $scope.showInfoOnSubmit = !0, $scope.revert();
    }
}
]).controller('userCtrl', function ($scope, $rootScope, $location, $cookieStore, userService) {
	
	$scope.rememberMe = false;
	
	var message = $scope.message;
	
//	console.log(message);
	
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
				
				$rootScope.authToken = authToken;
				$scope.rememberMe = user.rememberMe;
				
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
				}
				
				$scope.message = result.message;
				
				console.log("user: "+result.user);
				
				$rootScope.user = result.user;
				$location.path("/dashboard");
			}
		});
	};
	
	$scope.save = function(user) {
		var token = $rootScope.authToken;
		console.log("save token:"+token);
		user.token = token;
		userService.save(user, function(result) {
			$scope.message = result.message;
			console.log("Saving message: "+$scope.message);
			if(result.user != null){
				$location.path("/login");
			}
		});
	}
})