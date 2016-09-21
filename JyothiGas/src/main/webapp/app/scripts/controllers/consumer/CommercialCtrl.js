/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('CommercialCtrl', ['$scope', 'ConsumerService', 'SessionService', '_', function($scope, ConsumerService, SessionService, _) {

    var user = SessionService.getConsumerSession().consumer;
    $scope.connection = {};

    var getProductType = function() {
        var obj = {
            "connectionTypeID": user.connectionTypeId
        }
        ConsumerService.getProductType(obj).then(function(response) {
            $scope.products = response;
            $scope.connection.connectionType = response[0];
            $scope.calculate();
            var priceObj = {
                "gasRefill_charges": 200,
                "handling_charges": 300,
                "delievery_charges": 120
            };
            SessionService.createPriceSession(priceObj);
            getAppliances();
        }, function(error) {

        })
    };

    $scope.calculate = function() {
        $scope.total = 2000 + 400 + 200 + $scope.connection.connectionType.customerWithoutTax;
    };

    var getAppliances = function() {
        ConsumerService.getAppliances().then(function(response) {
            $scope.appliances = response;
            _.each($scope.appliances, function(item) {
                item.imgSrc = "app/images/" + item.appliance_Name + ".jpg";
            });
        }, function(error) {

        })
    }

    var init = function() {
        SessionService.deletePriceSession();
        getProductType();
    };
    init();
}]);
