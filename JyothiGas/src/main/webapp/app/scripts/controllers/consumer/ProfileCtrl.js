/**
 *  Module
 *
 * Description
 */
angular.module('clientApp').
controller('ProfileCtrl', ['$scope', 'SessionService', 'RegisterService', 'ConsumerService', 'AlertService', 'LoginService', '$q', function($scope, SessionService, RegisterService, ConsumerService, AlertService, LoginService, $q) {

    $scope.availableDealers = [];

    $scope.docsList = [{
        "docName": "Passport",
        "docType": "PASSPORT",
        "kyc": {}
    }, {
        "docName": "Voter Identity Card",
        "docType": "VOTER IDENTITY CARD",
        "kyc": {}
    }, {
        "docName": "Driving Licence",
        "docType": "DRIVING LICENCE",
        "kyc": {}
    }, {
        "docName": "Aadhaar Card",
        "docType": "AADHAAR CARD",
        "kyc": {}
    }];

    var getAllDealers = function() {
        RegisterService.getAllDealers().then(function(response) {
            $scope.availableDealers = response;
            getConsumer();
        }, function(error) {
            console.log("error getting dealers list");
        })
    };

    $scope.updateFile = function() {


        var arr = [];
        for (var i = 0, len = $scope.docsList.length; i < len; i++) {
            if ($scope.docsList[i].kyc.name && $scope.docsList[i].kyc.type) {
                var a = ConsumerService.updateFile($scope.consumer.consumer_id, $scope.docsList[i].docType, $scope.docsList[i].kyc);
                arr.push(a);
            };
        };

        $q.all(arr).then(function(response) {
            if (response[0].status == 'OK') {
                AlertService.alert("KYC Documents uploaded Successfully");
            }
        }, function(error) {
            console.log("error uploading KYC documents");
        })

    };

    var getConsumer = function() {
        $scope.consumer = JSON.parse(JSON.stringify(SessionService.getConsumerSession().consumer));
        getDocuments();
        $scope.filterDealers();
    }

    var getDocuments = function() {
        var obj = {
            "custId": $scope.consumer.consumer_id
        };
        ConsumerService.getDocumentDetails(obj).then(function(response) {
            if (response && response.length) {
                for (var i = 0, size = $scope.docsList.length; i < size; i++) {
                    for (var j = 0, len = response.length; j < len; j++) {
                        if ($scope.docsList[i].docType == response[j].docType){
                            $scope.docsList[i].kyc.name = response[j].documentName;
                        }
                    }
                }
            }
        }, function(error) {
            console.log("error retreiving documents");
        });
    }

    $scope.update = function(consumerForm) {
        var obj = {
            "id": $scope.consumer.reg_id,
            "areaCode": $scope.consumer.areaCode,
            "address": $scope.consumer.address,
            "connectionTypeId": $scope.consumer.connectionTypeId,
            "dealerId": $scope.consumer.dealerId,
            "status": "NEW"
        }
        ConsumerService.updateConsumer(obj).then(function(response) {
            if (response.status == 'OK') {
                updateUser();
            }
        }, function(erorr) {
            console.log("error Updating Consumer");
        });
    };

    var updateUser = function() {
        var obj = {
            "email": $scope.consumer.email
        }
        LoginService.getConsumer(obj).then(function(response) {
            SessionService.setConsumerSession(response);
            AlertService.alert("Profile updated sucessfully", "md-primary");
            getConsumer();
            //$scope.reset();
        }, function(response) {
            console.log("errror");
        });
    };

    $scope.filterDealers = function() {
        $scope.dealers = [];
        for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
            if ($scope.consumer.areaCode == $scope.availableDealers[i].dealer_area_code) {
                $scope.dealers.push($scope.availableDealers[i]);
            }
        }
    };

    var init = function() {
        getAllDealers();
    };
    init();
}]);
