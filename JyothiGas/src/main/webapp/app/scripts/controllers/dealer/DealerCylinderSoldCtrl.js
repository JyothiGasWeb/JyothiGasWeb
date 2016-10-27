/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerCylinderSoldCtrl', ['$scope', 'DealerService', function($scope, DealerService) {

    $scope.list = [];
    var start = 1900;
    var end = new Date().getFullYear();
    $scope.year = end;
    $scope.yearsList = [];
    for (var year = start; year <= end; year++) {
        $scope.yearsList.unshift(year);
    };
    $scope.getSold = function() {
        DealerService.cylinderSold($scope.year).then(function(response) {
            $scope.list = response;
        }, function(error) {
            console.log("Error retrieving cylinders sold");
        })
    };

    var init = function() {
        $scope.getSold();
    };
    init();
}]);
