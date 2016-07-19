angular.module('medRepApp')
    .controller('LoginCtrl', ['$scope', '$state', 'LoginService', 'SessionService', function($scope, $state, LoginService, SessionService) {

        $scope.user = {};

        var saveUser = function(userObj) {
            LoginService.getUser(userObj.accessToken).then(function(response) {
                userObj.userId = response[0].userId;
                userObj.user = {
                    "firstName": response[0].firstName,
                    "lastName": response[0].lastName,
                    "dPicture": response[0].dPicture
                }
                SessionService.setSession(userObj);
                $state.go('dashboard');
            }, function(response) {
                console.log("errror");
            });
        };

        $scope.validateuser = function() {
            LoginService.login($scope.user).then(function(response) {
                var userObj = {
                    "accessToken": response.accessToken,
                    "username": response.username,
                    "refreshToken": response.refresh_token.value,
                    "doctor": true
                };

                saveUser(userObj);
            }, function(response) {
                console.log(response);
            })

        }
        var init = function() {
            console.log(SessionService.getSession())
        }
        init();
    }]);
