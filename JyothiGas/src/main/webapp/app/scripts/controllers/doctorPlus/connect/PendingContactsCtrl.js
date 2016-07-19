angular.module('medRepApp')
    .controller('PendingContactsCtrl', ['$scope', 'ContactService', '$state', function($scope, ContactService, $state) {

        $scope.$parent.pageName = "connect";
        $scope.view = "cards";

        var getSuggestedContacts = function() {
            ContactService.getPendingContacts().then(function(response){
                 $scope.pendingContacts = response;
            }, function(response){
                console.log(response);
            })
        };

        $scope.toggleSearch = function() {
            $scope.showSearch = !$scope.showSearch;
        };
        $scope.toggleView = function(type) {
            $scope.view = type;
        };

        $scope.updateStatus = function(userId, status){
            var connection = {
                "connList": [userId],
                "status": status
            }
            ContactService.updateStatus(connection).then(function(response){
                console.log(response);
            }, function(error){
                
            })
        };

        $scope.manageContact = function(id) {
            $state.go('connectView', {
                "from": "pendingContacts",
                "id": id
            })
        }

        var init = function() {
            getSuggestedContacts();
        };

        init();
    }]);
