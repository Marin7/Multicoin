(function () {

    var usersModule = angular.module('userControllers', ['ngRoute'])

    usersModule.config(function ($routeProvider) {
        $routeProvider
            .when('/register', {
                templateUrl: 'app/views/user/user-register.html',
                controller: 'RegisterUserController',
                controllerAs: "createCtrl"
            })
            .when('/login', {
                templateUrl: 'app/views/user/user-login.html',
                controller: 'LoginUserController',
                controllerAs: "loginCtrl"
            })
            .when('/logout', {
                templateUrl: 'app/views/user/user-login.html',
                controller: 'LogoutUserController',
                controllerAs: "logoutCtrl"
            })
    });

    usersModule.controller('RegisterUserController', ['$scope', 'UserFactory',
        function ($scope, UserFactory) {
            $scope.register = function () {
                $scope.user = {
                    "username": $scope.username,
                    "email": $scope.email,
                    "password": $scope.password
                };

                console.debug($scope.user);

                var promise = UserFactory.register($scope.user);
                promise.success(function (data) {
                    $scope.user = data;
                    window.location = "#/";
                    location.reload();
                }).error(function (data, status) {
                    alert(status);
                });
            };
        }]);

    usersModule.controller('LoginUserController', ['$scope', 'UserFactory',
        function ($scope, UserFactory) {
            $scope.login = function () {
                $scope.user = {
                    "username": $scope.username,
                    "password": $scope.password
                };

                var promise = UserFactory.login($scope.user);
                promise.success(function (data) {
                    sessionStorage.setItem('loggedOn', 'true');
                    sessionStorage.setItem('user_id', data.id);
                    $scope.user = data;
                    window.location = "#/";
                    location.reload();
                }).error(function (data, status) {
                    alert(status);
                });
            };
        }]);

    usersModule.controller('LogoutUserController', [
        function () {
            sessionStorage.setItem('loggedOn', 'false');
            sessionStorage.setItem('user_id', 0);
            window.location = "#/";
            location.reload();
        }]);
})();
