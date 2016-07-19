angular.module('medRepApp')
    .controller('DoctorPlusDashCtrl', ['$scope', function($scope) {
        $scope.pageName = "transform";
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
            if (toState.data)
                $scope.currentTab = toState.data.selectedTab;
        });
    }]);
