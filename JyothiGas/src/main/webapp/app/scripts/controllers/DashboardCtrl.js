angular.module('medRepApp')
    .controller('DashboardCtrl', ['$scope', 'SessionService', '$state', '$filter', '$q', '$timeout', function($scope, SessionService, $state, $filter, $q, $timeout) {

        var getUser = function() {
            $scope.userObj = SessionService.getSession();
        };

        $scope.currentPage = "Dashboard";

        $scope.appointments = [{
            "appointmentId": 6,
            "title": "ABC",
            "appointmentDesc": "ABC 1 Test Description",
            "doctorId": 25,
            "doctorName": "Umar Ashraf",
            "pharmaRepId": 5,
            "pharmaRepName": "Ravish Jha",
            "notificationId": 11,
            "startDate": "20150928100000",
            "duration": 30,
            "feedback": null,
            "status": "Scheduled",
            "createdOn": 1443351907000,
            "location": "Clinic",
            "companyId": 2,
            "companyname": "Abbott Laboratories",
            "therapeuticId": 1,
            "therapeuticName": "Demo"
        }, {
            "appointmentId": 6,
            "title": "SAIN",
            "appointmentDesc": "Appointment Test Description",
            "doctorId": 25,
            "doctorName": "Umar Ashraf",
            "pharmaRepId": 5,
            "pharmaRepName": "Ravish Jha",
            "notificationId": 11,
            "startDate": "20150928100000",
            "duration": 30,
            "feedback": null,
            "status": "Scheduled",
            "createdOn": 1443351907000,
            "location": "Clinic",
            "companyId": 2,
            "companyname": "Abbott Laboratories",
            "therapeuticId": 1,
            "therapeuticName": "Demo"
        }, {
            "appointmentId": 6,
            "title": "ABC",
            "appointmentDesc": "ABC 1 Test Description",
            "doctorId": 25,
            "doctorName": "Umar Ashraf",
            "pharmaRepId": 5,
            "pharmaRepName": "Ravish Jha",
            "notificationId": 11,
            "startDate": "20150928100000",
            "duration": 30,
            "feedback": null,
            "status": "Scheduled",
            "createdOn": 1443351907000,
            "location": "Clinic",
            "companyId": 2,
            "companyname": "Abbott Laboratories",
            "therapeuticId": 1,
            "therapeuticName": "Demo"
        }];

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

        var init = function() {
            getUser();
        };
        init();

    }])
