/**
 * clientApp Module
 *
 * Description
 */
angular.module('clientApp').
controller('BookingHistoryCtrl', ['$scope', 'SessionService', 'ConsumerService', function($scope, SessionService, ConsumerService) {

    $scope.bookings = [];
    $scope.noBookings = false;
    var consumerId = SessionService.getConsumerSession().consumer.consumer_id;
    var getBookings = function() {
        var obj = {
            "consumer_id": consumerId
        }
        ConsumerService.getConsumerBookings(obj).then(function(response) {
            $scope.bookings = response;
            if ($scope.bookings.length == 0) {
                $scope.noBookings = true;
            } else {
                for (var i = 0, len = $scope.bookings.length; i < len; i++) {
                    if ($scope.bookings[i].status == 'PENDING') {
                        var someDate = new Date($scope.bookings[i].date_of_deleivery);
                        someDate.setDate(someDate.getDate() + 5);
                        var dd = ("0" + someDate.getDate()).slice(-2);
                        var mm = ("0" + (someDate.getMonth() + 1)).slice(-2)
                        var y = someDate.getFullYear();
                        $scope.bookings[i].date_of_deleivery = y + '-' + mm + '-' + dd;
                    }
                }
            }
        }, function(error) {
            console.log("error getting consumer bookings");
        })
    };

    $scope.download = function(item) {
        console.log(item)
        if(item.bookingType == 'CYLINDER'){
            
        }
        /*var docDefinition = {
            content: [{
                text: 'JYOTHI GAS'
            }, {
                style: 'demoTable',
                table: {
                    widths: ['*', '*', '*', '*'],
                    body: [
                        [{ text: 'Order No', style: 'header' }, { text: 'Booking Type', style: 'header' },
                            { text: 'Amount', style: 'header' }, { text: 'Booking Date', style: 'header' },
                        ],
                        [item.id.toString(), item.bookingType, item.total.toString(), item.booking_date]
                    ]
                }
            }],
            styles: {
                header: {
                    bold: true,
                    color: '#000',
                    fontSize: 11
                },
                demoTable: {
                    color: '#666',
                    fontSize: 10
                }
            }
        };
        pdfMake.createPdf(docDefinition).download('JyothiGas.pdf');*/
        //pdfMake.createPdf(docDefinition).open();

    };

    var init = function() {
        getBookings();
    };
    init();
}])
