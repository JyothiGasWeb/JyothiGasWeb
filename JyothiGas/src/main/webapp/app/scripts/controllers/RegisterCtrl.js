angular.module('medRepApp')
    .controller('RegisterCtrl', ['$scope', 'RegisterService', '$mdDialog', '$state', '$mdToast', '$mdDialog', function($scope, RegisterService, $mdDialog, $state, $mdToast, $mdDialog) {

        $scope.doctorObj = {};
        $scope.companyObj = {};
        var last = {
            bottom: false,
            top: true,
            left: false,
            right: true
        };

        $scope.registerDoctor = function() {
            RegisterService.registerDoctor($scope.doctorObj).then(function(response) {
                if (response.status == 'OK') {
                    $mdDialog.show({
                            controller: 'OtpModalCtrl',
                            templateUrl: 'app/views/modals/optModal.html',
                            parent: angular.element(document.body),
                            locals: { Number: $scope.doctorObj.mobileNo }
                        })
                        .then(function(obj) {

                        }, function() {
                            $scope.status = 'You cancelled the dialog.';
                        });
                } else {
                    var pinTo = $scope.getToastPosition();
                    $mdToast.show(
                        $mdToast.simple()
                        .textContent(response.message)
                        .position(pinTo)
                        .hideDelay(3000)
                    );
                }
            });

        };

        $scope.toastPosition = angular.extend({}, last);
        $scope.getToastPosition = function() {
            sanitizePosition();
            return Object.keys($scope.toastPosition)
                .filter(function(pos) {
                    return $scope.toastPosition[pos];
                })
                .join(' ');
        };

        function sanitizePosition() {
            var current = $scope.toastPosition;
            if (current.bottom && last.top) current.top = false;
            if (current.top && last.bottom) current.bottom = false;
            if (current.right && last.left) current.left = false;
            if (current.left && last.right) current.right = false;
            last = angular.extend({}, current);
        }
        $scope.showSimpleToast = function() {

        };

        $scope.goBack = function() {
            $state.go('register');
        }

        $scope.registerCompany = function() {
            RegisterService.registerCompany($scope.companyObj).then(function(response) {
                if (response.status == 'OK') {
                    var confirm = $mdDialog.confirm()
                        .title('Successfully Registered')
                        .textContent('You will recieve a confirmation email')
                        .ariaLabel('Lucky day')
                        .targetEvent(document.body)
                        .ok('Ok')
                        .cancel('Cancel');
                    $mdDialog.show(confirm).then(function() {
                        $state.go('login');
                    }, function() {
                        $state.go('login');
                    });
                }
            })
        }

        var init = function() {
            $scope.states = ["Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Chandigarh",
                "Dadra and Nagar Haveli",
                "Daman and Diu",
                "Delhi",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Orissa",
                "Punjab",
                "Pondicherry",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Tripura",
                "Uttar Pradesh",
                "Uttarakhand",
                "West Bengal"
            ];


        };

        init();

    }]);
