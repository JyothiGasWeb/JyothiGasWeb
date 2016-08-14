/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('ProfileCtrl', ['$scope', 'SessionService', function($scope, SessionService) {

	var getConsumer = function(){
		$scope.consumer = SessionService.getConsumerSession().consumer;
	}
	var init = function(){
		getConsumer();
	};
	init();
}]);
