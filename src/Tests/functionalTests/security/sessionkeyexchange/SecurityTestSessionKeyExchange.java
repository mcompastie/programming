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
package functionalTests.security.sessionkeyexchange;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.proactive.core.security.PolicyServer;
import org.objectweb.proactive.core.security.ProActiveSecurityDescriptorHandler;
import org.objectweb.proactive.core.security.ProActiveSecurityManager;
import org.objectweb.proactive.core.security.SecurityConstants.EntityType;

import functionalTests.FunctionalTest;


/**
 * Test if the session exchange works.
 *
 * @author The ProActive Team
 *
 */
public class SecurityTestSessionKeyExchange extends FunctionalTest {

    /**
     *
     */
    private ProActiveSecurityManager psm = null;

    @Test
    public void action() throws Exception {
        ProActiveSecurityManager psm1 = psm.generateSiblingCertificate(EntityType.NODE, "Caller");
        DummySecurityEntity entity1 = new DummySecurityEntity(psm1);

        ProActiveSecurityManager psm2 = psm.generateSiblingCertificate(EntityType.NODE, "Callee");
        DummySecurityEntity entity2 = new DummySecurityEntity(psm2);

        entity1.initiateSession(1, entity2);
    }

    @Before
    public void initTest() throws Exception {
        PolicyServer ps = ProActiveSecurityDescriptorHandler
                .createPolicyServer(SecurityTestSessionKeyExchange.class.getResource(
                        "../applicationPolicy.xml").getPath());
        psm = new ProActiveSecurityManager(EntityType.APPLICATION, ps);
    }
}
