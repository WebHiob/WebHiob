/***************************************************************************************************
 *
 *
 * This file is part of WebHiob, an Robert Rozmus utility.
 * WebHiob was created a as part of the master thesis by Robert Rozmus (student of Warsaw University of Technology, Institute of Computer Science)
 * Copyright (c) 2015 Robert Rozmus
 *
 * This program has got two licences:
 *  1. For non-commercial use - you can redistribute it and/or modify it under the terms of the
 *  		GNU General Public License version 3.0 (GPLv3);
 *
 *  2. For any commercial use (including payable academic lectures) - you must obtain the permission from the author
 *  (Robert Rozmus) to use it in these purposes
 *
 * @author <a href="mailto:robertrozmusjob@gmail.com">Robert Rozmus</a>
 */

var app = angular.module('app', ['ngRoute', 'route-segment', 'view-segment', 'app.services']);
var lastURL = null;
var services = angular.module('app.services', ['ngResource']);
app.config(function ($routeSegmentProvider, $routeProvider, $locationProvider, $httpProvider) {
    $routeSegmentProvider.options.autoLoadTemplates = true;
    $routeSegmentProvider
        .when('/:viewName/lesson', 'lessonSegment')
        .when('/:viewName/lesson/loginTab', 'lessonSegment.loginTab')
        .when('/:viewName/lesson/publicTab', 'lessonSegment.publicTab')
        .when('/:viewName/lesson/secretTab', 'lessonSegment.secretTab')
        .when('/:viewName/lesson/administratorTab', 'lessonSegment.administratorTab')

        .when('/:viewName/problem', 'problemSegment')

        .when('/:viewName/solution', 'solutionSegment')

        .segment('problemSegment', {
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/problem.html';
            },
            controller: function ($routeParams) {
                return 'MainController';
            },
            dependencies: ['viewName']
        })
        .segment('solutionSegment', {
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/solution.html';
            },
            controller: function ($routeParams) {
                return 'MainController';
            },
            dependencies: ['viewName']
        })

        .segment('lessonSegment', {
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/index.html';
            },
            controller: function ($routeParams) {

                return $routeParams.viewName + 'Controller';
            },
            dependencies: ['viewName']
        })
        .within()

        .segment('loginTab', {
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/tabs/loginTab.html';
            },
            controller: function ($routeParams) {
                return $routeParams.viewName + 'Controller';
            }
        })
        .segment('publicTab', {
            'default': true,
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/tabs/publicTab.html';
            },
            controller: function ($routeParams) {
                return $routeParams.viewName + 'PublicTabController';
            }
        })
        .segment('secretTab', {
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/tabs/secretTab.html';
            },
            controller: function ($routeParams) {
                return $routeParams.viewName + 'SecretTabController';
            }
        })

        .segment('administratorTab', {
            templateUrl: function ($routeParams) {
                return 'lesson/' + $routeParams.viewName + '/tabs/administratorTab.html';
            },
            controller: function ($routeParams) {
                return $routeParams.viewName + 'AdministratorTabController';
            }
        });

    $routeProvider.otherwise({redirectTo: '/RIASecurityCookieBased/lesson'});


    /*
    //
    //
    //
    // solution of the Rich Internet Application security with cookie-based and token-based authentication lesson
    //
    //
    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
        return {
            'responseError': function (rejection) {
                var status = rejection.status;
                if (status == 901) {
                    $location.path("/RIASecurityCookieBased/lesson/loginTab");
                } else if (status == 401 || status == 403) {
                    $location.path("/RIASecurityTokenBased/lesson/loginTab");
                }
                return $q.reject(rejection);
            }
        };
    });
    */

    /*
    //
    //
    // solution of the Rich Internet Application security with token-based authentication lesson
    //
    //
    //
    //
    //
    $httpProvider.interceptors.push(function ($q, $rootScope, $location, $window) {
        return {
            'request': function (config) {
                if (config.url.indexOf('RIA') > -1 && config.url.indexOf('login') == -1) {
                    lastURL = $location.path();
                }

                var isRestCall = config.url.indexOf('RIA') > -1 || config.url.indexOf('ria') > -1;
                var authToken = $window.localStorage.token;
                if (isRestCall && authToken != "undefined") {
                    config.headers['X-Auth-Token'] = authToken;
                }
                return config || $q.when(config);
            }
        };
    });
    */
});
