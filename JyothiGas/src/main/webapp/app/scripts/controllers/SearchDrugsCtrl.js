angular.module('medRepApp')
    .controller('SearchDrugsCtrl', ['$scope', 'SearchDrugService', '$http', '$q', '$timeout', function($scope, SearchDrugService, $http, $q, $timeout) {


        $scope.searchArr = [];
        var stock_hack

        function stock_search(data) {
            stock_hack = data;
            console.log(data);
        }
        $http.defaults.useXDomain = true;


        $scope.querySearch = function(text) {
            var deferred = $q.defer();
            if (text) {
                //var url = "http://www.truemd.in/api/typeahead.json?key=77b06e379a6f984a1f1ec091ce70f4&limit=10&id=" + text + '&format=json&callback=stock_search';
                $http({
                    method: "JSONP",
                    params: {
                        key: "77b06e379a6f984a1f1ec091ce70f4",
                        limit: 10,
                        id: text,
                        callback: "stock_search"
                    },
                    url: "http://www.truemd.in/api/typeahead.json",
                    isArray: true,
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded', 'Access-Control-Allow-Origin': '*' }
                })
                .success(function(data) {
                    //results = data;
                    $scope.searchArr = data;
                    console.log($scope.searchArr);
                    //deferred.resolve();
                }).error(function(data, status) {
                    console.log(stock_hack);
                });
            } else {
                return [];
            }
            //return deferred.promise;
        }
        var init = function() {
            var a = $scope.querySearch('A');
        };
        init();
    }]);
