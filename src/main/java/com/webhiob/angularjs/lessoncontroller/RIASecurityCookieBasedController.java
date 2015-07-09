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
package com.webhiob.angularjs.lessoncontroller;

import com.webhiob.angularjs.utils.SimpleJSONMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Robert on 2015-04-13.
 */
@RestController
@RequestMapping("riaCookie")
public class RIASecurityCookieBasedController
{
    @RequestMapping(value = "secret", method = RequestMethod.GET, produces = "application/json")
    public SimpleJSONMessage getSecretContent()
    {
        return new SimpleJSONMessage("This is the secret message from the server");
    }

    @RequestMapping(value = "public", method = RequestMethod.GET, produces = "application/json")
    public SimpleJSONMessage getPublicContent()
    {
        return new SimpleJSONMessage("This is the public message from the server");
    }
}
