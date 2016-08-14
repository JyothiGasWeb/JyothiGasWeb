/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerChangeCtrl', ['$scope', 'SessionService', function($scope, SessionService) {

    $scope.current = {};
    var getCurrentAdd = function() {
        $scope.current = SessionService.getConsumerSession().consumer;
    }
    var init = function() {
        getCurrentAdd();
    };
    init();
}]);
