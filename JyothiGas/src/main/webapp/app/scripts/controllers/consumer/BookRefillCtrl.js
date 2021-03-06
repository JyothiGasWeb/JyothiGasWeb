angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', 'SessionService', 'AlertService', 'ConsumerService', 'RegisterService', '$mdDialog', '$state', function($scope, SessionService, AlertService, ConsumerService, RegisterService, $mdDialog, $state) {

        $scope.isForm = true;
        $scope.connectionType = {};
        $scope.refillObj = SessionService.getConsumerSession().consumer;
        console.log($scope.refillObj);
        $scope.paymentType = 'cod';

        var getDetails = function() {
            $scope.cylinders = SessionService.getConsumerSession().consumer.connectionQty;
            getConnectionTypes();
        };

        var getDealer = function() {
            RegisterService.getAllDealers(2).then(function(response) {
                $scope.availableDealers = response;

            }, function(error) {
                console.log("error getting dealers list");
            })
        };

        var getAllDistributors = function() {
            RegisterService.getAllDealers(3).then(function(response) {
                $scope.availableDealers.push(response);
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
                getAllDistributors();
            }, function(error) {
                console.log("error getting connections");
            });
        }

        $scope.bookRefill = function() {
            if ($scope.connectionType.id == 1) {
                $scope.tax = 1;
            } else {
                $scope.tax = 14.5;
            }
            $scope.totalPrice = $scope.cylinders * Math.round(($scope.tax / 100 * $scope.connectionType.customerWithoutTax) + $scope.connectionType.customerWithoutTax);
            var obj = {
                "consumer_id": $scope.refillObj.consumer_id,
                "connectionTypeId": $scope.connectionType.id,
                "quantity": $scope.cylinders,
                "total": $scope.totalPrice,
                "bookingType": "REFILL"
            };
            if($scope.refillObj.dealerId !=0){
                obj.dealerDistributorId = $scope.refillObj.dealerId;
            }else{
                obj.dealerDistributorId = $scope.refillObj.distributor_Id;
            }

            ConsumerService.bookRefill(obj).then(function(response) {
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
