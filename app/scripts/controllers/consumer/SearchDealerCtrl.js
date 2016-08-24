/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('SearchDealerCtrl', ['$scope', 'RegisterService', 'ConsumerService', 'AlertService', 'SessionService', 'LoginService', function($scope, RegisterService, ConsumerService, AlertService, SessionService, LoginService) {

    $scope.showDealers = false;
    var user = SessionService.getConsumerSession().consumer;
    var getAllDealers = function() {
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    $scope.updateDealer = function(item) {
        //Need to integrate with API
        var obj = {
            "id": user.reg_id,
            "dealerId": item.id
        }
        ConsumerService.updateDealer(obj).then(function(response) {
            if (response.status == 'OK') {
                updateUser();
            }
        }, function(error) {
            AlertService.alert("Error Updating Dealer", 'md-warn');
            console.log("error updating dealer");
        })
    };

    var updateUser = function() {
        var obj = {
            "email": user.email
        }
        LoginService.getConsumer(obj).then(function(response) {
            SessionService.setConsumerSession(response);
            AlertService.alert("Dealer Updated Successfully", 'md-primary');
            //$scope.reset();
        }, function(response) {
            console.log("errror");
        });
    };

    $scope.filterDealers = function() {
        $scope.dealers = [];
        for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
            if ($scope.dealer.areaCode == $scope.availableDealers[i].dealer_area_code) {
                $scope.dealers.push($scope.availableDealers[i]);
            }
        };
        $scope.showDealers = true;
    };

    var init = function() {
        getAllDealers();
    };
    init();
}]);
