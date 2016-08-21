angular.module('clientApp')
    .controller('HeaderCtrl', ['$scope', '$state', 'SessionService', 'ngCart', 'ConsumerService', '$mdDialog', function($scope, $state, SessionService, ngCart, ConsumerService, $mdDialog) {

        $scope.userLogout = function() {
            SessionService.deleteSession();
            ngCart.empty();
            $state.go('login')
        };

        var getAllNotifications = function() {
            ConsumerService.getAllNotifications().then(function(response) {
                $scope.notifications = response;
            }, function(erorr) {
                console.log("error");
            })
        };

        $scope.openNotifi = function(ev, item) {
            $mdDialog.show({
                    controller: function($scope, $mdDialog, item) {
                        $scope.notification = item;
                        $scope.create = function() {
                            $mdDialog.hide(response);
                        };

                        $scope.closeDialog = function() {
                            $mdDialog.cancel();
                        }
                    },
                    templateUrl: 'app/views/consumer/viewNotification.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    locals: {
                        'item': item
                    }
                })
                .then(function(module) {

                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        $scope.openMenu = function($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };
        var init = function() {
            getAllNotifications();
        };
        init();
    }])
