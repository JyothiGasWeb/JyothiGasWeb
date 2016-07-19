angular.module('medRepApp')
    .service('AlertService', ['$mdToast', function($mdToast) {

        this.alert = function(message, style) {
            var toast = $mdToast.simple()
                .textContent(message)
                .action('X')
                .hideDelay(500000000000)
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
