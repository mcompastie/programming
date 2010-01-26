/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2010 INRIA/University of 
 * 				Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 
 * or a different license than the GPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package org.objectweb.proactive.extensions.masterworker.interfaces.internal;

import org.objectweb.proactive.extensions.masterworker.interfaces.DivisibleTask;
import org.objectweb.proactive.extensions.masterworker.interfaces.Task;

import java.io.Serializable;


/**
 * <i><font size="-1" color="#FF0000">**For internal use only** </font></i><br>
 * Internal view of a task in the Master/Worker API<br/>
 * Adds the notion of a "Task ID"<br/>
 * @author The ProActive Team
 *
 * @param <R> the type of the result
 */
public interface TaskIntern<R extends Serializable> extends DivisibleTask<R>, Identifiable, Serializable {

    /**
     * The ID of the NullTask
     */
    long NULL_TASK_ID = -1;

    /**
     * get the actual task
     * @return the task
     */
    Task<R> getTask();

    /**
     * tells if the task is null (nothing to do)
     * @return true if the task is null, false otherwise
     */
    boolean isNull();
}
