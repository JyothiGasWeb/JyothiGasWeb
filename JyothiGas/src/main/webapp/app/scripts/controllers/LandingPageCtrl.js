angular.module('clientApp')
    .controller('LandingpageCtrl', ['$scope', 'SessionService', '$state', function($scope, SessionService, $state) {
        var userObj = {};

        $scope.deleteSession = function() {
            SessionService.deleteSession();
        };
        $scope.init = function() {
            var session = SessionService.getSession();
            switch (session.roleName) {
                case 'Consumer':
                    $state.go('consumerDash');
                    break;
            }

        };

        $scope.init();
    }]);
