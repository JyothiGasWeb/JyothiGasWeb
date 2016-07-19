angular.module('medRepApp')
    .controller('CaseStudiesCtrl', ['$scope', '$http', '$state', function($scope, $http, $state) {
        $scope.view = "cards";
        var getList = function() {
            $http.get('app/data/bbcList.json').success(function(data) {
                $scope.newsList = data;
            });
        };

        $scope.toggleView = function(type) {
            $scope.view = type;
        };

        $scope.manage = function(list) {
            $state.go('transformView', {
                'from': 'caseStudies',
                'id': list.id
            });
        };

        var init = function() {
            getList();
        };
        init();
    }]);
