angular.module('clientApp')
    .service('AlertService', ['$mdToast', function($mdToast) {

        this.alert = function(message, style) {
            var toast = $mdToast.simple()
                .textContent(message)
                .action('X')
                .hideDelay(500)
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
