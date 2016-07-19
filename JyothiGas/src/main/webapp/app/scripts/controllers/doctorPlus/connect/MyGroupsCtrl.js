angular.module('medRepApp')
    .controller('MyGroupsCtrl', ['$scope', '$http', '$state', 'GroupService', '$mdDialog', 'AlertService', function($scope, $http, $state, GroupService, $mdDialog, AlertService) {

        $scope.myGroups = [];
        $scope.showGroups = true;
        $scope.groupobj = {};

        var getMyGroups = function() {
            GroupService.getMyGroups().then(function(response) {
                $scope.myGroups = response;
            })
        };

        $scope.addGroup = function(show) {
            $scope.showGroups = show;
        };

        $scope.deleteGroup = function(group) {
            var groupObj = {
                "group_id": group.group_id
            };
            var confirm = $mdDialog.confirm()
                .title('Would you like to delete group?')
                .targetEvent('body')
                .ok('Ok')
                .cancel('Cancel');
            $mdDialog.show(confirm).then(function() {
                GroupService.deleteGroup(groupObj).then(function(response) {
                    var index = $scope.myGroups.indexOf(group);
                    $scope.myGroups.splice(index, 1);
                    AlertService.alert("Group Delete Successfully", "md-primary");
                }, function(response) {

                });
            }, function() {
                $scope.status = 'Cancel';
            });
        };

        $scope.createGroup = function() {
            //Add Service
            var isNew = true;
            var group = {
                "group_name": $scope.groupobj.group_name,
                "group_long_desc": $scope.groupobj.group_long_desc,
                "group_short_desc": $scope.groupobj.group_short_desc,
            }
            if ($scope.groupobj.group_id) {
                group.group_id = $scope.groupobj.group_id;
                isNew = false;
            }
            if ($scope.groupobj.group_img_data) {
                if (!isNew && !$scope.groupobj.group_img_data.base64) {
                    group.group_img_data = $scope.groupobj.group_img_data;
                } else {
                    group.group_img_data = $scope.groupobj.group_img_data.base64;
                }
                group.group_mimeType = "png";
            };

            if (isNew) {
                GroupService.createGroup(group).then(function(response) {
                    getMyGroups();
                    $scope.showGroups = true;
                })
            } else {
                GroupService.updateGroup(group).then(function(response) {
                    getMyGroups();
                    $scope.showGroups = true;
                })
            }

        };

        $scope.editGroup = function(group) {
            $scope.groupobj = group;
            $scope.showGroups = false;
        };

        $scope.manageGroup = function(groupId) {
            $state.go('groupView', {
                "from": "myGroups",
                "id": groupId
            });
        }
        var init = function() {
            getMyGroups();
        };
        init();
    }]);
