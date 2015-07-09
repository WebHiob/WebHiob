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

function LessonTree() {
    LessonTree.prototype.init = function ($scope) {

        $('#lessonTree')
            .bind('loaded.jstree', function (e, data) {
                var lessonTree = $.jstree.reference('#lessonTree');
                if (sessionStorage.select) {
                    lessonTree.select_node(sessionStorage.lastUrl);
                    sessionStorage.select = false;
                }
            })
            .jstree({
                "core": {
                    "themes": {
                        "variant": "large"
                    },
                    'data': {
                        'url': function () {
                            return '/facelets/rest/lessons';
                        }
                    }
                },
                "plugins": [
                    "wholerow"
                ]
            }).
            on("select_node.jstree", function (e, data) {
                if (data.action != "ready") {
                    var typ = data.node;
                    if (typ != undefined) {
                        var url = typ.id;
                        if (typ.parent != "#") {
                            $scope.chosenLesson = typ.original.name;
                            $scope.$apply();
                            var lastUrl = sessionStorage.lastUrl;
                            if (lastUrl) {
                                if (lastUrl != url) {
                                    sessionStorage.lastUrl = url;
                                    sessionStorage.select = true;
                                    location.replace(url);
                                    $('#lesson').click();
                                }
                            }
                            else {
                                sessionStorage.lastUrl = url;
                                location.replace(url);
                                sessionStorage.select = true;
                            }
                        }
                    }
                }
            });
    }
}