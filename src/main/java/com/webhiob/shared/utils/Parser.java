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

package com.webhiob.shared.utils;

import com.google.json.JsonSanitizer;

public class Parser
{
    public static String getWordsFromCamelCaseName(String camelCaseName)
    {
	String allWords = "";
	for (String word : camelCaseName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"))
	{
	    allWords += word + " ";
	}
	allWords = allWords.substring(0, allWords.length() - 1);
	return allWords;
    }

    public static String jsonpToSafeJsonParser(String jsonpResponse)
    {
	int i = jsonpResponse.indexOf("(");
	String json = jsonpResponse.substring(i+ 1, jsonpResponse.length() - 2);
	return JsonSanitizer.sanitize(json);
    }
}
