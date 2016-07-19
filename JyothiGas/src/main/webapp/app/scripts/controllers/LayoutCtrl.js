angular.module('medRepApp')
    .controller('LayoutCtrl', ['$scope', '$timeout', '$mdSidenav', 'SessionService', '$rootScope', 'LoginService', '$state', function($scope, $timeout, $mdSidenav, SessionService, $rootScope, LoginService, $state) {
        $scope.toggleLeft = buildDelayedToggler('left');
        $scope.toggleRight = buildToggler('right');
        $scope.isOpenRight = function() {
            return $mdSidenav('right').isOpen();
        };

        $rootScope.$on('event:auth-forbidden', function(event, attr) {
            var refreshToken = SessionService.getSession().refreshToken;
            if (attr.data[0] && attr.data[0].httpErrorCode == 405) {
                LoginService.getRefreshToken(refreshToken).then(function(response){
                    var tokens  = {
                        "accessToken": response.accessToken,
                        "refreshToken": response.refresh_token.value
                    }
                    SessionService.updateTokens(tokens);
                    //$state.reload();
                }, function(error){

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
    .controller('NavCtrl', ['$scope', '$state', '$rootScope', function($scope, $state, $rootScope) {

        $scope.currentPage = $state.current.data.name;

        $rootScope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.currentPage = toState.data.name;
            })
        $scope.navList = [{
            "name": "Dashboard",
            "icon": "fa fa-home",
            "link": "dashboard",
            "extNmae": "dashboard"
        }, {
            "name": "Notifications",
            "icon": "fa fa-bell",
            "link": "notifications",
            "extNmae": "notifications"
        }, {
            "name": "DoctorPlus",
            "icon": "fa fa-stethoscope",
            "link": "doctorPlusDash.transform.regulatory",
            "extNmae": "doctorPlus"
        }, {
            "name": "Surveys",
            "icon": "fa fa-weixin",
            "link": "surveys",
            "extNmae": "surveys"
        }, {
            "name": "Activity Score",
            "icon": "fa fa-bar-chart",
            "link": "activityScore",
            "extNmae": "activityScore"
        }, {
            "name": "Marketing Campaigns",
            "icon": "fa fa-bullhorn",
            "link": "dashboard",
            "extNmae": "dashboard"
        }, {
            "name": "MedRep Meetings",
            "icon": "fa fa-users",
            "link": "dashboard",
            "extNmae": "dashboard"
        }, {
            "name": "Discussion Forums",
            "icon": "fa fa fa-comment",
            "link": "dashboard",
            "extNmae": "dashboard"
        }, {
            "name": "Search for Drugs",
            "icon": "fa fa-search",
            "link": "drugSearch",
            "extNmae": "drugSearch"
        }, {
            "name": "News & Updates",
            "icon": "fa fa-newspaper-o",
            "link": "dashboard",
            "extNmae": "dashboard"
        }]
    }]);
