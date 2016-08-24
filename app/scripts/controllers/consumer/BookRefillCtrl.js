angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', 'SessionService', 'AlertService', 'ConsumerService', 'RegisterService', function($scope, SessionService, AlertService, ConsumerService, RegisterService) {

        $scope.isForm = true;
        $scope.connectionType = {};
        var getDetails = function() {
            $scope.refillObj = SessionService.getConsumerSession().consumer;
            $scope.cylinders = SessionService.getConsumerSession().consumer.connectionQty;
            getConnectionTypes();
        };

        var getDealer = function() {
            RegisterService.getAllDealers().then(function(response) {
                $scope.availableDealers = response;
                for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
                    if (SessionService.getConsumerSession().consumer.dealerId == $scope.availableDealers[i].id) {
                        $scope.dealer = $scope.availableDealers[i];
                        break;
                    }
                }
            }, function(error) {
                console.log("error getting dealers list");
            })
        };

        var getConnectionTypes = function() {
            var obj = {
                "connectionTypeID": $scope.refillObj.connectionTypeId
            }
            ConsumerService.getProductType(obj).then(function(response) {
                $scope.connections = response;
            }, function(error) {
                console.log("error getting connections");
            });
        }

        $scope.bookRefill = function() {
            var obj = {
                "consumer_id": $scope.refillObj.consumer_id,
                "connectionTypeId": $scope.connectionType.id,
                "qunatity": $scope.cylinders,
                "bookingType":"REFILL" 
            };

            ConsumerService.bookRefill(obj).then(function(response) {
                $scope.refNo = response.reference;
                console.log($scope.connectionType.connectionPrice);
                $scope.totalPrice = $scope.cylinders * JSON.parse($scope.connectionType).connectionPrice;
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

        $scope.reset = function() {
            $scope.user = {};
        }

        var init = function() {
            getDetails();
            getDealer();
        };
        init();
    }])
