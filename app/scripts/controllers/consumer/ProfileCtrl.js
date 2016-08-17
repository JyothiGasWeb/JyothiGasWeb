/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('ProfileCtrl', ['$scope', 'SessionService', 'RegisterService', 'ConsumerService', 'AlertService', 'LoginService', function($scope, SessionService, RegisterService, ConsumerService, AlertService, LoginService) {

    $scope.availableDealers = [];
    var getAllDealers = function() {
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
            getConsumer();
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    $scope.updateFile = function(){
        
    }

    var getConsumer = function() {
        $scope.consumer = JSON.parse(JSON.stringify(SessionService.getConsumerSession().consumer));
        $scope.filterDealers();
    }

    $scope.update = function(consumerForm) {
        var obj = {
            "id": $scope.consumer.reg_id,
            "areaCode": $scope.consumer.areaCode,
            "address": $scope.consumer.address,
            "connectionTypeId": $scope.consumer.connectionTypeId,
            "dealerId": $scope.consumer.dealerId,
            "status": "NEW"
        }
        ConsumerService.updateConsumer(obj).then(function(response) {
            if (response.status == 'OK') {
                updateUser();
            }
        }, function(erorr) {
            console.log("error Updating Consumer");
        });
    };

    var updateUser = function() {
        var obj = {
            "email": $scope.consumer.email
        }
        LoginService.getConsumer(obj).then(function(response) {
            SessionService.setConsumerSession(response);
            AlertService.alert("Profile updated sucessfully", "md-primary");
            getConsumer();
            //$scope.reset();
        }, function(response) {
            console.log("errror");
        });
    };

    $scope.filterDealers = function() {
        $scope.dealers = [];
        for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
            if ($scope.consumer.areaCode == $scope.availableDealers[i].dealer_area_code) {
                $scope.dealers.push($scope.availableDealers[i]);
            }
        }
    };

    var init = function() {
        getAllDealers();
    };
    init();
}]);
