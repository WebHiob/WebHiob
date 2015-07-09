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

package com.webhiob.GWT.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.webhiob.GWT.js.LessonJS;

import java.util.HashMap;
import java.util.Map;

public class LessonTabPanel extends Composite
{
    private Frame frame;
    private LessonJS selectedLesson;
    private Map<String, Widget> lessonsMap = new HashMap<>();
    private final String GWT_BEGIN_PATH = "/GWT/lesson/";

    @UiField SimplePanel lessonPanel;
    @UiField SimplePanel problemPanel;
    @UiField SimplePanel solutionPanel;
    @UiField TabPanel tabPanel;
    @UiField Label lessonTitle;


    public void setLessonsMap(Map<String, Widget> lessonsMap)
    {
	this.lessonsMap = lessonsMap;
    }

    public void setSelectedLesson(LessonJS selectedLesson)
    {
	this.selectedLesson = selectedLesson;
	tabPanel.selectTab(0);
	lessonTitle.setText(selectedLesson.getName());
    }


    public void showSelectedLesson()
    {
        Widget lesson = lessonsMap.get(selectedLesson.getUrl());
        show(lessonPanel, lesson);
    }

    interface ProblemAndSolutionFragmentUiBinder extends UiBinder<Widget, LessonTabPanel>
    {
    }

    private static ProblemAndSolutionFragmentUiBinder ourUiBinder = GWT
		    .create(ProblemAndSolutionFragmentUiBinder.class);

    public LessonTabPanel()
    {
	initWidget(ourUiBinder.createAndBindUi(this));
        Style style = lessonTitle.getElement().getStyle();
        style.setFontSize(20, Style.Unit.PX);
        style.setFontWeight(Style.FontWeight.BOLD);

	frame = new Frame();
	frame.setWidth("100%");
	frame.setHeight("100%");
	frame.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
	frame.getElement().setId("framename");

	frame.addLoadHandler(new LoadHandler()
	{
	    @Override public void onLoad(LoadEvent event)
	    {
		optimizeFrameSize("framename");
	    }
	});
    }


    @UiHandler("tabPanel")
    void onTabSelection(SelectionEvent<Integer> event) {
	switch (event.getSelectedItem()) {
	    case 0:
		showSelectedLesson();
		break;
	    case 1:
		showProblem();
		break;
	    case 2:
		showSolution();
		break;
	}
    }

    private void show(SimplePanel panel, Widget widget)
    {
	panel.clear();
	panel.add(widget);
    }


    protected void showProblem()
    {
	String viewName = selectedLesson.getViewName();
	frame.setUrl(GWT_BEGIN_PATH + viewName + "/problem.html");
	show(problemPanel, frame);
    }

    private void showSolution()
    {
        String viewName = selectedLesson.getViewName();
        frame.setUrl(GWT_BEGIN_PATH + viewName + "/solution.html");
        show(solutionPanel, frame);
    }

    public native boolean optimizeFrameSize(String id)
    /*-{
        var newHeight;
        var newWidth;
        var frame = $doc.getElementById(id);

        if (frame != null) {
            newHeight = frame.contentWindow.document.body.scrollHeight;
            newWidth = frame.contentWindow.document.body.scrollWidth;

            frame.height = (newHeight) + "px";
            frame.width = (newWidth) + "px";
            return true;
        }
        return false;
    }-*/;


}