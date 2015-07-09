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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.webhiob.GWT.utils.GWTLesson;
import com.webhiob.GWT.utils.URLConstants;

public class JsonpXSS extends Composite implements GWTLesson
{
    private final String RESULTS_ERROR = "error";
    interface JsonpXSSUiBinder
		    extends UiBinder<Widget, JsonpXSS>
    {
    }
    private static JsonpXSSUiBinder ourUiBinder = GWT.create(JsonpXSSUiBinder.class);
    @UiField Label jsonpResultLabel;
    @UiField TextBox customUrlTextBox;

    public JsonpXSS()
    {
	initWidget(ourUiBinder.createAndBindUi(this));
	customUrlTextBox.getElement().setPropertyString("placeholder", "URL");
    }

    @UiHandler("preparedRequestButton")
    public void preparedRequestButtonPressed(ClickEvent event)
    {
	JsonpRequestBuilder jsonpRequestBuilder = new JsonpRequestBuilder();
	jsonpRequestBuilder.requestObject(getPreparedUrl(), new AsyncCallback<JavaScriptObject>()
	{
	    @Override public void onFailure(Throwable caught)
	    {
		jsonpResultLabel.setText(RESULTS_ERROR);
	    }

	    @Override public void onSuccess(JavaScriptObject result)
	    {
		String json = new JSONObject(result).toString();
		jsonpResultLabel.setText(json);
	    }
	});
    }

    private String getPreparedUrl() {
	return URLConstants.JSONP_INSECURE;
    }

    private String getCustomUrl(String url) {
	return url;
    }

    @UiHandler("customRequestButton")
    public void customRequestButtonPressed(ClickEvent event)
    {
	JsonpRequestBuilder jsonpRequestBuilder = new JsonpRequestBuilder();
	String url = customUrlTextBox.getText();
	int i = url.indexOf("?");
	String parameterNameWithValue;
	if (i >= 0)
	{
	    int nextIndex = url.indexOf("&");
	    if (nextIndex >= 0)
	    {
		parameterNameWithValue = url.substring(i + 1, nextIndex);
		url = url.replaceFirst("&", "");
	    }
	    else
	    {
		parameterNameWithValue = url.substring(i+ 1);
		url = url.replace("?", "");
	    }
	    url = url.replace(parameterNameWithValue, "");
	    int paramValueIndex = parameterNameWithValue.indexOf("=");
	    String parameterName = parameterNameWithValue.substring(0,paramValueIndex);
	    jsonpRequestBuilder.setCallbackParam(parameterName);
	}

	jsonpRequestBuilder.requestObject(getCustomUrl(url), new AsyncCallback<JavaScriptObject>()
	{
	    @Override public void onFailure(Throwable caught)
	    {
		jsonpResultLabel.setText(RESULTS_ERROR);
	    }

	    @Override public void onSuccess(JavaScriptObject result)
	    {
		String json = new JSONObject(result).toString();
		jsonpResultLabel.setText(json);
	    }
	});
    }


    /*
    //
    //
    // Solution of the JSONP XSS lesson
    //
    //
    //
    //
    //
    private String getPreparedUrl() {
	String protocolHostAndPort = Window.Location.getProtocol() + "//" + Window.Location.getHost();
	return "/facelets/jsonpGWT/secure?url=" + protocolHostAndPort + URLConstants.JSONP_INSECURE;
    }

    private String getCustomUrl(String url) {
	return "/facelets/jsonpGWT/secure?url=" + url;
    }
    */
}