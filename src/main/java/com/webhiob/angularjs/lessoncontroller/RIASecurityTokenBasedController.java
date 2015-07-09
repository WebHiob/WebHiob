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
import com.webhiob.shared.utils.token.TokenTransfer;
import com.webhiob.shared.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController()
@RequestMapping("riaToken")
public class RIASecurityTokenBasedController
{

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PermitAll
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded")
    public TokenTransfer authenticate(  @RequestParam(value="username") String username,
					@RequestParam(value="password") String password
					)
    {
	UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(username, password);
	Authentication authentication = authenticationManager.authenticate(authenticationToken);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	UserDetails userDetails = userService.loadUserByUsername(username);

	return new TokenTransfer(TokenUtils.createToken(userDetails));
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "secret", method = RequestMethod.GET, produces = "application/json")
    public SimpleJSONMessage getSecretContent()
    {
	return new SimpleJSONMessage("This is the secret message from the server");
    }

    @PermitAll
    @RequestMapping(value = "public", method = RequestMethod.GET, produces = "application/json")
    public SimpleJSONMessage getPublicContent()
    {
	return new SimpleJSONMessage("This is the public message from the server");
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "admin", method = RequestMethod.GET, produces = "application/json")
    public SimpleJSONMessage getAdministratorContent()
    {
	return new SimpleJSONMessage("This is the message from the server only for administrator");
    }
}

