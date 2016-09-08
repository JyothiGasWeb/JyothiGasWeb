/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('PriceListCtrl', ['$scope', 'SessionService', 'ConsumerService', '_', function($scope, SessionService, ConsumerService, _) {

    $scope.priceList = [];
    var getPriceList = function() {
        var obj = { 'connectionTypeID': SessionService.getConsumerSession().consumer.connectionTypeId }
        ConsumerService.getProductType(obj).then(function(response) {
            var connection = response;
            ConsumerService.getPriceList().then(function(response) {
            	$scope.priceList = connection;
                for (var i = 0, len = response.length; i < len; i++) {
                    if (response[i].appliance_Name.indexOf('Cylinder') == -1) {
                        $scope.priceList.push(response[i]);
                    }
                }
                
            }, function(response) {
                console.log("error getting priceList");
            })
        }, function(response) {

        });

    };

    var init = function() {
        console.log(SessionService.getConsumerSession().consumer);
        $scope.connectionType = SessionService.getConsumerSession().consumer.connectionTypeName;
        getPriceList();
    };
    init();
}]);
