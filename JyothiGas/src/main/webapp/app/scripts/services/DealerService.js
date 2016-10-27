angular.module('clientApp')
    .factory('DealerService', ['$q', 'DealerFactory', 'SessionService', 'APP_CONFIG', function($q, DealerFactory, SessionService, APP_CONFIG) {
        var conService = {};

        conService.getSubRegionsList = function() {
            var deferred = $q.defer();

            var dealerId = SessionService.getConsumerSession().consumer.consumer_id;
            DealerFactory.subRegionsList().get({ 'dealerId': dealerId }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.bookRefill = function(obj) {
            var deferred = $q.defer();
            DealerFactory.refill().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.bookAppliances = function(obj) {
            var deferred = $q.defer();
            DealerFactory.appliances().post({ 'method': 'bookDealerAppliances' }, obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.cylinderBookings = function(year) {
            var deferred = $q.defer();
            var bookToId = SessionService.getConsumerSession().consumer.reg_id;
            DealerFactory.cyliders().get({ 'method': 'getCylinderBookingsFY', 'bookToID': bookToId, 'year': year }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.cylinderSold = function(year) {
            var deferred = $q.defer();
            var bookToId = SessionService.getConsumerSession().consumer.reg_id;
            DealerFactory.cyliders().get({ 'method': 'getCylinderSoldFY', 'bookToID': bookToId, 'year': year }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.purchaseReport = function(year) {
            var deferred = $q.defer();
            var bookToId = SessionService.getConsumerSession().consumer.reg_id;
            DealerFactory.reports().list({ 'method': 'getPurchaseReport', 'bookToId': bookToId, 'year': year }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.salesReport = function(year) {
            var deferred = $q.defer();
            var bookToId = SessionService.getConsumerSession().consumer.reg_id;
            DealerFactory.reports().list({ 'method': 'getSalesReport', 'bookToId': bookToId, 'year': year }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };



        return conService;
    }])
    .factory('DealerFactory', ['$resource', 'APP_CONFIG', 'SessionService', function($resource, APP_CONFIG, SessionService) {

        var conFact = {};

        conFact.refill = function() {
            return $resource(APP_CONFIG.API_URL + 'bookDealerRefill', {}, {
                'post': {
                    method: 'POST',
                }

            })
        };

        conFact.subRegionsList = function() {
            return $resource(APP_CONFIG.API_URL + 'getDealerSubRegions', {}, {
                'get': {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        conFact.appliances = function() {
            return $resource(APP_CONFIG.API_URL + ':method', {
                method: '@method'
            }, {
                'get': {
                    method: 'GET',
                    isArray: true
                },
                'post': {
                    method: 'POST'
                }

            })
        };

        conFact.cyliders = function() {
            return $resource(APP_CONFIG.API_URL + ':method', {
                method: '@method'
            }, {
                'get': {
                    method: 'GET'
                },
                'post': {
                    method: 'POST'
                },
                list: {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        conFact.reports = function() {
            return $resource(APP_CONFIG.API_URL + ':method', {
                method: '@method'
            }, {
                'get': {
                    method: 'GET'
                },
                'post': {
                    method: 'POST'
                },
                list: {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        return conFact;
    }])
