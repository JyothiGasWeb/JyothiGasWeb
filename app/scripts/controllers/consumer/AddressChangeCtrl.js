/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('AddressChangeCtrl', ['$scope', 'SessionService', 'ConsumerService', 'AlertService', 'LoginService', function($scope, SessionService, ConsumerService, AlertService, LoginService) {

    $scope.current = {};
    $scope.user = {};
    var getCurrentAdd = function() {
        $scope.current = SessionService.getConsumerSession().consumer;
    }
   

    $scope.change = function() {
        var obj = {
            "id": $scope.current.reg_id,
            "address": $scope.user.newAddress,
            "dealerId": $scope.current.dealerId,
            "connectionTypeId": $scope.current.connectionTypeId,
            "areaCode": $scope.user.newPinCode
        };

        ConsumerService.changeAddress(obj).then(function(response) {
            if (response.status == 'OK') {

                updateUser();
            };
        }, function(error) {
            console.log("error updating Address");
        })
    };

    var updateUser = function() {
        var obj = {
            "email": $scope.current.email
        }
        LoginService.getConsumer(obj).then(function(response) {
            SessionService.setConsumerSession(response);
            AlertService.alert("Address updated sucessfully", "md-primary");
            getCurrentAdd();
            $scope.reset();
        }, function(response) {
            console.log("errror");
        });
    };

    $scope.reset = function() {
        $scope.user = {
            "newPinCode": "",
            "newAddress": ""
        };
    };

     var init = function() {
        getCurrentAdd();
    };
    init();
}]);
