/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('AppliancesCtrl', ['$scope', 'ConsumerService', '_', function($scope, ConsumerService, _) {


	var getAppliances = function() {
        ConsumerService.getAppliances().then(function(response) {
            $scope.appliances = response;
            _.each($scope.appliances, function(item){
            	item.imgSrc = "app/images/"+ item.appliance_Name+".jpg";
            });
        }, function(error) {

        })
    }

	var init = function(){
		getAppliances();
	};
	init();
}]);
