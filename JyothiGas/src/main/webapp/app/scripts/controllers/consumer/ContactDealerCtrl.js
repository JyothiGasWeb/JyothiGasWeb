/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('ContactDealerCtrl', ['$scope', 'RegisterService', 'SessionService', function($scope, RegisterService, SessionService) {


    var getContactDetails = function() {
        RegisterService.getAllDealers(2).then(function(response) {
            $scope.availableDealers = response;
            for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
                if(SessionService.getConsumerSession().consumer.dealerId == $scope.availableDealers[i].id){
                    $scope.dealer = $scope.availableDealers[i];
                    break;
                }
            }
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    var init = function() {
        getContactDetails();
    };
    init();
}]);
