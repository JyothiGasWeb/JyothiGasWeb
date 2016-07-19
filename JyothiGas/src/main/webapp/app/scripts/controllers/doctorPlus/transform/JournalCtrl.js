
angular.module('medRepApp')
    .controller('JournalCtrl', ['$scope', '$http', '$state', function($scope, $http, $state) {

        $scope.view = "cards";
        var getList = function() {
            $http.get('app/data/journalList.json').success(function(data) {
                $scope.newsList = data;
            });
        };

        $scope.manage = function(list) {
            $state.go('transformView', {
                'from': 'journal',
                'id': list.id
            });
        };

        $scope.toggleView = function(type) {
            $scope.view = type;
        };

        var init = function() {
            getList();
        };
        init();

    }])
