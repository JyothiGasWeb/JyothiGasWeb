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
                if ($scope.user.pinCode == $scope.availableDealers[i].dealer_area_code) {
                    $scope.dealers.push($scope.availableDealers[i]);
                }
            };
            $timeout(function() {
                $scope.searchDeal = false;
                if ($scope.dealers.length) {
                    $scope.user.dealerId = $scope.dealers[0].id;
                }
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
                "userType": $scope.user.userType
            };

            RegisterService.register(userObj).then(function(response) {
                if (response && response.status == 'OK') {
                    validateuser($scope.user.contactNo, $scope.user.email);
                } else if (response.status == 'Fail') {
                    AlertService.alert(response.message, 'md-warn', 2000);
                }
            }, function(error) {
                console.log("error Registering User")
            })
        };

        var validateuser = function(mobile, email) {
            $mdDialog.show({
                    controller: function($scope, $mdDialog, RegisterService, mobile, email, LoginService) {
                        $scope.otpObj = {
                            "number": mobile
                        };
                        $scope.create = function() {
                            RegisterService.validateOtp($scope.otpObj).then(function(response) {
                                $mdDialog.hide(response);
                            }, function(error) {
                                console.log("error validating OTP");
                            });
                        };

                        $scope.closeDialog = function() {
                            $mdDialog.cancel();
                        };

                        $scope.resend = function() {
                            var obj = {
                                "verificationId": email
                            };
                            LoginService.newOtp(obj).then(function(response) {
                                console.log("success");
                            }, function(error) {
                                console.log("error")
                            })
                        }
                    },
                    templateUrl: 'app/views/consumer/registerOtp.html',
                    parent: angular.element(document.body),
                    targetEvent: document.body,
                    locals: {
                        "mobile": mobile,
                        "email": email
                    }
                })
                .then(function() {
                    AlertService.alert("Mobile Validated Successfully", "1500");
                    $timeout(function() {
                        $state.go('login');
                    }, 1000);
                    
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
