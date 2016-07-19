angular.module('medRepApp')
    .factory('SearchDrugService', ['$q', 'SearchDrugFactory', function($q, SearchDrugFactory) {

        var service = {};

        service.searchQuery = function(text) {
            var deferred = $q.defer();
            SearchDrugFactory.drugs().list({ 'method': 'typeahead.json', 'id': text, 'limit': 10 }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        }

        return service;

    }])
    .factory('SearchDrugFactory', ['$resource', function($resource) {

        var factorie = {};
        var url = "http://www.truemd.in/api/"

        factorie.drugs = function() {
            return $resource(url + ':method?key=77b06e379a6f984a1f1ec091ce70f4', {
                method: '@method'
            }, {
                'list': {
                    method: 'GET',
                    isArray: true
                },
                'create': {
                    method: 'POST'
                }

            })
        }
        return factorie;

    }])
