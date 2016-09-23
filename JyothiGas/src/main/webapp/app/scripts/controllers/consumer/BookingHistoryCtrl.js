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
        var itemsDetailed = [
            [{ text: 'Booking Name', style: 'subheader' }, { text: 'Quantity', style: 'subheader' },
                { text: 'Price', style: 'subheader' }, { text: 'Total Amount', style: 'subheader' },
            ]
        ];
        if (item.bookingType == 'CYLINDER') {
            var price = item.gasRefill_charges + item.handling_charges + item.delievery_charges + item.regulator_charges + item.deposite_charges;
            var total = item.quantity * price;
            var cylinderArr = [];
            cylinderArr.push("Cylinder", item.quantity.toString(), price.toString(), total.toString());
            itemsDetailed.push(cylinderArr);
        }
        for (var i = 0, len = item.appliancesObj.length; i < len; i++) {
            var fila = [];
            fila.push(item.appliancesObj[i].appliance_Name.toString(), item.appliancesObj[i].quantity.toString(), item.appliancesObj[i].customerWithoutTax.toString(), (item.appliancesObj[i].quantity * item.appliancesObj[i].customerWithoutTax).toString());
            itemsDetailed.push(fila);
        }
        console.log(itemsDetailed)

        var docDefinition = {
            content: [{
                text: 'JYOTHI GAS',
                style: 'header',
                alignment: 'center'
            }, {
                text: '\nORDER ID:' + item.id
            }, {
                style: 'demoTable',
                table: {
                    widths: ['*', '*', '*', '*'],
                    body: itemsDetailed
                }
            }, {
                table: {
                    widths: ['*', '*', '*', '*'],
                    body: [
                        ["", "", "Total ", item.total.toString()]
                    ]
                }
            }],
            styles: {
                header: {
                    bold: true,
                    color: '#000',
                    fontSize: 16
                },
                subheader: {
                    bold: true,
                    color: '#000',
                    fontSize: 12
                },
                demoTable: {
                    color: '#666',
                    fontSize: 10
                }
            }
        };
        pdfMake.createPdf(docDefinition).download('JyothiGas.pdf');
        //pdfMake.createPdf(docDefinition).open();

    };

    var init = function() {
        getBookings();
    };
    init();
}])
