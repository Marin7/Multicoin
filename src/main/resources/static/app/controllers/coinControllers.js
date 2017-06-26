(function () {

    var coinModule = angular.module('coinControllers', ['ngRoute'])

    coinModule.config(function ($routeProvider) {
        $routeProvider.when('/coin', {
            templateUrl: 'app/views/coin.html',
            controller: 'CoinController',
            controllerAs: "coinCtrl"
        })
    });

    coinModule.controller('CoinController', ['$scope', '$routeParams', 'CoinFactory', 'UserFactory',
        function ($scope, $routeParams, CoinFactory, UserFactory) {
            $scope.buyPrice = 0;
            $scope.sellPrice = 0;
            var price = CoinFactory.getLatestPrice();
            price.success(function (data) {
                $scope.buyPrice = data.buyPrice;
                $scope.sellPrice = data.sellPrice;
            }).error(function (data, status) {
                alert(status);
            });

            var user = UserFactory.findById(sessionStorage.getItem('user_id'));
            user.success(function (user_data) {
                $scope.userDollars = user_data.dollars;
                $scope.userCoins = user_data.coins;
            }).error(function (data, status) {
                alert(status);
            });

            $scope.buy = function () {
                var transaction = {
                    "amount": $scope.amountDollars,
                    "userId": sessionStorage.getItem('user_id')
                };
                console.debug(transaction);

                var buyMC = CoinFactory.buy(transaction);
                buyMC.success(function () {
                    location.reload();
                }).error(function (data, status) {
                    alert(status);
                });
            };

            $scope.sell = function () {
                $scope.transaction = {
                    "amount": $scope.amountMulticoins,
                    "userId": sessionStorage.getItem('user_id')
                };

                var sellMC = CoinFactory.sell($scope.transaction);
                sellMC.success(function () {
                    location.reload();
                }).error(function (data, status) {
                    alert(status);
                });
            };

        }]);

})();