angular.module('medRepApp')
    .controller('SuggestedGroupsCtrl', ['$scope', 'GroupService', '$state', function($scope, GroupService, $state) {

        $scope.suggestedGroups = [];
        $scope.showGroups = true;

        var getSuggestedGrps = function() {
            GroupService.suggestedGroups().then(function(response){
                $scope.suggestedGroups = response;
            }, function(response){
                console.log("error");
            })
        };

        $scope.manageGroup = function(groupId) {
            $state.go('groupView', {
                "from": "suggestedGroups",
                "id": groupId
            });
        }
        var init = function() {
            getSuggestedGrps();
        };
        init();
    }]);
