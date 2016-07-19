angular.module('medRepApp')
    .factory('GroupService', ['$q', 'Upload', 'SessionService', 'APP_CONFIG', 'GroupFactory', function($q, Upload, SessionService, APP_CONFIG, GroupFactory) {

        var groupServ = {};

        groupServ.createGroup = function(group) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().create({ 'method': 'createGroup', 'token': token }, group, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.updateGroup = function(group) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().update({ 'method': 'updateGroup', 'token': token }, group, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.getMyGroups = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().list({ 'method': 'getGroups', 'token': token }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.deleteGroup = function(group) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().create({ 'method': 'deleteGroup', 'token': token }, group, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.getPendingGroups = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().list({ 'method': 'getPendingGroups', 'token': token }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.suggestedGroups = function() {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().list({ 'method': 'getSuggestedGroups', 'token': token }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.viewGroup = function(groupId) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().list({ 'method': 'getGroupMembers', 'groupId': groupId, 'token': token }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.updateStatus = function(group) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().update({ 'method': 'updateMemberStatus', 'token': token }, group, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        groupServ.getPendingMembers = function(groupId) {
            var token = SessionService.getStoredUserToken();
            var deferred = $q.defer();
            GroupFactory.groups().list({ 'method': 'getPendingGroupMembers', 'groupId': groupId, 'token': token }, function(success) {
                deferred.resolve(success);
            }, function(error) {
                deferred.reject(error);
            });
            return deferred.promise;
        };

        return groupServ;

    }])
    .factory('GroupFactory', ['$resource', 'SessionService', 'APP_CONFIG', function($resource, SessionService, APP_CONFIG) {

        var groupFact = {};
        groupFact.groups = function() {
            return $resource(APP_CONFIG.API_URL + ':method/:groupId', {
                method: '@method',
                groupId: '@groupId'
            }, {
                'list': {
                    method: 'GET',
                    isArray: true
                },
                'create': {
                    method: 'POST'
                },
                'update': {
                    method: 'POST'
                },
                'get': {
                    method: 'GET'
                }

            })
        }

        return groupFact;

    }])
