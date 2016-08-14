/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('AddressChangeCtrl', ['$scope', function($scope) {

	$scope.current = {};
    var getCurrentAdd = function() {
    	$scope.current = {
    		"dealer": "Parvathi enterprises",
    		"consumerNo": "123234",
    		"consumerName": "Nivetha Thomas",
    		"address": "no 14, 165street 5th block",
    		"contactNo": "987654321"
    	}
    }
    var init = function() {
        getCurrentAdd();
    };
    init();
}]);
