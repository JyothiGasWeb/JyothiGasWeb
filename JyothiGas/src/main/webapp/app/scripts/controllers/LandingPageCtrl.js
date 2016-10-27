angular.module('clientApp')
    .controller('LandingpageCtrl', ['$scope', 'SessionService', '$state', function($scope, SessionService, $state) {
        var userObj = {};

        $scope.deleteSession = function() {
            SessionService.deleteSession();
        };
        $scope.init = function() {
            var session = SessionService.getSession();
            switch (session.roleId) {
                case 1:
                    $state.go('consumerDash');
                    break;
                case 2:
                    $state.go('dealerDash');
                    break;
            }

        };

        $scope.init();
    }]);
