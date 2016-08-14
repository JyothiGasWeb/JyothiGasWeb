angular.module('clientApp')
    .controller('LoginCtrl', ['$scope', '$state', 'LoginService', 'SessionService', 'AlertService', function($scope, $state, LoginService, SessionService, AlertService) {

        $scope.user = {};

        var saveUser = function(username) {
            var obj = {
                "email": username
            }
            LoginService.getConsumer(obj).then(function(response) {
                var userObj = {
                    "consumer_id": response.consumer_id,
                    "email": response.email,
                    "connectionTypeName": response.connectionTypeName,
                    "connectionTypeId": response.connectionTypeId
                }
                SessionService.setConsumerSession(response);
                SessionService.setSession(userObj);
                $state.go('consumerDash');
                console.log(response)
            }, function(response) {
                console.log("errror");
            });
        };

        $scope.validateuser = function() {
            LoginService.login($scope.user).then(function(response) {
                if (response && response.status == 'OK') {
                    saveUser($scope.user.username);
                } else if (response.accessToken && !response.username) {
                    AlertService.alert("Account not activated", 'md-warn');
                } else {
                    AlertService.alert("Email password did not match", 'md-warn');
                }
            }, function(response) {
                console.log(response);
            })

        };

        $scope.registerNew = function() {
            $state.go('registerNew');
        };

        $scope.registerOld = function() {
            $state.go('registerOld');
        }

        var init = function() {
            //console.log(SessionService.getSession())
        }
        init();
    }]);
