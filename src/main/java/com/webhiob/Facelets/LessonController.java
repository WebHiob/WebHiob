/***************************************************************************************************
 *
 *
 * This file is part of WebHiob, an Robert Rozmus utility.
 * WebHiob was created a as part of the master thesis by Robert Rozmus (student of Warsaw University of Technology, Institute of Computer Science) WebHiob was created a as part of the master thesis by Robert Rozmus (student of Warsaw University of Technology, Institute of Computer Science)
 * Copyright (c) 2015 Robert Rozmus
 *
 * This program has got two licences:
 *  1. For non-commercial use - you can redistribute it and/or modify it under the terms of the
 *  		GNU General Public License version 3.0 (GPLv3).
 *
 *  2. For any commercial use (including payable academic lectures) - you must obtain the permission from the author
 *  (Robert Rozmus) to use it in these purpose.
 *
 * @author <a href="mailto:robertrozmusjob@gmail.com">Robert Rozmus</a>
 */

package com.webhiob.Facelets;

import com.webhiob.shared.lessons.lessonhelper.creation.Lesson;
import com.webhiob.shared.lessons.lessonhelper.transfer.LessonManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("lesson")
public class LessonController
{
    @RequestMapping(value = "/{viewName}")
    public ModelAndView getLesson(@PathVariable String viewName, HttpSession session)
    {
	ModelMap map = new ModelMap();
	Lesson lesson = LessonManager.getFaceletLesson(viewName);
	initLessonPathBean(lesson, session);

	return new ModelAndView(lesson.getViewName() + "/index", map);
    }

    private void initLessonPathBean(Lesson lesson, HttpSession session)
    {
	String lessonPathBeanName = "lessonPathBean";
	LessonPathBean bean = (LessonPathBean) session.getAttribute(lessonPathBeanName);
	if (bean == null)
	{
	    bean = new LessonPathBean();
	    session.setAttribute(lessonPathBeanName, bean);
	}
	bean.setLessonName(lesson.getName());
	bean.setProblemPath("lesson/" + lesson.getViewName() + "/problem.xhtml");
	bean.setSolutionPath("lesson/" + lesson.getViewName() + "/solution.xhtml");
    }
}
