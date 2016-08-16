/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('SurrenderCtrl', ['$scope', '$mdDialog', function($scope, $mdDialog) {

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
            
        }, function() {
            
        });
    };

    var init = function() {

    };
    init();
}]);
