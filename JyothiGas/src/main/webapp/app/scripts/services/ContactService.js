angular.module('medRepApp')
    .factory('ContactService', ['$q', 'ContactFactory', 'SessionService', function($q, ContactFactory, SessionService) {

        var contactServ = {};

        contactServ.getMyContacts = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            ContactFactory.contacts().list({'method': 'getMyContactList', 'token': token}, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        contactServ.getSuggestedContacts = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            ContactFactory.contacts().list({'method': 'getSuggestedContacts', 'token': token}, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        contactServ.getPendingContacts = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            ContactFactory.contacts().list({'method': 'fetchPendingConnections', 'token': token}, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        contactServ.getContactByCity = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            ContactFactory.contacts().list({'method': 'getAllContactsByCity', 'token': token}, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        contactServ.addContacts = function(contact) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            ContactFactory.contacts().create({'method': 'addContacts', 'token': token}, contact, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        contactServ.updateStatus = function(contact){ 
            var token = SessionService.getStoredUserToken();   
            var deferred = $q.defer();
            ContactFactory.contacts().create({'method': 'updateContactStatus', 'token': token}, contact, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        }

        

        return contactServ;
    }])
    .factory('ContactFactory', ['$resource', 'APP_CONFIG', function($resource, APP_CONFIG) {

        var contactFact = {};
        
        contactFact.contacts = function() {
            return $resource(APP_CONFIG.API_URL + ':method', {
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

        return contactFact;

    }])
