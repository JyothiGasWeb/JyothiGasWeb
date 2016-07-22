angular.module('clientApp')
    .controller('LoginCtrl', ['$scope', '$state', 'LoginService', 'SessionService', 'AlertService', function($scope, $state, LoginService, SessionService, AlertService) {

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
                var response = {"isAuthenticated":true,"isDoctor":true,"isMedRep":false,"isMedRepManager":false,"accessToken":"3e94cf78-aaf5-40ca-ab30-ab2201321176","userId":null,"refresh_token":{"refreshToken":null,"expireToken":0,"value":"a0ab2930-1094-4210-970b-86fedd37480e","expiration":-1429051979},"username":"Dinesh","medRep":false,"medRepManager":false,"doctor":true,"authenticated":true}
                if (response.accessToken && response.username) {
                    var userObj = {
                        "accessToken": response.accessToken,
                        "username": response.username,
                        "refreshToken": response.refresh_token.value,
                        "doctor": true
                    };

                    saveUser(userObj);
                }else if(response.accessToken && !response.username){
                    AlertService.alert("Account not activated", 'md-warn');
                }else{
                     AlertService.alert("Email password did not match", 'md-warn');
                }
            }, function(response) {
                console.log(response);
            })

        };

        $scope.registerNew = function(){
            $state.go('registerNew');
        };

        $scope.registerOld = function(){
            $state.go('registerOld');
        }

        var init = function() {
            //console.log(SessionService.getSession())
        }
        init();
    }]);
