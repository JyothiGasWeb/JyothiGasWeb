angular.module('medRepApp')
    .controller('TransformViewCtrl', ['$scope', '$stateParams', '$state', function($scope, $stateParams, $state) {

        var comeFrom = $stateParams.from;

        $scope.back = function() {
            var url = 'doctorPlusDash.transform.' + comeFrom;
            $state.go(url)
        }
        var init = function() {
            $scope.item = {
                "id": "1",
                "title": "Best cancer Screening methods",
                "description": "The titles of Washed Out's breakthrough song and the first single from Paracosm share the two most important words in Ernest Greene's musical language: feel it. It's a simple request, as well Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo onsequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non roident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                "imagePath": "https://material.angularjs.org/latest/img/washedout.png",
                "imageData": "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"
            }
        };

        $scope.share = function(){
            //API call for Share
            $state.go('doctorPlusDash.share');
        }
        init();
    }]);
