angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', 'SessionService', 'AlertService', 'ConsumerService', function($scope, SessionService, AlertService, ConsumerService) {

        $scope.isForm = true;
        $scope.connectionType = {};
        var getDetails = function() {
            $scope.refillObj = SessionService.getConsumerSession().consumer;
            $scope.cylinders = SessionService.getConsumerSession().consumer.connectionQty;
            getConnectionTypes();
        };

        var getConnectionTypes = function() {
            var obj = {
                "connectionType": $scope.refillObj.connectionTypeName
            }
            ConsumerService.getConnectionTypes(obj).then(function(response) {
                $scope.connections = response;
            }, function(error) {
                console.log("error getting connections");
            });
        }

        $scope.bookRefill = function() {
            var obj = {
                "consumer_id": $scope.refillObj.consumer_id,
                "connectionTypeId": JSON.parse($scope.connectionType).id,
                "qunatity": $scope.cylinders
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
        };
        init();
    }])
