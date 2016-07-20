angular.module('clientApp')
    .factory('RegisterService', ['$q', 'RegisterFactory', function($q, RegisterFactory) {
        var regService = {};
        regService.registerDoctor = function(doctorObj) {
            var deferred = $q.defer();
            RegisterFactory.doctorRegister().save(doctorObj, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.registerCompany = function(companyObj) {
            var deferred = $q.defer();
            RegisterFactory.companyRegister().save(companyObj, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.validateOtp = function(otp, mobileno) {
            var deferred = $q.defer();
            RegisterFactory.otpValidation().get({ 'token': otp, 'number': mobileno }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        regService.regenerateOtp = function(mobileno) {
            var deferred = $q.defer();
            RegisterFactory.recreateOTP().get({'mobileNumber': mobileno}, function(success) {
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
        regFactory.doctorRegister = function() {
            return $resource(APP_CONFIG.API_URL + 'signupDoctor', {}, {
                'save': {
                    method: 'POST'
                }

            })
        }

        regFactory.companyRegister = function() {
            return $resource(APP_CONFIG.API_URL + 'companyRegistration', {}, {
                'save': {
                    method: 'POST'
                }

            })
        };

        regFactory.otpValidation = function() {
            return $resource(APP_CONFIG.API_URL + 'verifyOTP', {}, {
                'get': {
                    method: 'GET'
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

        return regFactory;
    }]);
