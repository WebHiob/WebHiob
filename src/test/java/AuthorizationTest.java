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

import com.webhiob.angularjs.lessoncontroller.RIASecurityTokenBasedController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
                "classpath:spring-servlet.xml",
                "classpath:security-configuration.xml"})
public class AuthorizationTest
{

    @Autowired
    RIASecurityTokenBasedController riaSecurityTokenBasedController;

    @Autowired
    private UserDetailsService userService;

    @Test public void testValidRole()
    {
	UserDetails userDetails = userService.loadUserByUsername("admin");
	Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
			userDetails.getPassword(), userDetails.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authToken);
        riaSecurityTokenBasedController.getAdministratorContent();
    }

    @Test(expected = AccessDeniedException.class)
    public void testInvalidRole()
    {
	UserDetails userDetails = userService.loadUserByUsername("user");
	Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
			userDetails.getPassword(), userDetails.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authToken);
        riaSecurityTokenBasedController.getAdministratorContent();
    }

}
