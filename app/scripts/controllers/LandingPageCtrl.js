angular.module('clientApp')
    .controller('LandingpageCtrl', ['$scope', 'SessionService', '$state', function($scope, SessionService, $state) {
        var userObj = {};

        $scope.deleteSession = function() {
            SessionService.deleteSession();
        };
        $scope.init = function() {
            /*var email = SessionService.getSession().userEmail;

            UserService().get({
                'email': email
            }).$promise.then(function(response) {
                userObj = response.data.leagueUser;
                leagueService().get({
                    "type": "info",
                    "id": userObj.lid
                }).$promise.then(function(response) {
                    if (response.status == 'SUCCESS' && response.data) {
                        userObj.sportsType = response.data.league.spt_type;
                        SessionService.createSessionObject(userObj);
                        if ($rootScope.nextState != undefined && $rootScope.nextState != "")
                            $state.go($rootScope.nextState.next, $rootScope.nextState.params);
                        else
                            $state.go('dashboard');
                    }
                }, function(response) {
                    console.log("error");
                    $scope.loader = false;
                });

            }, function(error) {
                //TODO - ask user to refresh page
            });*/
            $state.go('dashboard');
            //$scope.deleteSession();

        };

        $scope.init();
    }]);
