angular.module('clientApp')
    .controller('DealerRefillCtrl', ['$scope', 'SessionService', 'AlertService', 'ConsumerService', 'DealerService', function($scope, SessionService, AlertService, ConsumerService, DealerService) {

        $scope.isForm = true;
        $scope.paymentType = 'cod';
        $scope.refillObj = SessionService.getConsumerSession().consumer;
        $scope.refillObj.connectionTypeId = 1;
        $scope.cylinders = 1;


        $scope.updateType = function() {
            var obj = {
                "connectionTypeID": $scope.refillObj.connectionTypeId
            }
            ConsumerService.getProductType(obj).then(function(response) {
                $scope.connections = response;
            }, function(error) {
                console.log("error getting connections");
            });
        };

        $scope.bookRefill = function() {
            if ($scope.refillObj.connectionTypeId == 1) {
                $scope.tax = 1;
            } else {
                $scope.tax = 14.5;
            }
            $scope.totalPrice = $scope.cylinders * Math.round(($scope.tax / 100 * $scope.connectionType.customerWithoutTax) + $scope.connectionType.customerWithoutTax);
            var obj = {
                "dealerDistributorId": $scope.refillObj.consumer_id,
                "connectionTypeId": parseInt($scope.refillObj.connectionTypeId),
                "quantity": $scope.cylinders,
                "total": $scope.totalPrice,
                "bookingType": "REFILL",
                "userType": "DEALER"
            };

            DealerService.bookRefill(obj).then(function(response) {
                $scope.refNo = response.reference;
                var currentdate = new Date();
                $scope.isForm = false;
                if (currentdate.getHours() > "20") {
                    $scope.isNextDay = true;
                } else {
                    $scope.isNextDay = false;
                };
            }, function(error) {

            })
        };

        var init = function() {
            $scope.updateType();
        };

        init();
    }])
