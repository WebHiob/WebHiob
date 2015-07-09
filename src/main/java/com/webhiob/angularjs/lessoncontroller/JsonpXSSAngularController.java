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

import com.webhiob.shared.utils.Parser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
@RequestMapping("jsonpAngular")
public class JsonpXSSAngularController
{
    @RequestMapping(value = "/insecure")
    public String insecureJsonp(@RequestParam(value = "callback") String callback)
    {
	return callback + "({date:\"09:04:49\", attack:alert(\"JSONP XSS\")});";
    }



    /*
    // Solution of the JSONP XSS lesson
    //
    //
    //
    //
    //
    //
    @RequestMapping(value = "/secure") public String secureJsonp(@RequestParam(value = "url") String url, @RequestParam(value = "callback") String callback)
		    throws IOException
    {
	String secureJsonp;
	URL insecureUrl = new URL(url + "?callback=" + callback);
	try (InputStream inputStream = insecureUrl.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream)))
	{
	    String jsonpResponse = "";
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	    {
		jsonpResponse += inputLine;
	    }
	    secureJsonp = Parser.jsonpToSafeJsonParser(jsonpResponse);
	    secureJsonp = callback + "(" + secureJsonp + ");";
	}
	return secureJsonp;
    }
    */

}
