(function() {

	var homeModule = angular.module('navControllers', [ 'ngRoute' ])

	homeModule.config(function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'app/views/homeview.html',
			controller : 'HomeController',
			controllerAs : "homeCtrl"
		})
	});

	homeModule.controller('HomeController', function($scope) {
	});

})();