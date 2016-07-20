angular.module('clientApp')
    .controller('ForgotPasswCtrl', ['$scope', 'RegisterService', '$state', function($scope, RegisterService, $state) {

    	$scope.medRepObj = {};
    	
    	$scope.forgotPassword = function(){
    		
    	};

    	$scope.back = function(){
    		$state.go('login');
    	}

    }]);
