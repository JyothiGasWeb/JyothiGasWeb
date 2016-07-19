angular.module('medRepApp')
    .controller('AddContactCtrl', ['$scope', '$state', 'ContactService', function($scope, $state, ContactService) {


        var getContactByCity = function() {
            ContactService.getContactByCity().then(function(response) {
                console.log(response);
                $scope.contacts = response;
            }, function(error) {
                console.log("error")
            })
        };

        $scope.addContact = function(id){
        	var contactObj = {
        		"connIdList": [id]
        	}
        	ContactService.addContacts(contactObj).then(function(response){
        		getContactByCity();
        	}, function(response){
        		console.log("error");
        	})
        };

        var init = function() {
            getContactByCity();
        };

        init();
    }])
