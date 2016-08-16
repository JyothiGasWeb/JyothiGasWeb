angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', 'SessionService', 'AlertService', 'ConsumerService', function($scope, SessionService, AlertService, ConsumerService) {

        $scope.isForm = true;
        var getDetails = function() {
            $scope.refillObj = SessionService.getConsumerSession().consumer;
            $scope.cylinders = SessionService.getConsumerSession().consumer.connectionQty;
        };

        $scope.bookRefill = function() {
            if ($scope.cylinders > $scope.refillObj.connectionQty) {
                AlertService.alert("you cannot book more than " + $scope.refillObj.connectionQty + " cylinders", 'md-warn');
            } else {
                var obj = {
                    "consumer_id": $scope.refillObj.consumer_id,
                    "connectionTypeId": $scope.refillObj.connectionTypeId,
                    "qunatity": $scope.cylinders
                }
                ConsumerService.bookRefill(obj).then(function(response) {
                    console.log(response);
                    $scope.refNo = response.reference;
                    $scope.totalPrice = $scope.cylinders * $scope.refillObj.connectionPrice;
                    var currentdate = new Date();
                    $scope.isForm = false;
                    if (currentdate.getHours() > "20") {
                        $scope.isNextDay = true;
                    } else {
                        $scope.isNextDay = false;
                    };
                }, function(error) {

                })

            }
        };

        $scope.reset = function() {
            $scope.user = {};
        }

        var init = function() {
            getDetails();
        };
        init();
    }])
