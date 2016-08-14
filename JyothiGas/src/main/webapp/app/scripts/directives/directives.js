angular.module('clientApp')
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
    })
    .directive('compareTo', function() {
        return {
            require: "ngModel",
            scope: {
                otherModelValue: "=compareTo"
            },
            link: function(scope, element, attributes, ngModel) {

                ngModel.$validators.compareTo = function(modelValue) {
                    return modelValue == scope.otherModelValue;
                };

                scope.$watch("otherModelValue", function() {
                    ngModel.$validate();
                });
            }
        };
    })
