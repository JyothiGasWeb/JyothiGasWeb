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
                    "roleId": response.roleId,
                    "roleName": response.roleName
                }
                SessionService.setConsumerSession(response);
                SessionService.setSession(userObj);
                switch (userObj.roleId) {
                    case 1:
                        $state.go('consumerDash');
                        break;
                    case 2:
                        $state.go('dealerDash');
                        break;
                };
                
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
                    AlertService.alert("Email and password did not match", 'md-warn');
                }
            }, function(response) {
                console.log(response);
            })

        };

        $scope.registerNew = function() {
            $state.go('registerNew');
        };

        var init = function() {
            //console.log(SessionService.getSession())
        }
        init();
    }]);
