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

import com.webhiob.shared.utils.Parser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Robert on 2015-05-30.
 */
public class ParserTest
{

    @Test
    public void testCorrectnessOfParserForSimpleCamelCaseName() {
	String riaSecurity = Parser.getWordsFromCamelCaseName("AutomaticRedirectionAfterSessionTimeout");
	Assert.assertEquals("Automatic Redirection After Session Timeout", riaSecurity);
    }

    @Test
    public void testCorrectnessOfParserForNameWithAcronym() {
	String riaSecurity = Parser.getWordsFromCamelCaseName("RIASecurity");
	Assert.assertEquals("RIA Security", riaSecurity);
    }

    @Test
    public void testJsonpToJsonParser()
    {
        String jsonp = "angular.callbacks._1({date:\"09:04:49\", nothing: alert(\"XSS\")});";
        String json = Parser.jsonpToSafeJsonParser(jsonp);
        Assert.assertEquals("{\"date\":\"09:04:49\", \"nothing\": \"alert\",\"XSS\":null}", json);
    }
}
