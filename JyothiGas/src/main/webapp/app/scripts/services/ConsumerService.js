angular.module('clientApp')
    .factory('ConsumerService', ['$q', 'ConsumerFactory', 'SessionService', 'Upload', 'APP_CONFIG', function($q, ConsumerFactory, SessionService, Upload, APP_CONFIG) {
        var conService = {};

        conService.updateFile = function(id, file) {
            var deferred = $q.defer();
            Upload.upload({
                url: APP_CONFIG.API_URL + 'uploadFile',
                file: file,
            }).success(function(data, status, headers, config) {
                console.log(data);
                deferred.resolve(data);

            }).error(function(error) {
                deferred.reject();

            });
            return deferred.promise;
        };

        conService.bookRefill = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.refill().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getConsumerBookings = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.bookings().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.changeAddress = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.address().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.surrenderConnection = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.surrender().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getMechanics = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.mechanics().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.addMechanicService = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.mechanicService().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getConnectionTypes = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.connectionTypes().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.updateConsumer = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.consumer().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getAllNotifications = function() {
            var deferred = $q.defer();
            ConsumerFactory.notifications().get(function(success) {
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

        conFact.bookings = function() {
            return $resource(APP_CONFIG.API_URL + 'getBookingByConsumer', {}, {
                'post': {
                    method: 'POST',
                    isArray: true
                }

            })
        };

        conFact.address = function() {
            return $resource(APP_CONFIG.API_URL + 'updateConsumer', {}, {
                'post': {
                    method: 'POST'
                }

            })
        };

        conFact.surrender = function() {
            return $resource(APP_CONFIG.API_URL + 'surrenderConnection', {}, {
                'post': {
                    method: 'POST'
                }

            })
        };

        conFact.mechanics = function() {
            return $resource(APP_CONFIG.API_URL + 'getMechanicByDealerID', {}, {
                'post': {
                    method: 'POST',
                    isArray: true
                }

            })
        };

        conFact.mechanicService = function() {
            return $resource(APP_CONFIG.API_URL + 'addMechanicService', {}, {
                'post': {
                    method: 'POST'
                }

            })
        };

        conFact.connectionTypes = function() {
            return $resource(APP_CONFIG.API_URL + 'getConnectionsByType', {}, {
                'post': {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        conFact.consumer = function() {
            return $resource(APP_CONFIG.API_URL + 'updateConsumer', {}, {
                'post': {
                    method: 'POST',
                }

            })
        };

        conFact.notifications = function() {
            return $resource(APP_CONFIG.API_URL + 'getAllNotification', {}, {
                'get': {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        return conFact;
    }])
