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

package com.webhiob.shared.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class SessionAccessAwareRequest extends HttpServletRequestWrapper
{
    public static final String LAST_ACCESS_SESSION_ATTR = "lastAccessTime";

    public SessionAccessAwareRequest(HttpServletRequest request) {
	super(request);
    }

    @Override
    public HttpSession getSession() {
	return getSession(true);
    }

    @Override
    public HttpSession getSession(boolean create) {
	HttpSession session = super.getSession(create);
	if (session != null) {
	    session.setAttribute(LAST_ACCESS_SESSION_ATTR, System.currentTimeMillis());
	}
	return session;
    }
}
