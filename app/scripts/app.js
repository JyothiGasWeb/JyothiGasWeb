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
        'underscore'
    ])
    .constant('APP_CONFIG', {
        API_URL: ''
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
                    type: 'login',
                    name: 'consumerDash'
                }
            })
             .state('bookRefill', {
                url: '/bookRefill',
                templateUrl: "app/views/consumer/bookRefill.html",
                controller: "BookRefillCtrl",
                parent: 'layout',
                data: {
                    type: 'login',
                    name: 'bookRefill'
                }
            })
             .state('addressChange', {
                url: '/addressChange',
                templateUrl: "app/views/consumer/addressChange.html",
                controller: "AddressChangeCtrl",
                parent: 'layout',
                data: {
                    type: 'login',
                    name: 'addressChange'
                }
            })


    }])
    .config(['$mdThemingProvider', function($mdThemingProvider) {

        //Angular Material Theme Configuration
        $mdThemingProvider.theme('default')
            .primaryPalette('blue')
            .accentPalette('red')
            .dark();

        $mdThemingProvider.theme('docs-dark', 'default')
            .primaryPalette('yellow')
            .accentPalette('green')
            .dark();
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
                        $state.go('dashboard');
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
