/**
 * clientApp Module
 *
 * Description
 */
angular.module('clientApp').
controller('BookingHistoryCtrl', ['$scope', 'SessionService', 'ConsumerService', function($scope, SessionService, ConsumerService) {

    $scope.bookings = [];
    $scope.noBookings = false;
    var consumerId = SessionService.getConsumerSession().consumer.consumer_id;
    var getBookings = function() {
        var obj = {
            "consumer_id": consumerId
        }
        ConsumerService.getConsumerBookings(obj).then(function(response){
            $scope.bookings = response;
            if($scope.bookings.length == 0){
                $scope.noBookings = true;
            }
        }, function(error){
            console.log("error getting consumer bookings");
        })
    }
    var init = function() {
        getBookings();
    };
    init();
}])
