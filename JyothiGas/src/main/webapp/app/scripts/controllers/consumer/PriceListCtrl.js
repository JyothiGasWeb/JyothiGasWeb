/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('PriceListCtrl', ['$scope', 'SessionService', 'ConsumerService', '_', function($scope, SessionService, ConsumerService, _) {

    $scope.priceList = [];
    var getPriceList = function() {

        ConsumerService.getPriceList().then(function(response) {
            for (var i = 0, len = response.length; i < len; i++) {
                if (response[i].appliance_Name.indexOf('Cylinder') == -1) {
                    $scope.priceList.push(response[i]);
                }
            }

        }, function(response) {
            console.log("error getting priceList");
        })
    };

    var init = function() {
        $scope.connectionType = SessionService.getConsumerSession().consumer.connectionTypeName;
        getPriceList();
    };
    init();
}]);
