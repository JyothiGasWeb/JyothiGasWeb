angular.module('clientApp')
    .controller('ConsumerDashCtrl', ['$scope', 'SessionService', '$state', '$mdDialog', 'ngCart', '_', 'ConsumerService', function($scope, SessionService, $state, $mdDialog, ngCart, _, ConsumerService) {

        $scope.user = SessionService.getConsumerSession().consumer;
        $scope.isPending = false;
        var getAllBookings = function() {
            var obj = {
                "consumer_id": $scope.user.consumer_id
            }
            ConsumerService.getConsumerBookings(obj).then(function(response) {
                _.each(response, function(item) {
                    if (item.bookingType == 'CYLINDER' && item.status == 'PENDING') {
                        $scope.isPending = true;
                    }
                });
            }, function(error) {
                console.log("error getting consumer bookings");
            })
        }
        $scope.userLogout = function() {
            SessionService.deleteSession();
            ngCart.empty();
            $state.go('login')
        };

        $scope.bookRefill = function(ev) {
            if ($scope.user.userType == 'NEW') {
                var message = "Being a  new customer, Please book a " + $scope.user.connectionTypeName + " connection.";
                var confirm = $mdDialog.confirm()
                    .title(message)
                    .textContent('')
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('Ok');
                $mdDialog.show(confirm).then(function() {

                }, function() {

                });
            } else {
                $state.go('bookRefill');
            }
        };

        $scope.checkConnection = function(ev, type) {
            if (type == $scope.user.connectionTypeName.toLowerCase()) {
                if ($scope.isPending) {
                    var message = "Your Booking is under progress, You cannot book another connection";
                    var confirm = $mdDialog.confirm()
                        .title(message)
                        .textContent('')
                        .ariaLabel('Lucky day')
                        .targetEvent(ev)
                        .ok('Ok');
                    $mdDialog.show(confirm).then(function() {

                    }, function() {

                    });
                } else if ($scope.user.userType == 'EXISTING') {
                    var message = "You already have an active connection, Please book refill";
                    var confirm = $mdDialog.confirm()
                        .title(message)
                        .textContent('')
                        .ariaLabel('Lucky day')
                        .targetEvent(ev)
                        .ok('Ok');
                    $mdDialog.show(confirm).then(function() {

                    }, function() {

                    });
                } else {
                    $state.go(type)
                }
            } else {
                var message = "Being a " + $scope.user.connectionTypeName + " customer you are not allowed to book " + type + " connection. Please register separately for " + type + " connection";
                var confirm = $mdDialog.confirm()
                    .title(message)
                    .textContent('')
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('Ok');
                $mdDialog.show(confirm).then(function() {

                }, function() {

                });
            }
        }
        var init = function() {
            getAllBookings();
        };
        init();
    }])
