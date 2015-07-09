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

app.controller('JsonpXSSController', function ($scope, $routeSegment, $http) {
    var errorResponse = 'error';

    $scope.doRequest = function () {
        var url = "/facelets/jsonpAngular/insecure?callback=JSON_CALLBACK";
        $http.jsonp(url)
            .success(function (data) {
                $scope.jsonpResponse = data;
            }).error(function () {
                $scope.jsonpResponse = errorResponse;
            });
    };

    $scope.doCustomRequest = function () {
        var url = $scope.customUrl;
        $http.jsonp(url)
            .success(function (data) {
                $scope.jsonpResponse = data;
            }).error(function () {
                $scope.jsonpResponse = errorResponse;
            });
    };

    /*
    //
    //
    // Solution of the JSONP XSS lesson
    //
    //
    //
    //
    $scope.doRequest = function () {
        var url = "/facelets/jsonpAngular/secure?callback=JSON_CALLBACK&url=" + $location.protocol() + "://" + $location.host() + ":" + $location.port() +  "/Facelets/jsonpAngular/insecure";
        $http.jsonp(url)
            .success(function (data) {
                $scope.jsonpResponse = data;
            }).error(function () {
                $scope.jsonpResponse = errorResponse;
            });
    };

    $scope.doCustomRequest = function () {
        var url = "/facelets/jsonpAngular/secure?callback=JSON_CALLBACK&url=" + $scope.customUrl;
        $http.jsonp(url)
            .success(function (data) {
                $scope.jsonpResponse = data;
            }).error(function () {
                $scope.jsonpResponse = errorResponse;
            });
    };
    */
});
