(function () {
    var coinServices = angular.module('coinServices', []);

    coinServices.factory('CoinFactory', ['$http', 'config',
        function ($http, config) {

            var privateLatestPrice = function () {
                return $http.get(config.API_URL + '/price/');
            };

            var privateBuy = function (transaction) {
                return $http.post(config.API_URL + '/transaction/buy', transaction);
            };

            var privateSell = function (transaction) {
                return $http.post(config.API_URL + '/transaction/sell', transaction);
            };

            return {
                getLatestPrice: function () {
                    return privateLatestPrice();
                },

                buy: function(transaction) {
                    return privateBuy(transaction);
                },

                sell: function (transaction) {
                    return privateSell(transaction);
                }
            }
        }]);

})();