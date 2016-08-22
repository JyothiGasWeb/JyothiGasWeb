/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('CheckoutCtrl', ['$scope', 'ngCart', function($scope, ngCart) {

	ngCart.isSuccess = false;
}]);
