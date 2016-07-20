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

        loginService.getUser = function(token) {
            var deferred = $q.defer();
            LoginFactory.user().get({ 'token': token }, function(success) {
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
        }

        return loginService;
    }])
    .factory('LoginFactory', ['$resource', 'APP_CONFIG', function($resource, APP_CONFIG) {

        var loginFact = {};
        loginFact.authenticate = function() {
            return $resource(APP_CONFIG.API_URL + 'authenticate', {}, {
                'save': {
                    method: 'POST'
                }

            })
        };

        loginFact.user = function() {
            return $resource(APP_CONFIG.API_URL + 'getUserPreferences', {}, {
                'get': {
                    method: 'GET',
                    isArray: true
                }

            })
        };

        loginFact.rereshToken = function() {
            return $resource(APP_CONFIG.API_URL + 'refresh', {}, {
                'get': {
                    method: 'GET'
                }

            })
        }


        return loginFact;
    }])
