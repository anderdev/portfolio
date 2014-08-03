'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('app', ['ngRoute']);

    app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/login', {templateUrl: 'views/pages/signin.html', controller:'userCtrl'});
        $routeProvider.otherwise({redirectTo: '/login'});
}]);
