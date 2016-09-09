'use strict';
/**
 * @ngdoc overview
 * @name clientApp
 * @description
 * # clientApp
 *
 * Main module of the application.
 */

angular.module('clientApp', [
        'ui.router',
        'ngResource',
        'ngCookies',
        'ngMaterial',
        'ngMessages',
        'angular-loading-bar',
        'ngAnimate',
        'LocalStorageModule',
        'ngFileUpload',
        'naif.base64',
        'http-auth-interceptor',
        'underscore',
        'md.data.table',
        'ngCart',
        'mdPickers'
    ])
    .constant('APP_CONFIG', {
        API_URL: 'http://122.175.36.113:8080/jyothigas/'
        //API_URL: 'http://localhost:3000/space/'
    })
    .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
        //cfpLoadingBarProvider.latencyThreshold = 100;
    }])
    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/login');
        $stateProvider
            .state('layout', {
                url: '',
                abstract: true,
                views: {
                    '': {
                        templateUrl: 'app/views/layout/layout.html',
                        controller: 'LayoutCtrl'
                    },
                    'header@layout': {
                        templateUrl: 'app/views/layout/header.html'
                    },
                    'nav@layout': {
                        templateUrl: 'app/views/layout/nav.html'
                    },
                    'footer@layout': {
                        templateUrl: 'app/views/layout/footer.html'
                    }
                }
            })
            .state('main', {
                url: '/',
                templateUrl: "app/views/landingPage.html",
                controller: 'LandingpageCtrl'
            })
            .state('login', {
                url: '/login',
                templateUrl: "app/views/login.html",
                controller: "LoginCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('registerNew', {
                url: '/registerNew',
                templateUrl: "app/views/registerNew.html",
                controller: "RegisterNewCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('registerOld', {
                url: '/registerOld',
                templateUrl: "app/views/registerOld.html",
                controller: "RegisterOldCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('forgotPasswd', {
                url: '/forgotPasswd',
                templateUrl: "app/views/forgotPasswd.html",
                controller: "ForgotPasswdCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('consumerDash', {
                url: '/consumerDash',
                templateUrl: "app/views/consumer/consumerDash.html",
                controller: "ConsumerDashCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('bookRefill', {
                url: '/bookRefill',
                templateUrl: "app/views/consumer/bookRefill.html",
                controller: "BookRefillCtrl",
                parent: 'layout',
                data: {
                    name: 'bookRefill'
                }
            })
            .state('profile', {
                url: '/profile',
                templateUrl: "app/views/consumer/profile.html",
                controller: "ProfileCtrl",
                parent: 'layout',
                data: {
                    name: 'profile'
                }
            })
            .state('domestic', {
                url: '/domestic',
                templateUrl: "app/views/consumer/domesticConnection.html",
                controller: "DomesticConnectionCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('notifications', {
                url: '/notifications',
                templateUrl: "app/views/consumer/notifications.html",
                controller: "NotificationsCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('commercial', {
                url: '/commercial',
                templateUrl: "app/views/consumer/commercial.html",
                controller: "CommercialCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('industrial', {
                url: '/industrial',
                templateUrl: "app/views/consumer/industrial.html",
                controller: "IndustrialCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('appliances', {
                url: '/appliances',
                templateUrl: "app/views/consumer/appliances.html",
                controller: "AppliancesCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('searchDealer', {
                url: '/searchDealer',
                templateUrl: "app/views/consumer/searchDealer.html",
                controller: "SearchDealerCtrl",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('dealerChange', {
                url: '/dealerChange',
                templateUrl: "app/views/consumer/dealerChange.html",
                controller: "DealerChangeCtrl",
                parent: 'layout',
                data: {
                    name: 'dealerChange'
                }
            })
            .state('transferConnection', {
                url: '/transferConnection',
                templateUrl: "app/views/consumer/TransferConnection.html",
                controller: "DealerChangeCtrl",
                parent: 'layout',
                data: {
                    name: 'transferConnection'
                }
            })
            .state('addressChange', {
                url: '/addressChange',
                templateUrl: "app/views/consumer/addressChange.html",
                controller: "AddressChangeCtrl",
                parent: 'layout',
                data: {
                    name: 'addressChange'
                }
            })
            .state('mechanicService', {
                url: '/mechanicService',
                templateUrl: "app/views/consumer/mechanicService.html",
                controller: "MechanicServiceCtrl",
                parent: 'layout',
                data: {
                    name: 'mechanicService'
                }
            })
            .state('priceList', {
                url: '/priceList',
                templateUrl: "app/views/consumer/priceList.html",
                controller: "PriceListCtrl",
                parent: 'layout',
                data: {
                    name: 'priceList'
                }
            })
            .state('contactDealer', {
                url: '/contactDealer',
                templateUrl: "app/views/consumer/contactDealer.html",
                controller: "ContactDealerCtrl",
                parent: 'layout',
                data: {
                    name: 'contactDealer'
                }
            })
            .state('safetyTips', {
                url: '/safetyTips',
                templateUrl: "app/views/consumer/safetyTips.html",
                parent: 'layout',
                data: {
                    name: 'safetyTips'
                }
            })
            .state('bookingHistory', {
                url: '/bookingHistory',
                templateUrl: "app/views/consumer/bookingHistory.html",
                controller: "BookingHistoryCtrl",
                parent: 'layout',
                data: {
                    name: 'bookingHistory'
                }
            })
            .state('surrender', {
                url: '/surrender',
                templateUrl: "app/views/consumer/surrender.html",
                controller: "SurrenderCtrl",
                parent: 'layout',
                data: {
                    name: 'surrender'
                }
            })
            .state('checkout', {
                url: '/checkout',
                templateUrl: "app/views/consumer/checkout.html",
                controller: "CheckoutCtrl",
                data: {
                    name: 'consumerDash'
                }
            })
            .state('contactUs', {
                url: '/contactUs',
                templateUrl: "app/views/consumer/contactUs.html",
                parent: 'layout',
                data: {
                    name: 'consumerDash'
                }
            })
            .state('success', {
                url: '/success',
                templateUrl: "app/views/consumer/success.html",
                controller: "SuccessCtrl",
                data: {
                    name: 'consumerDash'
                }
            })


    }])
    .config(['$mdThemingProvider', function($mdThemingProvider) {

        //Angular Material Theme Configuration
        $mdThemingProvider.theme('default')
            .primaryPalette('blue')
            .accentPalette('red')

        $mdThemingProvider.theme('docs-dark', 'default')
            .primaryPalette('yellow')
            .accentPalette('green')
        $mdThemingProvider.theme('altTheme')
            .primaryPalette('blue')
    }])
    .run(function($state, $rootScope, $stateParams, LoginService) {

        $rootScope.$on('$stateChangeStart', function(event, next, params) {

            if (LoginService.isAuthenticated()) {

                console.log("In .run(), app.js - User Authenticated");

                if (LoginService.hasSessionObject()) {
                    console.log("In .run(), app.js - User has session object");

                    if (angular.isDefined(next.data) && angular.isDefined(next.data.type) && next.data.type === 'login') {
                        event.preventDefault();
                        $state.go('consumerDash');
                    }

                } else {
                    console.log("user does not have session object");
                    if (next.name !== 'main') {
                        event.preventDefault();
                        console.log("In .run(), app.js - User sent to loading page.");
                        $rootScope.nextState = {
                            next: next,
                            params: params
                        };

                        $state.go('main', {
                            redirect: true
                        });
                    }
                }

            } else {
                //console.log("In .run(), app.js - User NOT Authenticated");
                if (!angular.isDefined(next.data) || !angular.isDefined(next.data.type)) {
                    event.preventDefault();
                    $state.go('login');
                } else if (next.data && next.data.type == 'home') {
                    //Access the pages of type home
                } else if (angular.isDefined(next.data) && angular.isDefined(next.data.type) && next.data.type !== 'login') {
                    event.preventDefault();
                    $state.go('login');
                } else {
                    //Its already going to page of type login
                    //Do nothing
                }
            }
        });

    });
