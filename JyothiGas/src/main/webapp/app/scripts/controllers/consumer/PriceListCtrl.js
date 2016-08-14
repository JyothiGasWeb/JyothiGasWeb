/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('PriceListCtrl', ['$scope', 'SessionService', function($scope, SessionService) {


	var init = function(){
		$scope.connectionType = SessionService.getConsumerSession().consumer.connectionTypeName;
	};
	init();
}]);
