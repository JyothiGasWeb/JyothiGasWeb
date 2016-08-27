angular.module('clientApp')
    .controller('LayoutCtrl', ['$scope', '$timeout', '$mdSidenav', 'SessionService', '$rootScope', 'LoginService', '$state', 'AlertService', function($scope, $timeout, $mdSidenav, SessionService, $rootScope, LoginService, $state, AlertService) {
        $scope.toggleLeft = buildDelayedToggler('left');
        $scope.toggleRight = buildToggler('right');
        $scope.isOpenRight = function() {
            return $mdSidenav('right').isOpen();
        };

        $rootScope.$on('event:auth-forbidden', function(event, attr) {
            var refreshToken = SessionService.getSession().refreshToken;
            if (attr.data[0] && attr.data[0].httpErrorCode == 405) {
                LoginService.getRefreshToken(refreshToken).then(function(response) {
                    if (response && response.accessToken && response.refresh_token) {
                        var tokens = {
                            "accessToken": response.accessToken,
                            "refreshToken": response.refresh_token.value
                        }
                        SessionService.updateTokens(tokens);
                    } else {
                        AlertService.alert("Something went Wrong Please login Again", 'md-warn');
                    }
                    //$state.reload();
                }, function(error) {

                })
            }
            //$state.go('permissionDenied');
        });


        /**
         * Supplies a function that will continue to operate until the
         * time is up.
         */
        function debounce(func, wait, context) {
            var timer;
            return function debounced() {
                var context = $scope,
                    args = Array.prototype.slice.call(arguments);
                $timeout.cancel(timer);
                timer = $timeout(function() {
                    timer = undefined;
                    func.apply(context, args);
                }, wait || 10);
            };
        }
        /**
         * Build handler to open/close a SideNav; when animation finishes
         * report completion in console
         */
        function buildDelayedToggler(navID) {
            return debounce(function() {
                $mdSidenav(navID)
                    .toggle()
                    .then(function() {
                        //$log.debug("toggle " + navID + " is done");
                    });
            }, 200);
        }

        function buildToggler(navID) {
            return function() {
                $mdSidenav(navID)
                    .toggle()
                    .then(function() {
                        //$log.debug("toggle " + navID + " is done");
                    });
            }
        };
        var init = function() {
            var sessionObj = SessionService.getSession();
            if (sessionObj.user) {
                $scope.user = sessionObj.user;
            }
        };
        init();
    }])
    .controller('NavCtrl', ['$scope', '$state', '$rootScope', '$mdDialog', 'SessionService', function($scope, $state, $rootScope, $mdDialog, SessionService) {

        $scope.currentPage = $state.current.data.name;
        var user = SessionService.getConsumerSession().consumer;
        $rootScope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.currentPage = toState.data.name;
            });

        $scope.goto = function(ev, item) {
            switch (item.link) {
                case 'bookRefill':
                    if (user.userType == 'NEW') {
                        var message = "Being a  new customer, Please book a " + user.connectionTypeName + " connection.";
                        var confirm = $mdDialog.confirm()
                            .title(message)
                            .textContent('')
                            .ariaLabel('Lucky day')
                            .targetEvent(ev)
                            .ok('Ok');
                        $mdDialog.show(confirm).then(function() {

                        }, function() {

                        });
                    } else {
                        $state.go(item.link);
                    }
                    break;
                default:
                    $state.go(item.link);
            }
        };
        
        $scope.navList = [{
            "name": "Home",
            "icon": "fa fa-home",
            "link": "consumerDash",
            "extNmae": "consumerDash"
        }, {
            "name": "My Profile",
            "icon": "fa fa-user",
            "link": "profile",
            "extNmae": "profile"
        }, {
            "name": "Book Refill",
            "icon": "fa fa-external-link",
            "link": "bookRefill",
            "extNmae": "bookRefill"
        }, {
            "name": "Price List",
            "icon": "fa fa-money",
            "link": "priceList",
            "extNmae": "priceList"
        }, {
            "name": "Change Dealer",
            "icon": "fa fa-copy",
            "link": "dealerChange",
            "extNmae": "dealerChange"
        }, {
            "name": "Update Address",
            "icon": "fa fa-map-marker",
            "link": "addressChange",
            "extNmae": "addressChange"
        }, {
            "name": "Transfer Connection",
            "icon": "fa fa-exchange",
            "link": "transferConnection",
            "extNmae": "transferConnection"
        }, {
            "name": "Mechanic Service",
            "icon": "fa fa fa-suitcase",
            "link": "mechanicService",
            "extNmae": "mechanicService"
        }, {
            "name": "Surrender Connection",
            "icon": "fa fa fa-ban",
            "link": "surrender",
            "extNmae": "surrender"
        }, {
            "name": "Contact your Dealer",
            "icon": "fa fa-phone",
            "link": "contactDealer",
            "extNmae": "contactDealer"
        }, {
            "name": "Safety Tips",
            "icon": "fa fa-info",
            "link": "safetyTips",
            "extNmae": "safetyTips"
        }, {
            "name": "Booking History",
            "icon": "fa fa-history",
            "link": "bookingHistory",
            "extNmae": "bookingHistory"
        }]
    }]);
