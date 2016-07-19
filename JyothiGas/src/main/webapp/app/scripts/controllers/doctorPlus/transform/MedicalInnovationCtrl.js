
angular.module('medRepApp')
    .controller('MedicalInnovationCtrl', ['$scope', '$http', '$state', function($scope, $http, $state) {

        $scope.view = "cards";
        var getBBCList = function() {
            $http.get('app/data/bbcList.json').success(function(data) {
                $scope.newsList = data;
            });
        };

        $scope.toggleView = function(type) {
            $scope.view = type;
        };

        $scope.manage = function(list) {
            $state.go('transformView', {
                'from': 'medicalInnovation',
                'id': list.id
            });
        };

        var init = function() {
            getBBCList();
        };
        init();

    }])
