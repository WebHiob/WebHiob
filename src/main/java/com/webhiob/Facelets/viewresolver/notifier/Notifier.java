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

package com.webhiob.Facelets.viewresolver.notifier;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

public abstract class Notifier
{
    public void notifyPhaseListener(PhaseId phaseId, Lifecycle lifecycle, FacesContext context)
    {
	PhaseEvent phaseEvent = new PhaseEvent(context, phaseId, lifecycle);
	for (int i = 0; i < lifecycle.getPhaseListeners().length; i++)
	{
	    PhaseListener[] phaseListeners = lifecycle.getPhaseListeners();
	    PhaseListener listener = phaseListeners[i];
	    if (listener.getPhaseId() == phaseId || listener.getPhaseId() == PhaseId.ANY_PHASE)
	    {
		notify(listener, phaseEvent);
	    }
	}
    }
    protected abstract void notify (PhaseListener listener, PhaseEvent phaseEvent);
}
