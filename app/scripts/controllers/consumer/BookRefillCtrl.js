angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', 'LoginService', 'SessionService', 'AlertService', 'ConsumerService', function($scope, LoginService, SessionService, AlertService, ConsumerService) {

        $scope.isForm = true;
        var email = SessionService.getSession().email;
        var getDetails = function() {
            var obj = {
                "email": email
            }
            LoginService.getConsumer(obj).then(function(response) {
                $scope.refillObj = response;
                $scope.cylinders = response.connectionQty;
            }, function(response) {
                console.log("errror");
            });
        };

        $scope.bookRefill = function() {
            if ($scope.cylinders > $scope.refillObj.connectionQty) {
                AlertService.alert("you cannot book more than " + $scope.refillObj.connectionQty +" cylinders", 'md-warn');
            } else {
                var obj = {
                    "consumer_id": $scope.bookRefill.consumer_id,
                    "connectionTypeId": $scope.bookRefill.connectionTypeId,
                    "qunatity": $scope.cylinders
                }
                ConsumerService.bookRefill(obj).then(function(response){
                    console.log(response);
                    var currentdate = new Date(); 
                    $scope.isForm = false;
                    if(currentdate.getHours() > "20"){
                        $scope.isNextDay = true;
                    }else{  
                        $scope.isNextDay = false;
                    }
                    //
                }, function(error){

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
