angular.module('medRepApp')
    .controller('MyContactsCtrl', ['$scope', 'ContactService', '$state', function($scope, ContactService, $state) {

        $scope.myContacts = [];
        $scope.$parent.pageName = "connect";
        $scope.view = "cards";
        $scope.showSearch = false;

        $scope.toggleSearch = function() {
            $scope.showSearch = !$scope.showSearch;
        };
        $scope.toggleView = function(type) {
            $scope.view = type;
        };

        $scope.manageContact = function(id) {
            $state.go('connectView', {
                "from": "myContacts",
                "id": id
            })
        };

        var getMyContacts = function() {
            ContactService.getMyContacts().then(function(response) {
                $scope.myContacts = response;
            }, function(response) {
                console.log(response);
            })
        };


        var init = function() {
            getMyContacts();
        };

        init();
    }]);
