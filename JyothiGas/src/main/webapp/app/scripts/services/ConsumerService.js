angular.module('clientApp')
    .factory('ConsumerService', ['$q', 'ConsumerFactory', 'SessionService', 'Upload', 'APP_CONFIG', function($q, ConsumerFactory, SessionService, Upload, APP_CONFIG) {
        var conService = {};

        conService.updateFile = function(id, docType, file) {
            var deferred = $q.defer();
            Upload.upload({
                url: APP_CONFIG.API_URL + 'uploadFile',
                data: { file: file, 'custId': id, 'docType': docType }
            }).success(function(data, status, headers, config) {
                deferred.resolve(data);

            }).error(function(error) {
                deferred.reject();

            });
            return deferred.promise;
        };

        conService.getDocumentDetails = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.documentDetail().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getProductType = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.productType().get(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getAppliances = function() {
            var deferred = $q.defer();
            ConsumerFactory.productType().get({'connectionTypeID': '0' }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
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
            ConsumerFactory.notifications().post(function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.bookConnection = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.connection().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.bookAppliances = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.appliances().post({ 'method': 'bookAppliances' }, obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.updateDealer = function(obj) {
            var deferred = $q.defer();
            ConsumerFactory.dealerUpdate().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        conService.getPriceList = function() {
            var deferred = $q.defer();
            ConsumerFactory.appliances().get({ 'method': 'getAppliances'}, function(success) {
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
            return $resource(APP_CONFIG.API_URL + 'bookRefill', {}, {
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
            return $resource(APP_CONFIG.API_URL + 'getAllNotification?userType=CONSUMER', {}, {
                'post': {
                    method: 'POST',
                    isArray: true
                }

            })
        };

        conFact.productType = function() {
            return $resource(APP_CONFIG.API_URL + 'getProductByType', {}, {
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

        conFact.connection = function() {
            return $resource(APP_CONFIG.API_URL + 'bookConnection', {}, {
                'post': {
                    method: 'POST'
                }

            })
        };

        conFact.dealerUpdate = function() {
            return $resource(APP_CONFIG.API_URL + 'updateDealer', {}, {
                'post': {
                    method: 'POST'
                }

            })
        };

        conFact.documentDetail = function() {
            return $resource(APP_CONFIG.API_URL + 'getDocumentDetail', {}, {
                'post': {
                    method: 'POST',
                    isArray: true
                }

            })
        };
        

        return conFact;
    }])
