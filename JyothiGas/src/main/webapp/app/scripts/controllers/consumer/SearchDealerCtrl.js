/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('SearchDealerCtrl', ['$scope', 'RegisterService', function($scope, RegisterService) {

	$scope.showDealers = false;
    var getAllDealers = function() {
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
            console.log($scope.availableDealers)
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

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
