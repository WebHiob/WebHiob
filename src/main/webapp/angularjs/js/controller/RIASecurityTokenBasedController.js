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

app.controller('RIASecurityTokenBasedController', function ($scope, $routeSegment, $location, TokenService, $window) {

    $scope.$routeSegment = $routeSegment;

    $scope.login = function () {
        TokenService.authenticate($.param({
            username: $scope.username,
            password: $scope.password
        }), function (authenticationResult) {

            var authToken = authenticationResult.token;
            $window.localStorage.token = authToken;

            if (lastURL != null) {
                $location.path(lastURL);
                lastURL = null;
            }
        });
    };

});

app.controller('RIASecurityTokenBasedPublicTabController', function ($scope, $routeSegment, $http) {

    $scope.$routeSegment = $routeSegment;

    var actionUrl = '/facelets/riaToken/public',
        load = function () {
            $http({
                method: 'GET',
                url: actionUrl
            }).success(function (data) {
                $scope.publicData = data;
            });
        };
    load();
});

app.controller('RIASecurityTokenBasedSecretTabController', function ($scope, $routeSegment, $http) {

    $scope.$routeSegment = $routeSegment;

    var actionUrl = '/facelets/riaToken/secret',
        load = function () {
            $http({
                method: 'GET',
                url: actionUrl
            }).success(function (data) {
                $scope.secretData = data;
            });
        };
    load();
});

app.controller('RIASecurityTokenBasedAdministratorTabController', function ($scope, $routeSegment, $http) {

    $scope.$routeSegment = $routeSegment;

    var actionUrl = '/facelets/riaToken/admin',
        load = function () {
            $http({
                method: 'GET',
                url: actionUrl
            }).success(function (data) {
                $scope.secretData = data;
            });
        };
    load();
});

services.factory('TokenService', function ($resource) {

    return $resource('/facelets/riaToken/:action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action': 'authenticate'},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    );
});