angular.module('clientApp')
    .controller('ConsumerDashCtrl', ['$scope', 'SessionService', '$state', '$mdDialog', function($scope, SessionService, $state, $mdDialog) {


        var userConnectionType = SessionService.getConsumerSession().connectionTypeName;
        $scope.userLogout = function() {
            SessionService.deleteSession();
            $state.go('login')
        };

        $scope.checkConnection = function(ev, type) {
            if (type == userConnectionType.toLowerCase()) {
                $state.go(type)
            } else {
                var message = "Being a " + userConnectionType + "Customer you are not allowed to book " + type + "Connection. Please register separately for " + type + " connection";
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
