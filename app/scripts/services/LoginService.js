angular.module('clientApp')
    .factory('LoginService', ['$q', 'LoginFactory', 'SessionService', function($q, LoginFactory, SessionService) {
        var loginService = {};

        loginService.isAuthenticated = function() {
            return !!SessionService.checkSession();
        };

        loginService.hasSessionObject = function() {

            return !!SessionService.getSessionObject();

        };

        loginService.login = function(params) {
            var deferred = $q.defer();
            LoginFactory.authenticate().save(params, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        };

        loginService.getConsumer = function(obj) {
            var deferred = $q.defer();
            LoginFactory.consumer().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        }

        loginService.getRefreshToken = function(token) {
            var deferred = $q.defer();
            LoginFactory.rereshToken().get({ 'token': token }, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        };

        loginService.newOtp = function(obj) {
            var deferred = $q.defer();
            LoginFactory.otp().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        };

        loginService.forgotPasswd = function(obj) {
            var deferred = $q.defer();
            LoginFactory.passwd().post(obj, function(success) {
                deferred.resolve(success);
            }, function(error) {

                deferred.reject(error);
            });
            return deferred.promise;
        }

        return loginService;
    }])
    .factory('LoginFactory', ['$resource', 'APP_CONFIG', function($resource, APP_CONFIG) {

        var loginFact = {};
        loginFact.authenticate = function() {
            return $resource(APP_CONFIG.API_URL + 'login', {}, {
                'save': {
                    method: 'POST'
                }

            })
        };

        loginFact.consumer = function() {
            return $resource(APP_CONFIG.API_URL + 'getConsumerDetails', {}, {
                'post': {
                    method: 'POST',
                }

            })
        };

        loginFact.rereshToken = function() {
            return $resource(APP_CONFIG.API_URL + 'refresh', {}, {
                'get': {
                    method: 'GET'
                }

            })
        };

        loginFact.otp = function() {
            return $resource(APP_CONFIG.API_URL + 'getNewSMSOTP', {}, {
                'post': {
                    method: 'POST'
                }

            })
        };

        loginFact.passwd = function() {
            return $resource(APP_CONFIG.API_URL + 'forgotPassword', {}, {
                'post': {
                    method: 'POST'
                }

            })
        }


        return loginFact;
    }])
