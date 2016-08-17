/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('MechanicServiceCtrl', ['$scope', 'SessionService', 'ConsumerService', 'AlertService', function($scope, SessionService, ConsumerService, AlertService) {

    $scope.current = {};
    $scope.user = {};
    $scope.mechanicsList = [];
    var getCurrentAdd = function() {
        $scope.current = SessionService.getConsumerSession().consumer;
        $scope.user.consumerId = $scope.current.consumer_id;
        getMechanics($scope.current.dealerId);
    };

    var getMechanics = function(dealerId) {
        var obj = {
            "dealerId": dealerId
        }
        ConsumerService.getMechanics(obj).then(function(response) {
            $scope.mechanicsList = response;
        }, function(error) {
            console.log("error getting mechanics List")
        });
    };

    $scope.addService = function() {
        ConsumerService.addMechanicService($scope.user).then(function(response) {
            if (response.status == 'Success') {
                AlertService.alert("Mechanic Service added Successfully", 'md-primary', "5000");
                $scope.user = {};
            }
        }, function(error) {
            console.log("error getting mechanics List")
        });
    }

    $scope.reset = function() {
        $scope.user = {
            "comments": ""
        };
    }
    var init = function() {
        getCurrentAdd();
    };
    init();
}]);
