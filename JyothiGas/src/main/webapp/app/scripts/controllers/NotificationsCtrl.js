angular.module('medRepApp')
    .controller('NotificationsCtrl', ['$scope', function($scope) {

    	console.log($scope.currentPage);

    	
    	var getNotifications = function(){

    	};

    	var init = function(){
    		getNotifications();
    	};

    	init();
    }]);
