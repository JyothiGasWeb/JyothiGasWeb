/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('PriceListCtrl', ['$scope', 'SessionService', 'ConsumerService', function($scope, SessionService, ConsumerService) {


	var getPriceList = function(){
		ConsumerService.getPriceList().then(function(response){
			$scope.priceList = response;
		}, function(response){
			console.log("error getting priceList");
		})
	};

	var init = function(){
		$scope.connectionType = SessionService.getConsumerSession().consumer.connectionTypeName;
		getPriceList();
	};
	init();
}]);
