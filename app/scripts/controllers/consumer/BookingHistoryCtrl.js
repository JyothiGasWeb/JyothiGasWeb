/**
 * clientApp Module
 *
 * Description
 */
angular.module('clientApp').
controller('BookingHistoryCtrl', ['$scope', function($scope) {

    $scope.bookings = [];
    var getBookings = function() {
        $scope.bookings = [{
            "refNo": "111122",
            "orderNo": "9787678",
            "orderDate": "12-2-2016",
            "deliveryDate": "13-2-2016",
            "status": "Delivered"
        }, {
            "refNo": "545654",
            "orderNo": "3445657",
            "orderDate": "22-4-2016",
            "deliveryDate": "22-4-2016",
            "status": "Delivered"
        }, {
            "refNo": "678678",
            "orderNo": "756756",
            "orderDate": "09-6-2016",
            "deliveryDate": "09-6-2016",
            "status": "Delivered"
        }]
    }
    var init = function() {
        getBookings();
    };
    init();
}])
