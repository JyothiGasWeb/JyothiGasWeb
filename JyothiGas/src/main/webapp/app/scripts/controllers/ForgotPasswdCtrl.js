angular.module('clientApp')
    .controller('ForgotPasswdCtrl', ['$scope', '$state', 'LoginService', 'SessionService', 'AlertService', '$mdDialog', function($scope, $state, LoginService, SessionService, AlertService, $mdDialog) {

        $scope.user = {};

        $scope.validateuser = function(ev) {
            $mdDialog.show({
                    controller: function($scope, $mdDialog, AlertService) {
                       
                        $scope.create = function() {
                            $mdDialog.hide(response);
                        };

                        $scope.closeDialog = function() {
                            $mdDialog.cancel();
                        }
                    },
                    templateUrl: 'app/views/OtpModal.html',
                    parent: angular.element(document.body),
                    targetEvent: ev
                })
                .then(function() {
                   
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        $scope.login = function(){
            $state.go('login');
        }

        var init = function() {

        }
        init();
    }]);
