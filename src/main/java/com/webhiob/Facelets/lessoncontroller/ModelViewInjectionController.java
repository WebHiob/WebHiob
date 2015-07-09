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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("model_view_injection")
public class ModelViewInjectionController
{

    @RequestMapping()
    public ModelAndView getPlayers(@RequestParam("view") String view, @RequestParam(value = "team", defaultValue = "") String team)
    {
	ModelMap map = addPlayersToMapBasedOnTeamName(team);
	return new ModelAndView(view, map);
    }

    private ModelMap addPlayersToMapBasedOnTeamName(String team)
    {
	String[] players;
	String attributeName = "team";
	ModelMap map = new ModelMap();
	switch (team)
	{
	    case "barcelona":
	    {
		players = new String[] {"Dani Alves", "Andres Iniesta", "Lionel Messi", "David Villa"};
		map.addAttribute(attributeName, "FC Barcelona");
		break;
	    }
	    case "juventus":
	    {
		players = new String[] {"Andrea Barzagli", "Claudio Marchisio", "Andrea Pirlo", "Arturo Vidal"};
		map.addAttribute(attributeName, "Juventus");
		break;
	    }
	    case "manchester":
	    {
		players = new String[] {"Rio Ferdinand", "Shinji Kagawa", "Robin Van Persie", "Wayne Rooney"};
		map.addAttribute(attributeName, "Manchester United");
		break;
	    }
	    default:
	    {
		players = new String[] {"There is no information about this team. Probably wrong link"};
		break;
	    }
	}
	map.addAttribute("players", players);
	return map;
    }

    /*
    //
    // Solution of the Model View Injection lesson
    //
    //
    //
    //
    //
    //
    private final String DEFAULT_VIEW = "model_view_injection/index";
    private Map<Integer, String> viewMapping = new HashMap<>();

    @PostConstruct
    public void init()
    {
	viewMapping.put(1, DEFAULT_VIEW);
    }

    @RequestMapping()
    public ModelAndView getPlayer(@RequestParam("view") int view, @RequestParam(value = "team", defaultValue = "") String team)
    {
	String viewName = viewMapping.get(view);
	if (viewName == null)
	{
	    viewName = DEFAULT_VIEW;
	}
	ModelMap map = addPlayersToMapBasedOnTeamName(team);
	return new ModelAndView(viewName, map);
    }
    */

}
