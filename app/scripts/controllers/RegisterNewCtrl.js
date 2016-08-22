angular.module('clientApp')
    .controller('RegisterNewCtrl', ['$scope', 'RegisterService', '$state', 'AlertService', '$mdDialog', '$timeout', function($scope, RegisterService, $state, AlertService, $mdDialog, $timeout) {


        $scope.dealers = [];
        $scope.availableDealers = [];
        $scope.searchDeal = false;
        var getAllDealers = function() {
            RegisterService.getAllDealers().then(function(response) {
                $scope.availableDealers = response;
            }, function(error) {
                console.log("error getting dealers list");
            })
        };

        $scope.filterDealers = function() {
            $scope.dealers = [];
            $scope.searchDeal = true;
            for (var i = 0, len = $scope.availableDealers.length; i < len; i++) {
                if ($scope.user.areaCode == $scope.availableDealers[i].dealer_area_code) {
                    $scope.dealers.push($scope.availableDealers[i]);
                }
            };
            $timeout(function() {
                $scope.searchDeal = false;
            }, 1000);

        };

        $scope.register = function() {
            var userObj = {
                "email": $scope.user.email,
                "name": $scope.user.name,
                "contactNo": $scope.user.contactNo,
                "city": $scope.user.city,
                "areaCode": $scope.user.areaCode,
                "encyPassword": $scope.user.encyPassword,
                "roleId": 1,
                "address": $scope.user.address,
                "connectionTypeId": $scope.user.connectionTypeId,
                "dealerId": $scope.user.dealerId,
                "status": "New",
                "connectionQty": 1,
                "userType": $scope.user.user
            };

            RegisterService.register(userObj).then(function(response) {
                if (response && response.status == 'OK') {
                    validateuser($scope.user.contactNo);
                } else if (response.status == 'Fail') {
                    AlertService.alert(response.message, 'md-warn', 2000);
                }
            }, function(error) {
                console.log("error Registering User")
            })
        };

        var validateuser = function(mobile) {
            $mdDialog.show({
                    controller: function($scope, $mdDialog, RegisterService, mobile) {
                        $scope.otpObj = {};
                        $scope.create = function() {
                            RegisterService.validateOtp($scope.otpObj).then(function(response) {
                                $mdDialog.hide(response);
                            }, function(error) {
                                console.log("error validating OTP");
                            });
                        };

                        $scope.closeDialog = function() {
                            $mdDialog.cancel();
                        }
                    },
                    templateUrl: 'app/views/consumer/registerOtp.html',
                    parent: angular.element(document.body),
                    targetEvent: document.body,
                    locals: {
                        "mobile": mobile
                    }
                })
                .then(function() {
                    $state.go('login');
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function init() {
            getAllDealers();
            $scope.user = {
                "connectionQty": 1
            }
        }

        init();

    }]);
