angular.module('clientApp')
    .factory('ConsumerService', ['$q', 'ConsumerFactory', 'SessionService', function($q, ConsumerFactory, SessionService) {
        var conService = {};

        conService.bookRefill = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.refill().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        return conService;
    }])
    .factory('ConsumerFactory', ['$resource', 'APP_CONFIG', function($resource, APP_CONFIG) {

        var conFact = {};

        conFact.refill = function() {
            return $resource(APP_CONFIG.API_URL + 'bookCylinder', {}, {
                'post': {
                    method: 'POST',
                }

            })
        };

        return conFact;
    }])
