angular.module('medRepApp')
    .controller('PendingGroupsCtrl', ['$scope', 'GroupService', '$state', function($scope, GroupService, $state) {

        $scope.pendingGroups = [];
        $scope.showGroups = true;

        var getPendingGroups = function() {
            /*$http.get('app/data/pendingGroups.json').success(function(data) {
                $scope.pendingGroups = data;
            });*/
            GroupService.getPendingGroups().then(function(response) {
                $scope.pendingGroups = response;
            }, function(response) {
                console.log(response);
            })
        };

        $scope.manageGroup = function(groupId) {
            $state.go('groupView', {
                "from": "pendingGroups",
                "id": groupId
            });
        }
        var init = function() {
            getPendingGroups();
        };
        init();
    }])
