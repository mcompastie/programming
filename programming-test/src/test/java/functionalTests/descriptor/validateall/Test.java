/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2012 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package functionalTests.descriptor.validateall;

import java.io.File;
import java.io.IOException;

import org.objectweb.proactive.core.descriptor.data.ProActiveDescriptor;
import functionalTests.FunctionalTest;
import org.apache.xerces.parsers.SAXParser;
import org.junit.Before;
import org.junit.Ignore;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import static org.junit.Assert.assertTrue;


/**
 * A stripped-down Active Object example. The object has only one public method,
 * sayHello() The object does nothing but reflect the host its on.
 */
@Ignore
// no more files to test
public class Test extends FunctionalTest {
    ProActiveDescriptor pad;
    transient SAXParser parser = null;
    transient Validator handler = null;

    @org.junit.Test
    public void action() throws Exception {
        // locates the descriptor directory
        // FIXME if ever the classes are compiled to a location other than a direct child of the PROACTIVE_DIR, the descriptor folder won't be found
        File descriptorsfolder = new File("../descriptors");
        assertTrue("\"" + descriptorsfolder + "\" directory could not be found", descriptorsfolder.exists());
        assertTrue("\"" + descriptorsfolder + "\" is not a directory", descriptorsfolder.isDirectory());
        assertTrue("\"" + descriptorsfolder + "\" is read-protected", descriptorsfolder.canRead());

        // recurse into directory tree to locate xml files
        checkValidationRecursive(descriptorsfolder);

        assertTrue(!handler.validationError);
    }

    private void checkValidationRecursive(File basedirectory) throws SAXException, IOException {
        for (File file : basedirectory.listFiles()) {
            if (file.isDirectory() && file.canRead()) {
                checkValidationRecursive(file);
            } else if (file.canRead()) {
                String filename = file.getName();
                int index = filename.lastIndexOf('.');

                if (index > 0) {
                    String extension = filename.substring(index + 1, filename.length());

                    if (extension.equals("xml")) {
                        checkValidation(file);
                    }
                }
            }
        }
    }

    private void checkValidation(File xmlDescriptor) throws SAXException, IOException {
        //Since Validator implements ErrorHandler, you can use it to parse the example XML document. 
        // The parse methods parse(java.lang.String systemId) and parse(org.xml.sax.InputSource inputSource) 
        // may be used for parsing an XML document.
        try {
            parser.parse(xmlDescriptor.getAbsolutePath());
        } catch (SAXException exp) {
            exp.printStackTrace();
        } finally {
            if (handler.validationError) {
                System.out.println("Error occured when parsing \"" + xmlDescriptor.getAbsolutePath() + "\".");
                throw new SAXException(handler.saxParseException);
            }
        }

        //The errors generated by the parser get registered with the ErrorHandler 
    }

    @Before
    public void initTest() throws Exception {
        //To validate with a SAXParser, create a SAXParser. The SAXParser class is a subclass of the XMLParser class.
        parser = new SAXParser();

        //Set the validation feature to true to report validation errors. If the validation feature is set to true, the XML document should specify a XML schema or a DTD.
        parser.setFeature("http://apache.org/xml/features/validation/dynamic", true);

        //Set the validation/schema feature to true to report validation errors against a schema.
        parser.setFeature("http://apache.org/xml/features/validation/schema", true);

        //Set the validation/schema-full-checking feature to true to enable full schema, grammar-constraint checking.
        parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);

        handler = new Validator();
        parser.setErrorHandler(handler);

        /* Specify a validation schema for the parser with the schema/external-noNamespaceSchemaLocation or 
         * the schema/external-schemaLocation property. The schema/external-schemaLocation property is used 
         * to specify a schema with a namespace. A schema list may be specified with the schema/external-schemaLocation 
         * property. The schema/external-noNamespaceSchemaLocation property is used to specify a schema that does not 
         * have a namespace. A parser is not required to locate a schema specified with the schema/external-noNamespaceSchemaLocation 
         * and schema/external-schemaLocation properties. For our purposes, a schema without a namespace 
         * is used to validate an XML document.
         */
        //
        //		parser.setProperty(
        //		     "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
        //		     SchemaUrl);
    }

    public static void main(String[] args) {
        Test test = new Test();

        try {
            test.initTest();
            test.action();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Validator extends DefaultHandler {
        public boolean validationError = false;
        public SAXParseException saxParseException = null;

        @Override
        public void error(SAXParseException exception) throws SAXException {
            validationError = true;
            saxParseException = exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            validationError = true;
            saxParseException = exception;
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
        }
    }
}
