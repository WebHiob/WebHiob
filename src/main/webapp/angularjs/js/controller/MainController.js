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

app.controller('MainController', function ($scope, $routeSegment) {
    $scope.$routeSegment = $routeSegment;

    var lessonTree = new LessonTree();
    lessonTree.init($scope);

    var intervalID;
    var shouldClearInterval = false;

    $("#problem, #solution, #lesson").on("click", function () {
        $scope.highlightSyntax();
    });


    $scope.highlightSyntax = function () {
        intervalID = setInterval(
            function () {
                $('pre code').each(function (i, block) {
                    hljs.highlightBlock(block);
                });
                if (shouldClearInterval == true) {
                    clearInterval(intervalID);
                }
                shouldClearInterval = true;
            }, 50);
    };

    $scope.highlightSyntax();
});
