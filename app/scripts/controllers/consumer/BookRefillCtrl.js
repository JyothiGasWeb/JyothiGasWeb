angular.module('clientApp')
    .controller('BookRefillCtrl', ['$scope', function($scope) {
    	
    	$scope.isForm = true;
        var getDetails = function(){
            $scope.refillObj = {
                "dealerName": "Parvathi Enterprises",
                "consumerNo": "1234545",
                "consumerName": "Nivetha Thomas",
                "address": "No. 11, SSI Area 5th Block",
                "contact": "9030236291"
            };
            $scope.productTypes = ["12kg- Rs.800", "15kg- Rs.1500"];
            $scope.cylinders = 1;
        };

    	$scope.bookRefill = function(){
    		$scope.isForm = false;
    	};

        $scope.reset = function(){
            $scope.user = {};
        }

    	var init = function(){
    	   getDetails();	
    	};
    	init();
    }])
