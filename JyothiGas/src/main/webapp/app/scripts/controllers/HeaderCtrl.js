angular.module('clientApp')
    .controller('HeaderCtrl', ['$scope', '$state', 'SessionService', 'ngCart', 'ConsumerService', function($scope, $state, SessionService, ngCart, ConsumerService) {

        $scope.userLogout = function() {
            SessionService.deleteSession();
            ngCart.empty();
            $state.go('login')
        };

        var getAllNotifications = function(){
            ConsumerService.getAllNotifications().then(function(response){
                $scope.notifications = response;
            }, function(erorr){
                console.log("error");
            })
        };

        $scope.openMenu = function($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };
        var init = function(){
            getAllNotifications();
        };
        init();
    }])
