'use strict';
/**
 * @ngdoc overview
 * @name medRepApp
 * @description
 * # medRepApp
 *
 * Main module of the application.
 */

angular.module('medRepApp', [
        'ui.router',
        'ngResource',
        'ngCookies',
        'ngMaterial',
        'ngMessages',
        'angular-loading-bar',
        'ngAnimate',
        'LocalStorageModule',
        'materialCalendar',
        'ngFileUpload',
        'naif.base64',
        'http-auth-interceptor',
        'googlechart'
    ])
    .constant('APP_CONFIG', {
        API_URL: 'http://183.82.106.234:8080/medrep-web/'
    })
    .config(['$httpProvider', function($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }])
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
            .state('register', {
                url: '/register',
                templateUrl: "app/views/register.html",
                controller: "RegisterCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('registerDoctor', {
                url: '/register/doctor',
                templateUrl: "app/views/registerDoctor.html",
                controller: "RegisterCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('registerCompany', {
                url: '/register/company',
                templateUrl: "app/views/registerCompany.html",
                controller: "RegisterCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('forgotPassword', {
                url: '/forgotPassword',
                templateUrl: "app/views/forgotPassword.html",
                controller: "ForgotPasswordCtrl",
                data: {
                    type: 'login'
                }
            })
            .state('dashboard', {
                url: '/dashboard',
                templateUrl: "app/views/dashboard.html",
                parent: 'layout',
                controller: "DashboardCtrl",
                data: {
                    'name': "dashboard"
                }
            })
            .state('drugSearch', {
                url: '/searchDrugs',
                templateUrl: "app/views/searchDrugs.html",
                parent: 'layout',
                controller: "SearchDrugsCtrl",
                data: {
                    'name': "drugSearch"
                }
            })
            .state('notifications', {
                url: '/notifications',
                templateUrl: "app/views/notifications.html",
                parent: 'layout',
                controller: "NotificationsCtrl",
                data: {
                    'name': "notifications"
                }
            })
            .state('activityScore', {
                url: '/activityScore',
                templateUrl: "app/views/ActivityScore.html",
                parent: 'layout',
                controller: "ActivityScoreCtrl",
                data: {
                    'name': "activityScore"
                }
            })
            .state('surveys', {
                url: '/surveys',
                templateUrl: "app/views/surveys.html",
                parent: 'layout',
                controller: "SurveysCtrl",
                data: {
                    'name': "surveys"
                }
            })


        .state('doctorPlusDash', {
                url: '/doctorPlus',
                templateUrl: "app/views/doctorPlus/doctorPlusDash.html",
                parent: 'layout',
                controller: "DoctorPlusDashCtrl",
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('doctorPlusDash.transform', {
                abstract: true,
                url: '/transform',
                templateUrl: "app/views/doctorPlus/doctorPlusTransform.html",
                controller: "DoctorPlusTransformCtrl"
            })
            .state('doctorPlusDash.transform.regulatory', {
                url: '/regulatory',
                data: {
                    'selectedTab': 0,
                    'name': "doctorPlus"

                },
                views: {
                    'regulatory': {
                        templateUrl: 'app/views/doctorPlus/transform/regulatory.html',
                        controller: "RegulatoryCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.education', {
                url: '/education',
                data: {
                    'selectedTab': 1,
                    'name': "doctorPlus"
                },
                views: {
                    'education': {
                        templateUrl: 'app/views/doctorPlus/transform/education.html',
                        controller: "EducationCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.journal', {
                url: '/journal',
                data: {
                    'selectedTab': 2,
                    'name': "doctorPlus"
                },
                views: {
                    'journal': {
                        templateUrl: 'app/views/doctorPlus/transform/journal.html',
                        controller: "JournalCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.medicalInnovation', {
                url: '/medicalInnovation',
                data: {
                    'selectedTab': 3,
                    'name': "doctorPlus"
                },
                views: {
                    'medicalInnovation': {
                        templateUrl: 'app/views/doctorPlus/transform/medicalInnovation.html',
                        controller: "MedicalInnovationCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.webcasts', {
                url: '/webcasts',
                data: {
                    'selectedTab': 4,
                    'name': "doctorPlus"
                },
                views: {
                    'webcasts': {
                        templateUrl: 'app/views/doctorPlus/transform/webcasts.html',
                        controller: "WebcastsCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.bestPractices', {
                url: '/bestPractices',
                data: {
                    'selectedTab': 5,
                    'name': "doctorPlus"
                },
                views: {
                    'bestPractices': {
                        templateUrl: 'app/views/doctorPlus/transform/bestPractices.html',
                        controller: "BestPracticesCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.caseStudies', {
                url: '/caseStudies',
                data: {
                    'selectedTab': 6,
                    'name': "doctorPlus"
                },
                views: {
                    'caseStudies': {
                        templateUrl: 'app/views/doctorPlus/transform/caseStudies.html',
                        controller: "CaseStudiesCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.whitepapers', {
                url: '/whitepapers',
                data: {
                    'selectedTab': 7,
                    'name': "doctorPlus"
                },
                views: {
                    'whitepapers': {
                        templateUrl: 'app/views/doctorPlus/transform/whitepapers.html',
                        controller: "WhitepapersCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.videos', {
                url: '/videos',
                data: {
                    'selectedTab': 8,
                    'name': "doctorPlus"
                },
                views: {
                    'videos': {
                        templateUrl: 'app/views/doctorPlus/transform/videos.html',
                        controller: "VideosCtrl"
                    }
                }
            })
            .state('doctorPlusDash.transform.clinicalTrials', {
                url: '/clinicalTrials',
                data: {
                    'selectedTab': 9,
                    'name': "doctorPlus"
                },
                views: {
                    'clinicalTrials': {
                        templateUrl: 'app/views/doctorPlus/transform/clinicalTrials.html',
                        controller: "ClinicalTrialsCtrl"
                    }
                }
            })

        .state('transformView', {
                url: '/doctorPlus/transform/:from/:id',
                templateUrl: "app/views/doctorPlus/transform/transformView.html",
                controller: "TransformViewCtrl",
                parent: 'layout',
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('doctorPlusDash.share', {
                url: '/share',
                templateUrl: "app/views/doctorPlus/doctorPlusShare.html",
                controller: "DoctorPlusShareCtrl",
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('doctorPlusDash.shareDetails', {
                url: '/share/details/:postId',
                templateUrl: "app/views/doctorPlus/share/shareDetails.html",
                controller: "ShareDetailsCtrl",
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('doctorPlusDash.connect', {
                abstract: true,
                url: '/connect',
                templateUrl: 'app/views/doctorPlus/doctorPlusConnect.html',
                controller: "DoctorPlusConnectCtrl",
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('doctorPlusDash.connect.myContacts', {
                url: '/myContacts',
                data: {
                    'selectedTab': 0,
                    'name': "doctorPlus"
                },
                views: {
                    'myContacts': {
                        templateUrl: 'app/views/doctorPlus/connect/myContacts.html',
                        controller: "MyContactsCtrl"
                    }
                }
            })
            .state('doctorPlusDash.connect.suggestedContacts', {
                url: '/suggestedContacts',
                data: {
                    'selectedTab': 1,
                    'name': "doctorPlus"
                },
                views: {
                    'suggestedContacts': {
                        templateUrl: 'app/views/doctorPlus/connect/suggestedContacts.html',
                        controller: "SuggestedContactsCtrl"
                    }
                }
            })
            .state('doctorPlusDash.connect.pendingContacts', {
                url: '/pendingContacts',
                data: {
                    'selectedTab': 2,
                    'name': "doctorPlus"
                },
                views: {
                    'pendingContacts': {
                        templateUrl: 'app/views/doctorPlus/connect/pendingContacts.html',
                        controller: "PendingContactsCtrl"
                    }
                }
            })
            .state('doctorPlusDash.connect.myGroups', {
                url: '/myGroups',
                data: {
                    'selectedTab': 3,
                    'name': "doctorPlus"
                },
                views: {
                    'myGroups': {
                        templateUrl: 'app/views/doctorPlus/connect/myGroups.html',
                        controller: "MyGroupsCtrl"
                    }
                }
            })
            .state('doctorPlusDash.connect.suggestedGroups', {
                url: '/suggestedGroups',
                data: {
                    'selectedTab': 4,
                    'name': "doctorPlus"
                },
                views: {
                    'suggestedGroups': {
                        templateUrl: 'app/views/doctorPlus/connect/suggestedGroups.html',
                        controller: "SuggestedGroupsCtrl"
                    }
                }
            })
            .state('doctorPlusDash.connect.pendingGroups', {
                url: '/pendingGroups',
                data: {
                    'selectedTab': 5,
                    'name': "doctorPlus"
                },
                views: {
                    'pendingGroups': {
                        templateUrl: 'app/views/doctorPlus/connect/pendingGroups.html',
                        controller: "PendingGroupsCtrl"
                    }
                }
            })
            .state('connectView', {
                url: '/doctorPlus/connect/:from/:id',
                templateUrl: "app/views/doctorPlus/connect/connectView.html",
                controller: "ConnectViewCtrl",
                parent: 'layout',
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('addContacts', {
                url: '/doctorPlus/addContacts',
                templateUrl: "app/views/doctorPlus/connect/addContact.html",
                controller: "AddContactCtrl",
                parent: 'layout',
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('groupView', {
                url: '/doctorPlus/groups/:from/:id',
                templateUrl: "app/views/doctorPlus/connect/groupView.html",
                controller: "GroupViewCtrl",
                parent: 'layout',
                data: {
                    'name': "doctorPlus"
                }
            })
            .state('doctorPlusDash.serve', {
                url: '/serve',
                templateUrl: "app/views/doctorPlus/DoctorPlusServe.html",
                controller: "DoctorPlusServeCtrl",
                data: {
                    'name': "doctorPlus"
                }
            })


    }])
    .config(['$mdThemingProvider', function($mdThemingProvider) {

        //Angular Material Theme Configuration
        $mdThemingProvider.theme('default')
            .primaryPalette('blue')
            .accentPalette('red');

        $mdThemingProvider.theme('docs-dark', 'default')
            .primaryPalette('yellow')
            .accentPalette('green')
            .dark();
        $mdThemingProvider.theme('altTheme')
            .primaryPalette('light-blue')
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
