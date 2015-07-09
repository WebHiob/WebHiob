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

package com.webhiob.Facelets.lessoncontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("automatic_redirection_after_session_timeout")
public class AutomaticRedirectionAfterSessionTimeoutController
{
    @RequestMapping(value = "/login")
    public ModelAndView login()
    {
	return new ModelAndView("automatic_redirection_after_session_timeout/login");
    }

    @RequestMapping(value = "/lesson")
    public ModelAndView lesson()
    {
	return new ModelAndView("automatic_redirection_after_session_timeout/lesson");
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET, produces = "application/json")
    public long getServerTime()
    {
	Date now = new Date();
	return now.getTime();
    }
}
