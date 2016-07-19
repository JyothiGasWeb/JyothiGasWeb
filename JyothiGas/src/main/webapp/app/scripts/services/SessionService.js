angular.module('medRepApp')
    .factory('SessionService', ['localStorageService', function(localStorageService) {

        this.setSession = function(session) {
            if (localStorageService.isSupported) {
                localStorageService.set("accessToken", session.accessToken);
                localStorageService.set("username", session.username);
                localStorageService.set("userId", session.userId);
                localStorageService.set("user", session.user);
                localStorageService.set("refreshToken", session.refreshToken);

            } else {
                localStorageService.cookie.set("accessToken", session.accessToken);
                localStorageService.cookie.set("username", session.username);
                localStorageService.cookie.set("userId", session.userId);
                localStorageService.cookie.set("user", session.user);
                localStorageService.cookie.set("refreshToken", session.refreshToken);
            }
        }

        this.getSession = function() {
            var session = {};
            if (localStorageService.isSupported) {
                session.accessToken = localStorageService.get("accessToken");
                session.username = localStorageService.get("username");
                session.userId = localStorageService.get("userId");
                session.user = localStorageService.get("user");
                session.refreshToken = localStorageService.get("refreshToken");
            } else {
                session.accessToken = localStorageService.cookie.get("accessToken");
                session.username = localStorageService.cookie.get("username");
                session.userId = localStorageService.cookie.get("userId");
                session.user = localStorageService.cookie.get("user"); 
                session.refreshToken = localStorageService.cookie.get("refreshToken");
            }
            return session;
        };

        this.updateTokens = function(tokens){
            if(localStorageService.isSupported){
                localStorageService.set("accessToken", tokens.accessToken);
                localStorageService.set("refreshToken", tokens.refreshToken);
            }else{
                localStorageService.cookie.set("accessToken", tokens.accessToken);
                localStorageService.cookie.set("refreshToken", tokens.refreshToken);
            }
        }

        this.checkSession = function() {
            if (localStorageService.isSupported) {
                if (localStorageService.get("accessToken")) {
                    return true;
                } else {
                    return false;
                };
            } else {
                if (localStorageService.cookie.get("accessToken")) {
                    return true;
                } else {
                    return false;
                };
            }

        };

        this.getSessionObject = function() {

            return true;

        };

        this.getStoredUserToken = function() {
            if (localStorageService.isSupported) {
                return localStorageService.get("accessToken");
            } else {
                return localStorageService.cookie.get("accessToken");
            }
        };


        this.deleteSession = function() {

            if (localStorageService.isSupported) {
                localStorageService.clearAll();
            };

            localStorageService.cookie.clearAll();
        };


        return this;

    }])
