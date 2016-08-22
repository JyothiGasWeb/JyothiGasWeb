/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerChangeCtrl', ['$scope', 'SessionService', 'RegisterService', '$timeout', function($scope, SessionService, RegisterService, $timeout) {

    $scope.current = {};
    $scope.user = {};
    $scope.isNoDealer = true;
    $scope.dealers = [];
    $scope.availableDealers = [];
    $scope.searchDeal = false;
    $scope.emptyDealer = false;

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

    $scope.filterDealers = function() {
        $scope.dealers = [];
        $scope.searchDeal = true;
        for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
            if ($scope.user.areaCode == $scope.availableDealers[i].dealer_area_code) {
                $scope.dealers.push($scope.availableDealers[i]);
            }
        };
        $timeout(function() {
            if ($scope.dealers.length > 0) {
                $scope.isNoDealer = false;
                $scope.emptyDealer = false;
            } else {
                $scope.isNoDealer = true;
                $scope.emptyDealer = true;
            }
            $scope.searchDeal = false;
        }, 1000);

    };

    $scope.reset = function() {
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
