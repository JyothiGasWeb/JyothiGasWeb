/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('AppliancesCtrl', ['$scope', 'ConsumerService', function($scope, ConsumerService) {


	var getAppliances = function() {
        ConsumerService.getAppliances().then(function(response) {
            $scope.appliances = response;
        }, function(error) {

        })
    }

	var init = function(){
		getAppliances();
	};
	init();
}]);
