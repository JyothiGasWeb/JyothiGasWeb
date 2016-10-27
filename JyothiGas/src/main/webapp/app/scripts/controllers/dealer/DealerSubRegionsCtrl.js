angular.module('clientApp')
    .controller('DealerSubRegionsCtrl', ['$scope', 'DealerService', function($scope, DealerService) {
    	
    	$scope.areaList = [];
    	var getList = function(){
    		DealerService.getSubRegionsList().then(function(response){
    			$scope.areaList = response;
    			console.log(response);
    		}, function(error){
    			console.log("Error retrieving sub regions list");
    		})
    	};

    	var init = function(){
    		getList();
    	};
    	init();
    }]);
