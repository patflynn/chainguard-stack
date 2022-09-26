var app = angular.module('app', []);

app.controller('GreetingsCtrl', ['$scope', 'GreetingsService', function ($scope, GreetingsService) {

    $scope.addGreeting = function () {
        if ($scope.greeting != null && $scope.greeting.message) {
            GreetingsService.addGreeting($scope.greeting.username, $scope.greeting.message)
                .then(function success(response) {
                        $scope.message = 'Message added!';
                        $scope.errorMessage = '';
                    },
                    function error(response) {
                        $scope.errorMessage = 'Error adding message!';
                        $scope.message = '';
                    });
        } else {
            $scope.errorMessage = 'Please enter a username and greeting!';
            $scope.message = '';
        }
    }

    $scope.getAllGreetings = function () {
        GreetingsService.getAllGreetings()
            .then(function success(response) {
                    $scope.greetings = response.data.content;
                    $scope.message = '';
                    $scope.errorMessage = '';
                },
                function error(response) {
                    $scope.message = '';
                    $scope.errorMessage = 'Error getting users!';
                });
    }

}]);

app.service('GreetingsService', ['$http', function ($http) {

    this.addGreeting = function addGreeting(username, message) {
        let response = $http({
            method: 'POST',
            url: 'greetings',
            data: {username: username, message: message, token: grecaptcha.getResponse(captchaWidget)}
        });
        grecaptcha.reset(captchaWidget)
        return response;
    }

    this.getAllGreetings = function getAllGreetings() {
        return $http({
            method: 'GET',
            url: 'greetings'
        });
    }

}]);