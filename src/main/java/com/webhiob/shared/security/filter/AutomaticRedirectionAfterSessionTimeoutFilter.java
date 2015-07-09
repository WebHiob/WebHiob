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

package com.webhiob.shared.security.filter;

import com.webhiob.shared.security.SessionAccessAwareRequest;
import org.apache.click.util.ClickUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AutomaticRedirectionAfterSessionTimeoutFilter extends GenericFilterBean
{
    private static final long SESSION_TIMEOUT_IN_MS = 1000 * 60;
    private static final int SESSION_EXPIRED_CODE = 901;

    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		    throws IOException, ServletException
    {
	HttpServletRequest httpRequest = (HttpServletRequest) request;
	if (!ClickUtils.isAjaxRequest(httpRequest))
	{
	    chain.doFilter(new SessionAccessAwareRequest(httpRequest), response);
	    return;
	}

	boolean expired = false;
	HttpSession session = httpRequest.getSession(false);
	if (session == null)
	{
	    expired = true;
	}
	else
	{
	    Long lastAccessTime = (Long) session.getAttribute(SessionAccessAwareRequest.LAST_ACCESS_SESSION_ATTR);
	    if (lastAccessTime == null || lastAccessTime + SESSION_TIMEOUT_IN_MS < System.currentTimeMillis())
	    {
		session.invalidate();
		expired = true;
	    }
	}
	if (!expired)
	{
	    chain.doFilter(request, response);
	}
	else
	{
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    httpResponse.sendError(SESSION_EXPIRED_CODE);
	}
    }
}
