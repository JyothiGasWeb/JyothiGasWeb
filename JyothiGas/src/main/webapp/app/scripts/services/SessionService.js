angular.module('clientApp')
    .factory('SessionService', ['localStorageService', function(localStorageService) {

        this.setConsumerSession = function(session) {
            if (localStorageService.isSupported) {
                localStorageService.set("consumer", session);
            } else {
                localStorageService.cookie.set("consumer", session);
            }
        }

        this.setSession = function(session) {
            if (localStorageService.isSupported) {
                localStorageService.set("email", session.email);
                localStorageService.set("roleId", session.roleId);
                localStorageService.set("roleName", session.roleName);

            } else {
                localStorageService.cookie.set("email", session.email);
                localStorageService.cookie.set("roleId", session.roleId);
                localStorageService.cookie.set("roleName", session.roleName);
            }
        }

        this.getConsumerSession = function() {
            var session = {};
            if (localStorageService.isSupported) {
                session.consumer = localStorageService.get("consumer")
            } else {
                session.consumer = localStorageService.cookie.get("consumer");
            }
            return session;
        };

        this.getSession = function() {
            var session = {};
            if (localStorageService.isSupported) {
                session.email = localStorageService.get("email");
                session.roleId = localStorageService.get("roleId");
                session.roleName = localStorageService.get("roleName");
            } else {
                session.email = localStorageService.cookie.get("email");
                session.roleId = localStorageService.cookie.get("roleId");
                session.roleName = localStorageService.cookie.get("roleName");
            }
            return session;
        };

        this.updateAddress = function(tokens) {
            if (localStorageService.isSupported) {
                localStorageService.set("accessToken", tokens.accessToken);
                localStorageService.set("refreshToken", tokens.refreshToken);
            } else {
                localStorageService.cookie.set("accessToken", tokens.accessToken);
                localStorageService.cookie.set("refreshToken", tokens.refreshToken);
            }
        };

        this.checkSession = function() {
            if (localStorageService.isSupported) {
                if (localStorageService.get("email")) {
                    return true;
                } else {
                    return false;
                };
            } else {
                if (localStorageService.cookie.get("email")) {
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
                return localStorageService.get("email");
            } else {
                return localStorageService.cookie.get("email");
            }
        };

        this.createPriceSession = function(price) {
            if (localStorageService.isSupported) {
                localStorageService.set("deposite_charges", price.deposite_charges);
                localStorageService.set("regulator_charges", price.regulator_charges);
                localStorageService.set("gasRefill_charges", price.gasRefill_charges);
                localStorageService.set("handling_charges", price.handling_charges);
                localStorageService.set("delievery_charges", price.delievery_charges);

            } else {
                localStorageService.cookie.set("deposite_charges", price.deposite_charges);
                localStorageService.cookie.set("regulator_charges", price.regulator_charges);
                localStorageService.cookie.set("gasRefill_charges", price.gasRefill_charges);
                localStorageService.cookie.set("handling_charges", price.handling_charges);
                localStorageService.cookie.set("delievery_charges", price.delievery_charges);
            }
        };

        this.getPriceSession = function() {
            var priceObj = {};
            if (localStorageService.isSupported) {
                priceObj.deposite_charges = localStorageService.get("deposite_charges");
                priceObj.regulator_charges = localStorageService.get("regulator_charges");
                priceObj.gasRefill_charges = localStorageService.get("gasRefill_charges");
                priceObj.handling_charges = localStorageService.get("handling_charges");
                priceObj.delievery_charges = localStorageService.get("delievery_charges");
            } else {
                priceObj.deposite_charges = localStorageService.cookie.get("deposite_charges");
                priceObj.regulator_charges = localStorageService.cookies.get("regulator_charges");
                priceObj.gasRefill_charges = localStorageService.cookie.get("gasRefill_charges");
                priceObj.handling_charges = localStorageService.cookie.get("handling_charges");
                priceObj.delievery_charges = localStorageService.cookie.get("delievery_charges");
            }
            return priceObj;
        };

        this.deletePriceSession = function() {

            if (localStorageService.isSupported) {
                localStorageService.remove("gasRefill_charges");
                localStorageService.remove("handling_charges");
                localStorageService.remove("delievery_charges");
            } else {
                localStorageService.cookie.remove("gasRefill_charges");
                localStorageService.cookie.remove("handling_charges");
                localStorageService.cookie.remove("delievery_charges");
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
