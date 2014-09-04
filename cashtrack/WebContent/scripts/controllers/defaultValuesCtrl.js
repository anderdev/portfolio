/**
 * Created by asantos on 03/09/2014.
 */

'use strict';

angular.module("app.ui.ctrls", []).controller('defaultValuesCtrl', function ($scope, $routeParams, $location, currencyService) {
	
	$scope.loadDefaultsCurrency = function(lang) {
		console.log('started loadDefaultsCurrency');
		console.log("locale: "+lang);
		
		var currencies = currencyService.getbylocale(lang);		
		console.log(currencies);
		console.log(currencies.length);
		
		for (var int = 0; int < currencies.length; int++) {
			var x = currencies[int];
			console.log('x result['+int+']: '+x.id+' - '+x.name+' - '+x.locale);
		}
		
		currencyService.getbylocale({locale: lang}, function(result) {
			for (var int = 0; int < result.length; int++) {
				var currency = result[int];
				console.log('result['+int+']: '+currency.id+' - '+currency.name+' - '+currency.locale);
			}
		});
		
		currencyService.get({locale: lang}, function(result) {
			for (var int = 0; int < result.length; int++) {
				var currency = result[int];
				console.log('result['+int+']: '+currency.id+' - '+currency.name+' - '+currency.locale);
			}
		});
		console.log('ended loadDefaultsCurrency');
	};
})