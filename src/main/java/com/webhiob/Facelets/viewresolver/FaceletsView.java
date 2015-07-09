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

package com.webhiob.Facelets.viewresolver;

import com.webhiob.Facelets.viewresolver.notifier.AfterNotifier;
import com.webhiob.Facelets.viewresolver.notifier.BeforeNotifier;
import com.webhiob.Facelets.viewresolver.notifier.Notifier;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.PhaseId;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class FaceletsView extends AbstractUrlBasedView
{
    private Lifecycle faceletLifecycle;

    private Notifier afterNotifier = new AfterNotifier();

    private Notifier beforeNotifier = new BeforeNotifier();

    @Override
    public void afterPropertiesSet() throws Exception
    {
	super.afterPropertiesSet();
	faceletLifecycle = createFacesLifecycle();
    }

    private Lifecycle createFacesLifecycle()
    {
	LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
	return factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws
		    Exception
    {

	FacesContext facesContext = createFacesContext(request, response);
	populateRequestMap(facesContext, model);

	beforeNotifier.notifyPhaseListener(PhaseId.RESTORE_VIEW, faceletLifecycle, facesContext);

	UIViewRoot viewRoot = initViewRoot(facesContext, request);
	facesContext.setViewRoot(viewRoot);
	afterNotifier.notifyPhaseListener(PhaseId.RESTORE_VIEW, faceletLifecycle, facesContext);
	facesContext.setViewRoot(viewRoot);
	facesContext.renderResponse();

	tryRenderView(facesContext, viewRoot);
    }

    private void tryRenderView(FacesContext facesContext, UIViewRoot viewRoot)
    {
	try
	{
	    renderView(facesContext, viewRoot);
	}
	catch (IOException e)
	{
	    throw new FacesException(e);
	}
	finally
	{
	    closeFacesContext(facesContext);
	}
    }

    private void renderView(FacesContext facesContext, UIViewRoot viewRoot) throws IOException
    {
	beforeNotifier.notifyPhaseListener(PhaseId.RESTORE_VIEW, faceletLifecycle, facesContext);
	Application application = facesContext.getApplication();
	ViewHandler viewHandler = application.getViewHandler();
	viewHandler.renderView(facesContext, viewRoot);
	afterNotifier.notifyPhaseListener(PhaseId.RESTORE_VIEW, faceletLifecycle, facesContext);
    }

    private void closeFacesContext(FacesContext facesContext)
    {
	facesContext.responseComplete();
	facesContext.release();
    }

    private UIViewRoot initViewRoot(FacesContext facesContext, HttpServletRequest request)
    {
	ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
	viewHandler.initView(facesContext);

	UIViewRoot viewRoot = viewHandler.createView(facesContext, getUrl());
	viewRoot.setLocale(RequestContextUtils.getLocale(request));
	viewRoot.setTransient(true);

	return viewRoot;
    }

    private void populateRequestMap(FacesContext facesContext, Map model)
    {
	for (Object objectKey : model.keySet())
	{
	    String key = objectKey.toString();

	    ExternalContext externalContext = facesContext.getExternalContext();
	    Map<String, Object> requestMap = externalContext.getRequestMap();
	    Object o = model.get(key);
	    requestMap.put(key, o);
	}
    }

    private FacesContext createFacesContext(HttpServletRequest request, HttpServletResponse response)
    {
	FacesContextFactory factory = (FacesContextFactory) FactoryFinder
			.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
	ServletContext servletContext = getServletContext();
	return factory.getFacesContext(servletContext, request, response, faceletLifecycle);
    }
}
