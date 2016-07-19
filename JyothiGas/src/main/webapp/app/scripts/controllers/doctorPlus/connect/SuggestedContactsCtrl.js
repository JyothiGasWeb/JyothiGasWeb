angular.module('medRepApp')
    .controller('SuggestedContactsCtrl', ['$scope', 'ContactService', '$state', function($scope, ContactService, $state) {

        $scope.$parent.pageName = "connect";
        $scope.view = "cards";

        var getSuggestedContacts = function() {
            ContactService.getSuggestedContacts().then(function(response){
                $scope.suggestedContacts = response;
            }, function(response){
                console.log(response);
            })
        };

        $scope.addContact = function(id){
            var contactObj = {
                "connIdList": [id]
            }
            ContactService.addContacts(contactObj).then(function(response){
                getSuggestedContacts();
            }, function(response){
                console.log("error");
            })
        };

        $scope.toggleSearch = function() {
            $scope.showSearch = !$scope.showSearch;
        };
        $scope.toggleView = function(type) {
            $scope.view = type;
        };

        $scope.manageContact = function(id) {
            $state.go('connectView', {
                "from": "suggestedContacts",
                "id": id
            })
        }

        var init = function() {
            getSuggestedContacts();
        };

        init();
    }]);
