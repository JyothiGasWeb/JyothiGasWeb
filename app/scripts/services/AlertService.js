angular.module('clientApp')
    .service('AlertService', ['$mdToast', function($mdToast) {

        this.alert = function(message, style, delay) {
            if(!delay){
                delay = 1500;
            }else{
                delay = delay;
            }
            var toast = $mdToast.simple()
                .textContent(message)
                .action('X')
                .hideDelay(delay)
                .highlightAction(true)
                .highlightClass(style)
                .position("top right");
            $mdToast.show(toast).then(function(response) {
                if (response == 'ok') {
                    $mdToast.hide();
                }
            });
        };

        return this;

    }]);

var underscore = angular.module('underscore', []);
underscore.factory('_', ['$window', function($window) {
    return $window._; // assumes underscore has already been loaded on the page
}]);
