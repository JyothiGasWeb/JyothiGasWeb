 angular.module('clientApp')
     .controller('RegisterNewCtrl', ['$scope', 'RegisterService', '$state', 'AlertService', '$mdDialog', '$timeout', 'LoginService', 'SessionService', function($scope, RegisterService, $state, AlertService, $mdDialog, $timeout, LoginService, SessionService) {


         $scope.dealers = [];
         $scope.availableDealers = [];
         $scope.searchDeal = false;
         $scope.currentNavItem = 'consumer';
         $scope.dealer = {};
         var getAllDealers = function() {
             RegisterService.getAllDealers(2).then(function(response) {
                 $scope.availableDealers = response;
             }, function(error) {
                 console.log("error getting dealers list");
             })
         };

         var getAllDistributors = function() {
             RegisterService.getAllDealers(3).then(function(response) {
                 $scope.availableDealers.push(response);
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
                 if ($scope.dealers.length) {
                     $scope.user.dealerId = $scope.dealers[0].id;
                 }
             }, 1000);

         };


         //Consumer Login
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

         $scope.registerDealer = function() {
             var dealerObj = {
                 "email": $scope.dealer.email,
                 "name": $scope.dealer.name,
                 "contactNo": $scope.dealer.contactNo,
                 "city": $scope.dealer.city,
                 "areaCode": $scope.dealer.areaCode,
                 "encyPassword": $scope.dealer.encyPassword,
                 "roleId": 2,
                 "address": $scope.dealer.address,
                 "userType": $scope.dealer.userType
             };

             RegisterService.registerDealer(dealerObj).then(function(response) {
                 if (response && response.status == 'OK') {
                     validateuser($scope.dealer.contactNo, $scope.dealer.email);
                 } else if (response.status == 'Fail') {
                     AlertService.alert(response.message, 'md-warn', 2000);
                 }
             }, function(error) {
                 console.log("error Registering User")
             })
         };



         //Consumer OTP
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
                         gotoLogin();
                     }, 1000);

                 }, function() {
                     $scope.status = 'You cancelled the dialog.';
                 });
         };

         var saveUser = function(username) {
             var obj = {
                 "email": username
             }
             LoginService.getConsumer(obj).then(function(response) {
                 var userObj = {
                     "consumer_id": response.consumer_id,
                     "email": response.email,
                     "roleId": response.roleId,
                     "roleName": response.roleName
                 };
                 SessionService.setConsumerSession(response);
                 SessionService.setSession(userObj);
                 switch (userObj.roleId) {
                     case 1:
                         $state.go('consumerDash');
                         break;
                     case 2:
                         $state.go('dealerDash');
                         break;
                 };

             }, function(response) {
                 console.log("errror");
             });
         };

         var gotoLogin = function() {
             switch ($scope.currentNavItem) {
                 case 'consumer':
                     var loginObj = {
                         "username": $scope.user.email,
                         "password": $scope.user.encyPassword
                     }
                     break;
                 case 'dealer':
                     var loginObj = {
                         "username": $scope.dealer.email,
                         "password": $scope.dealer.encyPassword
                     }
             };

             LoginService.login(loginObj).then(function(response) {
                 if (response && response.status == 'OK') {
                     saveUser(loginObj.username);
                 } else if (response.accessToken && !response.username) {
                     AlertService.alert("Account not activated", 'md-warn');
                 } else {
                     AlertService.alert("Email and password did not match", 'md-warn');
                 }
             }, function(response) {
                 console.log(response);
             })

         };


         function init() {
             getAllDealers();
             $scope.user = {
                 "connectionQty": 1
             }
         }

         init();

     }]);
