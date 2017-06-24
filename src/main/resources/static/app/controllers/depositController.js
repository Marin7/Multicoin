(function () {

    var depositModule = angular.module('depositControllers', ['ngRoute'])

    depositModule.config(function ($routeProvider) {
        $routeProvider.when('/deposit', {
            templateUrl: 'app/views/deposit.html',
            controller: 'DepositController',
            controllerAs: "DepositCtrl"
        })
    });

    depositModule.controller('DepositController', ['$scope', '$routeParams', 'UserFactory', 'DepositFactory',
        function ($scope, $routeParams, UserFactory, DepositFactory) {
            var user = UserFactory.findById(sessionStorage.getItem('user_id'));
            user.success(function (user_data) {
                $scope.userDollars = user_data.dollars;
            }).error(function (data, status) {
                alert(status);
            });

            $scope.depositDollars = function () {
                $scope.card = {
                    "cardId": $scope.cardId,
                    "cardOwner": $scope.cardOwner,
                    "expMonth": $scope.expMonth,
                    "expYear": $scope.expYear,
                    "cct": $scope.cct
                };

                var transaction = {
                    "card": $scope.card,
                    "userId": sessionStorage.getItem('user_id'),
                    "amount": $scope.amount
                };
                console.debug(transaction);

                var promise = DepositFactory.deposit(transaction);
                promise.success(function (amount) {
                    $scope.userDollars = $scope.userDollars + amount;
                }).error(function (data, status) {
                    alert(status);
                });
            };

            $scope.withdrawDollars = function () {
                $scope.card = {
                    "cardId": $scope.cardId,
                    "cardOwner": $scope.cardOwner,
                    "expMonth": $scope.expMonth,
                    "expYear": $scope.expYear,
                    "cct": $scope.cct
                };

                var transaction = {
                    "card": $scope.card,
                    "userId": sessionStorage.getItem('user_id'),
                    "amount": $scope.amount
                };
                console.debug(transaction);

                var promise = DepositFactory.withdraw(transaction);
                promise.success(function (amount) {
                    $scope.userDollars = $scope.userDollars + amount;
                }).error(function (data, status) {
                    alert(status);
                });
            };

        }]);


})();