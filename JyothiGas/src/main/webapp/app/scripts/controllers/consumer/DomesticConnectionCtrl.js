/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DomesticConnectionCtrl', ['$scope', 'ConsumerService', 'SessionService', '_', function($scope, ConsumerService, SessionService, _) {

    var user = SessionService.getConsumerSession().consumer;
    $scope.connection = {};

    var getProductType = function() {
        var obj = {
            "connectionTypeID": user.connectionTypeId
        }
        ConsumerService.getProductType(obj).then(function(response) {
            $scope.products = response;
            console.log($scope.products);
            $scope.connection.connectionType = response[0];
            $scope.calculate();

            getAppliances();
        }, function(error) {
            console.log("error");
        });
    };

    $scope.calculate = function() {
        var priceObj = {
            "deposite_charges": 1200,
            "regulator_charges": 300,
            "gasRefill_charges": Math.round((1 / 100 * $scope.connection.connectionType.customerWithoutTax) + $scope.connection.connectionType.customerWithoutTax),
            "handling_charges": 300,
            "delievery_charges": 120
        };
        SessionService.createPriceSession(priceObj);
        $scope.total = Math.round(1200 + 345 + 300 + (1 / 100 * $scope.connection.connectionType.customerWithoutTax) + $scope.connection.connectionType.customerWithoutTax);
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
