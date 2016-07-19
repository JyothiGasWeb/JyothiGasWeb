angular.module('medRepApp')
    .directive('verticalScroll', function($rootScope) {
        return {
            restrict: 'A',
            link: function(scope, element) {
                scope.$watch(
                    function() {
                        return element[0].clientWidth;
                    },
                    function() {
                        $rootScope.$emit('resize');
                    }
                );
            }
        }
    });
