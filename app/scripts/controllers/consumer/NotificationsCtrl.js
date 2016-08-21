/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('NotificationsCtrl', ['$scope', 'ConsumerService', '$mdDialog', function($scope, ConsumerService, $mdDialog) {


	$scope.notifications = [];

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

    var getAllNotifications = function() {
        ConsumerService.getAllNotifications().then(function(response) {
            $scope.notifications = response;
        }, function(erorr) {
            console.log("error");
        })
    };

    var init = function() {
    	getAllNotifications();
    };
    init();
}]);
