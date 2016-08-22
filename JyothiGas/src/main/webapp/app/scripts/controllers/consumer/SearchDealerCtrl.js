/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('SearchDealerCtrl', ['$scope', 'RegisterService', 'ConsumerService', 'AlertService', function($scope, RegisterService, ConsumerService, AlertService) {

	$scope.showDealers = false;
    var getAllDealers = function() {
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
            console.log($scope.availableDealers)
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    $scope.updateDealer = function(){
        //Need to integrate with API
    }

    $scope.filterDealers = function() {
        $scope.dealers = [];
        for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
            if ($scope.dealer.areaCode == $scope.availableDealers[i].dealer_area_code) {
                $scope.dealers.push($scope.availableDealers[i]);
            }
        };
        $scope.showDealers = true;
    };

    var init = function() {
        getAllDealers();
    };
    init();
}]);
