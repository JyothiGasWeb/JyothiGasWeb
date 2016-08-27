angular.module('clientApp')
    .controller('ForgotPasswdCtrl', ['$scope', '$state', 'LoginService', 'SessionService', 'AlertService', '$mdDialog', function($scope, $state, LoginService, SessionService, AlertService, $mdDialog) {

        $scope.user = {};

        $scope.validateuser = function(ev) {
            LoginService.newOtp($scope.user).then(function(response) {
                if (response.status == 'OK') {
                    $mdDialog.show({
                            controller: function($scope, $mdDialog, AlertService, username, LoginService, $timeout) {
                                $scope.userObj = {
                                    "userName": username,
                                    "verificationType": "eMAIL"
                                };
                                $scope.create = function() {
                                    LoginService.forgotPasswd($scope.userObj).then(function(response) {
                                        if (response.status == 'OK') {
                                            AlertService.alert("Password Changed Successfully", 'md-primary');
                                            $timeout(function() {
                                                $mdDialog.hide(response);
                                            }, 1000);
                                        } else {
                                            AlertService.alert("Invalid OTP", 'md-warn', '1500');
                                        }
                                    }, function(error) {
                                        AlertService.alert("something went wrong. Please try again later", 'md-warn');
                                    })

                                };

                                $scope.closeDialog = function() {
                                    $mdDialog.cancel();
                                };

                                $scope.resend = function() {
                                    var obj = {
                                        "verificationId": username
                                    };
                                    LoginService.newOtp(obj).then(function(response) {
                                        console.log("success");
                                    }, function(error) {
                                        console.log("error")
                                    })
                                }
                            },
                            templateUrl: 'app/views/OtpModal.html',
                            parent: angular.element(document.body),
                            targetEvent: ev,
                            locals: {
                                "username": $scope.user.verificationId
                            }
                        })
                        .then(function() {
                            $scope.login();
                        }, function() {
                            $scope.status = 'You cancelled the dialog.';
                        });
                }else{
                    
                }
            }, function(error) {

            });

        };

        $scope.login = function() {
            $state.go('login');
        }

        var init = function() {

        }
        init();
    }]);
