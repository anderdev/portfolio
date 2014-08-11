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
    }, $scope.submitForm = function() {
        return $scope.showInfoOnSubmit = !0, $scope.user.password=''
    }
}
]).controller('userCtrl', function ($scope, userService) {
    $scope.message = '';
    $scope.login = function(user){
        userService.login(user, $scope); // call login service
    }
})