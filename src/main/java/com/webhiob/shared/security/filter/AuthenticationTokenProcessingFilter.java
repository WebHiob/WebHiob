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

import com.webhiob.shared.utils.token.TokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean
{
    private final UserDetailsService userDetailsService;

    public AuthenticationTokenProcessingFilter(UserDetailsService userDetailsService)
    {
	this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
		    ServletException
    {
	HttpServletRequest httpRequest = (HttpServletRequest) request;

	String authToken = httpRequest.getHeader("X-Auth-Token");
	String userName = TokenUtils.getUsernameFromToken(authToken);

	if (userName != null) {
	    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
	    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

	    if (TokenUtils.validateToken(authToken, userDetails)) {
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	}
	chain.doFilter(request, response);
    }
}
