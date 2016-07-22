angular.module('clientApp')
    .controller('ForgotPasswdCtrl', ['$scope', '$state', 'LoginService', 'SessionService', 'AlertService', function($scope, $state, LoginService, SessionService, AlertService) {

        $scope.user = {};

        var init = function() {
        	
        }
        init();
    }]);
