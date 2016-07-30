angular.module('clientApp')
    .controller('RegisterNewCtrl', ['$scope', 'RegisterService', '$state', 'AlertService', '$mdDialog', '$q', function($scope, RegisterService, $state, AlertService, $mdDialog, $q) {

        $scope.querySearch = function(query) {
            var results = query ? $scope.dealers.filter(createFilterFor(query)) : $scope.dealers,
                deferred;
            if (self.simulateQuery) {
                deferred = $q.defer();
                $timeout(function() { deferred.resolve(results); }, Math.random() * 1000, false);
                return deferred.promise;
            } else {
                return results;
            }
        };

        $scope.selectedItem = null;

        function loadAll() {
            var dealers = 'Jyothi enterprises, Parvathi Enterprises';
            return dealers.split(/, +/g).map(function(state) {
                return {
                    value: state.toLowerCase(),
                    display: state
                };
            });
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return ($scope.dealers.indexOf(lowercaseQuery) === 0);
            };
        }

        function init() {
            $scope.dealers = loadAll();
        }

        init();

    }]);
