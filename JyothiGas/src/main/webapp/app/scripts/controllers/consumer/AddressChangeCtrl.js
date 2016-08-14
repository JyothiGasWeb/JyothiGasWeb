/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('AddressChangeCtrl', ['$scope', 'SessionService', function($scope, SessionService) {

	$scope.current = {};
	$scope.user = {};
    var getCurrentAdd = function() {
        $scope.current = SessionService.getConsumerSession().consumer;
    }
    var init = function() {
        getCurrentAdd();
    };

    $scope.reset = function(){
    	$scope.user = {
    		"newPinCode": "",
    		"newAddress": ""
    	};
    }
    init();
}]);
