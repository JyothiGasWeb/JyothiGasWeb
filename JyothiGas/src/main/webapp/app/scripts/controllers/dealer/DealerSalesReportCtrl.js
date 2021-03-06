/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerSalesReportCtrl', ['$scope', 'DealerService', function($scope, DealerService) {

    $scope.list = [];
    var start = 1900;
    var end = new Date().getFullYear();
    $scope.year = end;
    $scope.yearsList = [];
    for (var year = start; year <= end; year++) {
        $scope.yearsList.unshift(year);
    };
    $scope.getReport = function() {
        DealerService.salesReport($scope.year).then(function(response) {
            console.log(response);
            $scope.list = response;
        }, function(error) {
            console.log("Error retrieving sales report");
        })
    };

    var init = function() {
        $scope.getReport();
    };
    init();
}]);
