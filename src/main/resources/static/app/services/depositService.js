(function () {
    var depositService = angular.module('depositServices', []);

    depositService.factory('DepositFactory', ['$http', 'config',
        function ($http, config) {

            var privateDepositDollars = function (transaction) {
                return $http.post(config.API_URL + '/deposit', transaction);
            };

            var privateWithdrawDollars = function (transaction) {
                return $http.post(config.API_URL + '/withdraw', transaction);
            };

            return {
                deposit: function (transaction) {
                    return privateDepositDollars(transaction);
                },

                withdraw: function (transaction) {
                    return privateWithdrawDollars(transaction);
                }
            };
        }]);

})();