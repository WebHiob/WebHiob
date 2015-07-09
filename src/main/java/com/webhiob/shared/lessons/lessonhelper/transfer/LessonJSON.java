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

package com.webhiob.shared.lessons.lessonhelper.transfer;

import com.webhiob.GWT.utils.LessonTechnology;
import lombok.Data;

public @Data class LessonJSON
{
    private String id;
    private String text;
    private String name;
    private String viewName;
    private LessonTechnology technology;

    public String getText() {
        return name + " (" +  technology + ")";
    }
}
