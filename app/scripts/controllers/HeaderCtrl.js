angular.module('clientApp')
    .controller('HeaderCtrl', ['$scope', '$state', 'SessionService', 'ngCart', function($scope, $state, SessionService, ngCart) {

        $scope.userLogout = function() {
            SessionService.deleteSession();
            ngCart.empty();
            $state.go('login')
        };

        $scope.openMenu = function($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };
    }])
