angular.module('medRepApp')
    .controller('clientApp', ['$scope', '$mdDialog', 'RegisterService', 'Number', function($scope, $mdDialog, RegisterService, Number) {

        $scope.regenerate = function() {
        	RegisterService.regenerateOtp(Number).then(function(response){
        		console.log(response);
        	}, function(response){
        		console.log(response);
        	})
        };

        $scope.cancel = function(){
            $mdDialog.cancel();
        }

        $scope.confirm = function() {
        	if($scope.otp && Number){
        		RegisterService.validateOtp($scope.otp, Number).then(function(response){
        			console.log(response);
        			$mdDialog.hide(response);
        		}, function(response){
        			console.log(response);
        		})
        	}
        }
    }])
