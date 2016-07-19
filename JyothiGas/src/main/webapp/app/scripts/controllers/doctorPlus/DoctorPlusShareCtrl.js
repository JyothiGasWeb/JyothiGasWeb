angular.module('medRepApp')
    .controller('DoctorPlusShareCtrl', ['$scope', '$http', '$mdDialog', '$state', function($scope, $http, $mdDialog, $state) {

        $scope.$parent.pageName = "share";
        $scope.list = [];

        var getList = function() {
            $http.get('app/data/shareList.json').success(function(data) {
                $scope.list = data;
            });
        };

        $scope.share = function() {

        };

        $scope.comment = function(ev, post) {
            $mdDialog.show({
                    controller: function($scope, $mdDialog) {
                        $scope.commentObj = {};
                        //$scope.name = "";
                        $scope.create = function() {
                            $mdDialog.hide();
                        };

                        $scope.closeDialog = function() {
                            $mdDialog.cancel();
                        }
                    },
                    templateUrl: 'app/views/doctorPlus/commentPopup.html',
                    parent: angular.element(document.body),
                    targetEvent: ev
                })
                .then(function(comment) {

                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        $scope.view = function(post) {
            $state.go('doctorPlusDash.shareDetails', {
                postId: post.id
            });

        }
        var init = function() {
            getList();
        };
        init();
    }])
