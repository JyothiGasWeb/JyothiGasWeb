angular.module('clientApp')
    .factory('RegisterService', ['$q', 'RegisterFactory', function($q, RegisterFactory) {
        var regService = {};
        regService.register = function(userObj) {
            var deferred = $q.defer();
            RegisterFactory.consumerRegister().save(userObj, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.registerDealer = function(dealerObj) {
            var deferred = $q.defer();
            RegisterFactory.dealerRegister().save(dealerObj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.validateOtp = function(otp) {
            var deferred = $q.defer();
            RegisterFactory.otpValidation().get(otp, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.regenerateOtp = function(mobileno) {
            var deferred = $q.defer();
            RegisterFactory.recreateOTP().get({ 'mobileNumber': mobileno }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.getAllDealers = function(roleId) {
            var deferred = $q.defer();
            RegisterFactory.dealers().get({'roleId': roleId},function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };



        return regService;
    }])
    .factory('RegisterFactory', ['$resource', 'APP_CONFIG', function($resource, APP_CONFIG) {
        var regFactory = {};
        regFactory.consumerRegister = function() {
            return $resource(APP_CONFIG.API_URL + 'register', {}, {
                'save': {
                    method: 'POST'
                }

            })
        }

        regFactory.dealerRegister = function() {
            return $resource(APP_CONFIG.API_URL + 'register', {}, {
                'save': {
                    method: 'POST'
                }

            })
        };

        regFactory.otpValidation = function() {
            return $resource(APP_CONFIG.API_URL + 'verifyMobileNo', {}, {
                'get': {
                    method: 'GET'
                },
                'post': {
                    method: 'POST'
                }

            })
        };

        regFactory.recreateOTP = function() {
            return $resource(APP_CONFIG.API_URL + 'recreateOTP/:mobileNumber', {
                mobileNumber: '@mobileNumber'
            }, {
                'get': {
                    method: 'GET'
                }

            })
        };
        regFactory.dealers = function() {
            return $resource(APP_CONFIG.API_URL + 'getUserList', {

            }, {
                'get': {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        return regFactory;
    }]);
