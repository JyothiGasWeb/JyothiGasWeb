/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerCylinderBookingsCtrl', ['$scope', 'DealerService', function($scope, DealerService) {

    $scope.list = [];
    var start = 1900;
    var end = new Date().getFullYear();
    $scope.year = end;
    $scope.yearsList = [];
    for (var year = start; year <= end; year++) {
        $scope.yearsList.unshift(year);
    };

    $scope.getBookings = function() {
        DealerService.cylinderBookings($scope.year).then(function(response) {
            console.log(response);
            $scope.list = response;
        }, function(error) {
            console.log("Error retrieving cylinder Bookings");
        })
    };

    var init = function() {
        $scope.getBookings();
    };
    init();
}]);
