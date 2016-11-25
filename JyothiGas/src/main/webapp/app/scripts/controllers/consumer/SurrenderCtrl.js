/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('SurrenderCtrl', ['$scope', '$mdDialog', 'ConsumerService', 'SessionService', 'AlertService', 'LoginService', function($scope, $mdDialog, ConsumerService, SessionService, AlertService, LoginService) {

    var consumer = SessionService.getConsumerSession().consumer;
    $scope.isSurrender = false;
    var getSurrender = function() {
        if (consumer && consumer.surrenderStatus == 'SURRENDERED') {
            $scope.isSurrender = true;
            $scope.surrenderedDate = new Date(consumer.surrender_Date);
        }
    }

    $scope.surrender = function(ev) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.confirm()
            .title('Would you like to surrender your connection?')
            .textContent('')
            .ariaLabel('Lucky day')
            .targetEvent(ev)
            .ok('Yes')
            .cancel('No');
        $mdDialog.show(confirm).then(function() {
            var obj = {
                "id": consumer.reg_id,
                "surrenderInfo": "Relocation"
            }
            ConsumerService.surrenderConnection(obj).then(function(response) {
                if (response.status == 'OK') {
                    updateUser();

                }
            }, function(error) {
                console.log("Error");
            })
        }, function() {

        });
    };

    var updateUser = function() {
        var obj = {
            "email": consumer.email
        }
        LoginService.getConsumer(obj).then(function(response) {
            SessionService.setConsumerSession(response);
            $scope.isSurrender = true;
            AlertService.alert("Connection surrenderred Successfully. Thanks for surrendering, your dealer will get in touch with you", 'md-primary', 100000);
            $scope.isTextSurrender = true;
            getSurrender();
            //$scope.reset();
        }, function(response) {
            console.log("errror");
        });
    };

    var init = function() {
        getSurrender();
    };
    init();
}]);
