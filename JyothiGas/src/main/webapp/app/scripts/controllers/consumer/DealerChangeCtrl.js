/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('DealerChangeCtrl', ['$scope', 'SessionService', 'RegisterService', '$timeout', 'ConsumerService', 'AlertService', 'LoginService', function($scope, SessionService, RegisterService, $timeout, ConsumerService, AlertService, LoginService) {

    $scope.current = {};
    $scope.user = {};
    $scope.isNoDealer = true;
    $scope.dealers = [];
    $scope.availableDealers = [];
    $scope.searchDeal = false;
    $scope.emptyDealer = false;


    var getCurrentDealer = function() {
        $scope.consumer = SessionService.getConsumerSession().consumer;
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
            for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
                if ($scope.consumer.dealerId == $scope.availableDealers[i].id) {
                    $scope.current = $scope.availableDealers[i];
                    break;
                }
            }
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    $scope.filterDealers = function() {
        $scope.dealers = [];
        $scope.searchDeal = true;
        for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
            if ($scope.user.areaCode == $scope.availableDealers[i].dealer_area_code) {
                $scope.dealers.push($scope.availableDealers[i]);
            }
        };
        $timeout(function() {
            if ($scope.dealers.length > 0) {
                $scope.isNoDealer = false;
                $scope.emptyDealer = false;
                $scope.user.dealerId = $scope.dealers[0].id;
            } else {
                $scope.isNoDealer = true;
                $scope.emptyDealer = true;
            }
            $scope.searchDeal = false;
        }, 1000);

    };

    $scope.update = function() {
        var obj = {
            "id": $scope.consumer.reg_id,
            "dealerId": $scope.user.dealerId
        }
        ConsumerService.updateDealer(obj).then(function(response) {
            if (response.status == 'OK') {
                updateUser();
            }
        }, function(error) {
            AlertService.alert("Error Updating Dealer", 'md-warn');
            console.log("error updating dealer");
        })
    }

    var updateUser = function() {
        var obj = {
            "email": $scope.consumer.email
        }
        LoginService.getConsumer(obj).then(function(response) {
            SessionService.setConsumerSession(response);
            AlertService.alert("Dealer Updated Successfully", 'md-primary');
            getCurrentDealer();
            $scope.reset();
        }, function(response) {
            console.log("errror");
        });
    };

    $scope.reset = function() {
        $scope.user = {};
        $scope.dealerForm.$setUntouched();
    }

    var init = function() {
        getCurrentDealer();
    };
    init();
}]);
