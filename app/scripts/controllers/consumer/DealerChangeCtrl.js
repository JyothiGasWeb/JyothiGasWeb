/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerChangeCtrl', ['$scope', 'SessionService', 'RegisterService', function($scope, SessionService, RegisterService) {

    $scope.current = {};
    $scope.user = {};
    var getCurrentAdd = function() {
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
            for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
                if (SessionService.getConsumerSession().consumer.dealerId == $scope.availableDealers[i].id) {
                    $scope.current = $scope.availableDealers[i];
                    break;
                }
            }
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    $scope.reset = function(){
    	$scope.user = {
    		"newPinCode": "",
    		"newAddress": ""
    	};
    }

    var init = function() {
        getCurrentAdd();
    };
    init();
}]);
