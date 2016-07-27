angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', function($scope) {
    	
    	$scope.isForm = true;
    	$scope.bookRefill = function(){
    		$scope.isForm = false;
    	};

        $scope.reset = function(){
            $scope.user = {};
        }

    	var init = function(){
    		
    	}
    	init();
    }])
