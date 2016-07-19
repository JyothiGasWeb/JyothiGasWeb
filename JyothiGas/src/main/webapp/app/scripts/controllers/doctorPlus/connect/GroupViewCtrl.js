angular.module('medRepApp')
    .controller('GroupViewCtrl', ['$scope', '$state', '$stateParams', 'GroupService', function($scope, $state, $stateParams, GroupService) {


        var id = $stateParams.id;
        var from = $stateParams.from;
        $scope.showJoin = false;
        $scope.showExit = false;
        $scope.pendingGroups = false;

        if (from == 'suggestedGroups') {
            $scope.showJoin = true;
            $scope.showExit = false;
        } else if (from == 'myGroups') {
            $scope.showJoin = false;
            $scope.showExit = true;
        } else if (from == 'pendingGroups') {
            $scope.pendingGroups = true;
        };


        $scope.updateStatus = function(message) {

            var group = {
                "group_id": id,
                "member_id": "2",
                "status": message
            }
            GroupService.updateStatus(group).then(function(response) {
                $scope.back();
            }, function(response) {
                console.log("error")
            })
        };

        var getDetails = function() {
            GroupService.viewGroup(id).then(function(response) {
                $scope.group = response[0];
            }, function(error) {
                console.log(error);
            });
        };

        var getPendingMembers = function() {
            GroupService.getPendingMembers(id).then(function(response) {
                //$scope.group = response[0];
                $scope.pendingMembers = response;
            }, function(error) {
                console.log(error);
            });
        };

        $scope.updateMemberStatus = function(memberId, status) {
            var groupObj = {
                "group_id": id,
                "member_id": memberId,
                "status": status
            };
            GroupService.updateStatus(groupObj).then(function(response) {
                //$scope.group = response[0];
                getPendingMembers();
            }, function(error) {
                console.log(error);
            });
        }

        $scope.back = function() {
            var url = "doctorPlusDash.connect." + from;
            $state.go(url);
        }

        var init = function() {
            console.log("here")
            getDetails();
            getPendingMembers();
        };
        init();
    }]);
