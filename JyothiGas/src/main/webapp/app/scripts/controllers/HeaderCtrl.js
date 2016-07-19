angular.module('medRepApp')
    .controller('HeaderCtrl', ['$scope', '$state', 'SessionService', function($scope, $state, SessionService) {

        $scope.userLogout = function() {
            SessionService.deleteSession();
            $state.go('login')
        };

        $scope.notifications = [{
            "notificationId": 1,
            "notificationDesc": "Test Description",
            "typeId": 1,
            "therapeuticId": "1",
            "companyId": "1",
            "updatedOn": null,
            "updatedBy": null,
            "createdOn": null,
            "createdBy": null,
            "validUpto": null,
            "status": "NEW",
            "externalRef": "none",
            "notificationDetails": [{
                "detailId": 1,
                "notificationId": 1,
                "contentType": "jpg",
                "contentSeq": 1,
                "contentLocation": "C:\\\\Users\\\\Umar Ashraf\\\\Pictures\\\\Test.jpg",
                "contentName": "1.jpg"
            }, {
                "detailId": 2,
                "notificationId": 1,
                "contentType": "JPeG",
                "contentSeq": 2,
                "contentLocation": "C:\\\\Users\\\\Umar Ashraf\\\\Pictures\\\\Test.jpg",
                "contentName": "Test"
            }]
        }, {
            "notificationId": 2,
            "notificationDesc": "Test Description 2",
            "typeId": 1,
            "therapeuticId": "1",
            "companyId": "1",
            "updatedOn": null,
            "updatedBy": null,
            "createdOn": null,
            "createdBy": null,
            "validUpto": null,
            "status": "NEW",
            "externalRef": "none",
            "notificationDetails": [{
                "detailId": 3,
                "notificationId": 2,
                "contentType": "JPEG",
                "contentSeq": 1,
                "contentLocation": "C:\\\\Temp\\2\\3",
                "contentName": "Test"
            }, {
                "detailId": 4,
                "notificationId": 2,
                "contentType": "JPEG",
                "contentSeq": 2,
                "contentLocation": "C:\\\\Temp\\2\\4",
                "contentName": "Test"
            }]
        }];

        $scope.openMenu = function($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };
    }])
