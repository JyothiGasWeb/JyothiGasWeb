angular.module('clientApp')
    .controller('ConsumerDashCtrl', ['$scope', 'SessionService', '$state', '$mdDialog', 'ngCart', function($scope, SessionService, $state, $mdDialog, ngCart) {

        var userConnectionType = SessionService.getConsumerSession().consumer.connectionTypeName;
        $scope.userLogout = function() {
            SessionService.deleteSession();
            ngCart.empty();
            $state.go('login')
        };

        $scope.checkConnection = function(ev, type) {
            if (type == userConnectionType.toLowerCase()) {
                $state.go(type)
            } else {
                var message = "Being a " + userConnectionType + " customer you are not allowed to book " + type + " connection. Please register separately for " + type + " connection";
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
