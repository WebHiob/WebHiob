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

import com.webhiob.shared.jpa.entity.Account;
import com.webhiob.shared.jpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(types = Account.class)
@RequestMapping("data_submission_to_non_editable_fields")
public class DataSubmissionToNonEditableFieldsController
{
    private String viewName = "data_submission_to_non_editable_fields/lesson";
    private String accountParameterName = "account";

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/lesson")
    public ModelAndView lesson()
    {
	ModelMap map = new ModelMap();
	map.addAttribute(accountParameterName, accountRepository.find("hacker"));

	return new ModelAndView(viewName,map);
    }

    @RequestMapping(value = "/lesson", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute Account account)
    {
	accountRepository.update(account);
	String login = account.getLogin();

	ModelMap map = new ModelMap();
	map.addAttribute(accountParameterName, accountRepository.find(login));
	map.addAttribute("confirmation", login + " account has been successfully edited");

	return new ModelAndView(viewName, map);
    }



    /*
    //
    //
    // Solution of the Data Submission To Non Editable Fields lesson
    //
    //
    //
    //
    //
    //
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
	binder.setDisallowedFields("login");
    }
    */

}
