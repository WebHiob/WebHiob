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
package com.webhiob.GWT.lesson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.webhiob.GWT.js.EmployeeJS;
import com.webhiob.GWT.utils.GWTLesson;
import com.webhiob.GWT.utils.URLConstants;

public class JPQLInjection extends Composite implements GWTLesson
{
    interface JPQLInjectionUiBinder extends UiBinder<Widget, JPQLInjection>
    {
    }

    private static JPQLInjectionUiBinder ourUiBinder = GWT.create(JPQLInjectionUiBinder.class);
    @UiField TextBox searchTextBox;
    @UiField FlexTable resultsFlexTable;
    @UiField Label queryLabel;

    public JPQLInjection()
    {
	initWidget(ourUiBinder.createAndBindUi(this));
	searchTextBox.addChangeHandler(new ChangeHandler()
	{
	    @Override public void onChange(ChangeEvent event)
	    {
		String searchedText = searchTextBox.getText();
		String fullQuery = "SELECT e FROM Employee e WHERE e.name = '";
		fullQuery += searchedText + "'";
		queryLabel.setText(fullQuery);
	    }
	});
	DomEvent.fireNativeEvent(Document.get().createChangeEvent(), searchTextBox);
	initResultsFlexTable();
    }

    private void initResultsFlexTable() {
	resultsFlexTable.removeAllRows();
	resultsFlexTable.setText(0, 0, "Name");
	resultsFlexTable.setText(0, 1, "City");
	resultsFlexTable.setCellPadding(2);

	resultsFlexTable.getRowFormatter().addStyleName(0, "tableHeader");
	resultsFlexTable.addStyleName("tableList");
    }

    @UiHandler("searchSQLButton")
    public void searchSQLButtonPressed(ClickEvent event)
    {
	searchQuery(URLConstants.JSON_SQL_USERS);
    }

    @UiHandler("searchJPQLButton")
    public void searchJPQLButtonPressed(ClickEvent event)
    {
	searchQuery(URLConstants.JSON_JPQL_USERS);
    }

    private void searchQuery(String request) {
	initResultsFlexTable();
	String query = searchTextBox.getText();

	RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, request + query);
	try
	{
	    builder.sendRequest(null, new RequestCallback()
	    {
		public void onError(Request request, Throwable exception)
		{
		}
		public void onResponseReceived(Request request, Response response)
		{
		    if (200 == response.getStatusCode())
		    {
			updateResultsTable(JsonUtils.<JsArray<EmployeeJS>>safeEval(response.getText()));
		    }
		}
	    });
	}
	catch (RequestException ignored) {}
    }

    private void updateResultsTable(JsArray<EmployeeJS> userJsArray)
    {
	for (int i = 0; i < userJsArray.length(); i++)
	{
	    updateResultsTable(userJsArray.get(i));
	}
    }

    private void updateResultsTable(EmployeeJS employeeJS)
    {
	int row = resultsFlexTable.getRowCount();

	resultsFlexTable.setText(row, 0, employeeJS.getName());
	resultsFlexTable.setText(row, 1, employeeJS.getCity());
    }
}