angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', 'SessionService', 'AlertService', 'ConsumerService', 'RegisterService', '$mdDialog', '$state', function($scope, SessionService, AlertService, ConsumerService, RegisterService, $mdDialog, $state) {

        $scope.isForm = true;
        $scope.connectionType = {};
        $scope.refillObj = SessionService.getConsumerSession().consumer;
        $scope.paymentType = 'cod';
        
        var getDetails = function() {
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
                "quantity": $scope.cylinders,
                "bookingType": "REFILL"
            };

            ConsumerService.bookRefill(obj).then(function(response) {
                $scope.refNo = response.reference;
                $scope.totalPrice = $scope.cylinders * $scope.connectionType.applliance_Cost;
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
            if ($scope.refillObj.userType == 'NEW') {
                var message = "Being a  new customer, Please book a " + $scope.refillObj.connectionTypeName + " connection.";
                var confirm = $mdDialog.confirm()
                    .title(message)
                    .textContent('')
                    .ariaLabel('Lucky day')
                    .targetEvent(document.body)
                    .ok('Ok');
                $mdDialog.show(confirm).then(function() {
                    $state.go('consumerDash');
                }, function() {
                    $state.go('consumerDash');
                });
            } else {
                getDetails();
                getDealer();
            }

        };
        init();
    }])
