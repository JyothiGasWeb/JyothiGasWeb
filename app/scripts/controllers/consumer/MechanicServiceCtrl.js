/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('MechanicServiceCtrl', ['$scope', 'SessionService', function($scope, SessionService) {

	$scope.current = {};
    $scope.user = {};
    var getCurrentAdd = function() {
    	$scope.current = SessionService.getConsumerSession().consumer;
    };

     $scope.reset = function(){
        $scope.user = {
            "comments": ""
        };
    }
    var init = function() {
        getCurrentAdd();
    };
    init();
}]);
