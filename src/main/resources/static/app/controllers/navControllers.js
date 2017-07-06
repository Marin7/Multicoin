(function() {

	var homeModule = angular.module('navControllers', [ 'ngRoute' ])

	homeModule.config(function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'app/views/homeview.html',
			controller : 'HomeController',
			controllerAs : "homeCtrl"
		})
	});

	homeModule.controller('HomeController', ['$scope', '$routeParams', 'CoinFactory',
		function($scope, $routeParams, CoinFactory) {
            $scope.buyPrice = 0;
            $scope.sellPrice = 0;
            var price = CoinFactory.getLatestPrice();
            price.success(function (data) {
                $scope.buyPrice = data.buyPrice;
                $scope.sellPrice = data.sellPrice;
            }).error(function (data, status) {
                alert(status);
            });
	}]);

})();