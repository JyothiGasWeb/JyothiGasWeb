/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('ContactDealerCtrl', ['$scope', function($scope) {


    var getContactDetails = function() {
        $scope.dealer = {
            "name": "General Electric and Radio Ser",
            "address1": "Main Road",
            "address2": "Opp. Collectorate Office",
            "district": "Thane",
            "pinCode": "400601",
            "email": "generalelectronic@gmail.com",
            "phone":"02225476969",
            "mobile":"5470098",
            "proprietor":"Kailash Dudani",
            "inchargeName": "Resmi Devasia",
            "inchargeEmail":"resmidevasia@bharatpetrolium.in"
        }
    };

    var init = function() {
        getContactDetails();
    };
    init();
}]);
