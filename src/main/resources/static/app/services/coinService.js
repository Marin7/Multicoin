(function () {
    var coinServices = angular.module('coinServices', []);

    coinServices.factory('CoinFactory', ['$http', 'config',
        function ($http, config) {

            var privateLatestPrice = function () {
                return $http.get(config.API_URL + '/price/');
            };

            var privateExchange = function (transaction) {
                return $http.post(config.API_URL + '/transaction', transaction);
            };

            return {
                getLatestPrice: function () {
                    return privateLatestPrice();
                },

                exchange: function (transaction) {
                    return privateExchange(transaction);
                }
            }
        }]);

})();