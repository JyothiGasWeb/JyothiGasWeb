angular.module('medRepApp')
    .controller('ActivityScoreCtrl', ['$scope', function($scope) {


        $scope.myChartObject = {};
        $scope.myChartObject.type = "ColumnChart";

        var getScores = function() {
            $scope.scores = [{
                "doctorId": 16,
                "companyId": null,
                "companyName": null,
                "doctorName": "Dinesh Reddy",
                "totalScore": 3,
                "activities": {
                    "Survey": 0,
                    "Appointment": 0,
                    "Feedback": 0,
                    "Notification": 3
                }
            }, {
                "doctorId": 16,
                "companyId": 2,
                "companyName": "SAIN Medicaments Pvt. Ltd",
                "doctorName": "Dinesh Reddy",
                "totalScore": 3,
                "activities": {
                    "Survey": 0,
                    "Appointment": 0,
                    "Feedback": 100,
                    "Notification": 3
                }
            }]
            $scope.drawChart($scope.scores[0]);
        };

        $scope.updateChart = function() {
            for (var i = 0, len = $scope.scores.length; i < len; i++) {
            	if($scope.scores[i].companyId == $scope.selectedId){
            		$scope.drawChart($scope.scores[i]);
            		break;
            	}
            }
        };


        $scope.drawChart = function(score) {
            console.log(score);
            $scope.myChartObject.data = {
                "cols": [
                    { id: "t", label: "Topping", type: "string" },
                    { id: "s", label: "Scores", type: "number" }
                ],
                "rows": [{
                    c: [
                        { v: "Survey" },
                        { v: score.activities.Survey },
                    ]
                }, {
                    c: [
                        { v: "Appointment" },
                        { v: score.activities.Appointment },
                    ]
                }, {
                    c: [
                        { v: "Feedback" },
                        { v: score.activities.Feedback },
                    ]
                }, {
                    c: [
                        { v: "Notification" },
                        { v: score.activities.Notification },
                    ]
                }]
            };

            $scope.myChartObject.options = {
                'title': score.companyName
            };
        };

        var init = function() {
            getScores();
        };
        init();
    }]);
