(function () {

    var coinModule = angular.module('coinControllers', ['ngRoute'])

    coinModule.config(function ($routeProvider) {
        $routeProvider.when('/coin', {
            templateUrl: 'app/views/coin.html',
            controller: 'CoinController',
            controllerAs: "coinCtrl"
        })
    });

    coinModule.controller('CoinController', ['$scope', '$routeParams', 'CoinFactory',
        function ($scope, $routeParams, CoinFactory) {
            $scope.buyPrice = 0;
            $scope.sellPrice = 0;
            var price = CoinFactory.getLatestPrice();
            price.success(function (data) {
                $scope.buyPrice = data.buyPrice;
                $scope.sellPrice = data.sellPrice;
            }).error(function (data, status) {
                alert(status);
            });

            $scope.buy = function () {
                $scope.transaction = {
                    "coins": $scope.amount,
                    "side": 'buy',
                    "userId": sessionStorage.getItem('user_id')
                };

                var exch = CoinFactory.exchange($scope.transaction);
                exch.success(function () {
                    location.reload();
                }).error(function (data, status) {
                    alert(status);
                });
            };

            $scope.sell = function () {
                $scope.transaction = {
                    "coins": $scope.amount,
                    "side": 'sell',
                    "userId": sessionStorage.getItem('user_id')
                };

                var exch = CoinFactory.exchange($scope.transaction);
                exch.success(function () {
                    location.reload();
                }).error(function (data, status) {
                    alert(status);
                });
            };

        }]);

})();