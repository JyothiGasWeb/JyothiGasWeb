/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('SurrenderCtrl', ['$scope', '$mdDialog', 'ConsumerService', 'SessionService', 'AlertService', function($scope, $mdDialog, ConsumerService, SessionService, AlertService) {

    var consumer = SessionService.getConsumerSession().consumer;

    $scope.surrender = function(ev) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.confirm()
            .title('Would you like to surrender your connection?')
            .textContent('')
            .ariaLabel('Lucky day')
            .targetEvent(ev)
            .ok('Please do it!')
            .cancel('No');
        $mdDialog.show(confirm).then(function() {
            var obj = {
                "id": consumer.reg_id,
                "surrenderInfo": "Relocation"
            }
            ConsumerService.surrenderConnection(obj).then(function(response) {
                if (response.status == 'OK') {
                    AlertService.alert("Connection surrenderred Successfully", 'md-primary', 100000);
                }
            }, function(error) {
                console.log("Error");
            })
        }, function() {

        });
    };

    var init = function() {

    };
    init();
}]);
