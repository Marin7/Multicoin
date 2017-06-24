(function() {
	var userServices = angular.module('userServices', []);

	userServices.factory('UserFactory', [ '$http', 'config',
			function($http, config) {

				var privateUserDetails = function(id) {
					return $http.get(config.API_URL + '/user/' + id);
				};

				var privateUserRegister = function(user) {
				    return $http.post(config.API_URL + '/user/register', user);
                };

				var privateUserLogin = function(user) {
				    return $http.post(config.API_URL + '/user/login', user);
                };

				return {
					findById : function(id) {
						return privateUserDetails(id);
					},

					register: function(user) {
					    return privateUserRegister(user);
                    },

                    login: function(user) {
					    return privateUserLogin(user);
                    }
				};
			} ]);

})();