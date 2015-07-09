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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.webhiob.GWT.js.LessonCategoryJS;
import com.webhiob.GWT.js.LessonJS;
import com.webhiob.GWT.utils.GWTClassFromStringFactory;
import com.webhiob.GWT.utils.LessonTechnology;
import com.webhiob.GWT.utils.URLConstants;

import java.util.HashMap;
import java.util.Map;

public class LessonsComposite extends Composite
{
    private Map<String, Widget> lessonViewNameToWidgetMap = new HashMap<>();
    private Map<String, LessonJS> lessonsTreeMap = new HashMap<>();

    private static LessonsCompositeUiBinder uiBinder = GWT.create(LessonsCompositeUiBinder.class);

    interface LessonsCompositeUiBinder extends UiBinder<Widget, LessonsComposite>
    {
    }

    @UiField LessonTabPanel lessonPanel;
    @UiField Label errorMsgLabel;
    @UiField Tree lessonTree;
    @UiField Label lessonTreeTitle;

    public LessonsComposite()
    {
	initWidget(uiBinder.createAndBindUi(this));
	setWidth("100%");
	setHeight(Window.getClientHeight() + "px");

	lessonTreeTitle.getElement().getStyle().setFontSize(16, Style.Unit.PX);
	lessonPanel.setLessonsMap(lessonViewNameToWidgetMap);
	getAllTreeLessons();
	initLessonTree();
    }

    private void initLessonTree()
    {
	lessonTree.addSelectionHandler(new SelectionHandler<TreeItem>()
	{
	    @Override public void onSelection(SelectionEvent<TreeItem> event)
	    {
		TreeItem selectedItem = event.getSelectedItem();
		TreeItem parentItem = selectedItem.getParentItem();
		if (parentItem == null)
		{
		    boolean opened = selectedItem.getState();
		    selectedItem.setState(!opened);
		}
		else
		{
		    String text = selectedItem.getText();
		    LessonJS lessonJS = lessonsTreeMap.get(text);
		    if (lessonJS != null)
		    {
			Storage sessionStorage = Storage.getSessionStorageIfSupported();
			String url = lessonJS.getUrl();
			if (!lessonJS.getTechnology().equals(LessonTechnology.GWT.toString()))
			{
			    sessionStorage.setItem("lastUrl", url);
			    sessionStorage.setItem("select", Boolean.toString(true));
			    Window.Location.replace(url);

			}
			else
			{
			    lessonPanel.setSelectedLesson(lessonJS);
			}
		    }
		}
	    }
	});
    }

    private void getAllTreeLessons()
    {
	RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URLConstants.JSON_LESSONS);
	try
	{
	    builder.sendRequest(null, new RequestCallback()
	    {
		public void onError(Request request, Throwable exception)
		{
		    displayError("Couldn't retrieve JSON : " + exception.getMessage());
		}

		public void onResponseReceived(Request request, Response response)
		{
		    if (Response.SC_OK == response.getStatusCode())
		    {
			buildLessonTree(response);
		    }
		    else
		    {
			displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")");
		    }
		}
	    });
	}
	catch (RequestException e)
	{
	    displayError("Couldn't retrieve JSON : " + e.getMessage());
	}
    }

    private void buildLessonTree(Response response)
    {
	TreeItem item = null;
	JsArray<LessonCategoryJS> lessonJSArray = JsonUtils.safeEval(response.getText());

	for (int i = 0; i < lessonJSArray.length(); i++)
	{
	    LessonCategoryJS lessonCategoryJS = lessonJSArray.get(i);
	    TreeItem category = new TreeItem();
	    category.setText(lessonCategoryJS.getName());

	    JsArray<LessonJS> lessons = lessonCategoryJS.getChildren();
	    for (int j = 0; j < lessons.length(); j++)
	    {
		LessonJS lesson = lessons.get(j);

		TreeItem lessonItem = new TreeItem();
		Storage sessionStorage = Storage.getSessionStorageIfSupported();
		String lastUrl = sessionStorage.getItem("lastUrl");
		lessonItem.setText(lesson.getText());
		category.addItem(lessonItem);
		lessonsTreeMap.put(lessonItem.getText(), lesson);
		if (lesson.getTechnology().equals(LessonTechnology.GWT.toString()))
		{
		    addLessonToMap(lesson);
		}
		if (lastUrl != null && lastUrl.equals(lesson.getUrl()))
		{
		    item = lessonItem;
		    lessonPanel.setSelectedLesson(lesson);
		    category.setState(true);
		}

	    }
	    lessonTree.addItem(category);
	}
	lessonTree.setSelectedItem(item);
    }

    private void addLessonToMap(LessonJS lesson)
    {
	String targetEntryPointClass = "com.webhiob.GWT.lesson." + lesson.getViewName();
	GWTClassFromStringFactory classFromStringFactory = GWT.create(GWTClassFromStringFactory.class);
	Widget targetEntryPointInstance = (Widget) classFromStringFactory.init(targetEntryPointClass);

	lessonViewNameToWidgetMap.put(lesson.getUrl(), targetEntryPointInstance);
    }


    private void displayError(String error)
    {
	errorMsgLabel.setText("Error: " + error);
	errorMsgLabel.setVisible(true);
    }
}
