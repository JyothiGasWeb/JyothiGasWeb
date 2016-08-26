angular.module('clientApp')
    .controller('ConsumerDashCtrl', ['$scope', 'SessionService', '$state', '$mdDialog', 'ngCart', function($scope, SessionService, $state, $mdDialog, ngCart) {

        $scope.user = SessionService.getConsumerSession().consumer;
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
            }else{
                $state.go('bookRefill');
            }
        };

        $scope.checkConnection = function(ev, type) {
            if (type == $scope.user.connectionTypeName.toLowerCase()) {
                $state.go(type)
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

        }
        init();
    }])
