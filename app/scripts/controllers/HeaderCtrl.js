angular.module('clientApp')
    .controller('HeaderCtrl', ['$scope', '$state', 'SessionService', function($scope, $state, SessionService) {

        $scope.userLogout = function() {
            SessionService.deleteSession();
            $state.go('login')
        };

        $scope.openMenu = function($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };
    }])
