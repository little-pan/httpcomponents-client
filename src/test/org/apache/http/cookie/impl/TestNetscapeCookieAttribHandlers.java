/*
 * $HeadURL$
 * $Revision$
 * $Date$
 * ====================================================================
 *
 *  Copyright 2002-2006 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.cookie.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;

public class TestNetscapeCookieAttribHandlers extends TestCase {

    public TestNetscapeCookieAttribHandlers(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestNetscapeCookieAttribHandlers.class);
    }

    // ------------------------------------------------------------------- Main
    public static void main(String args[]) {
        String[] testCaseName = { TestNetscapeCookieAttribHandlers.class.getName() };
        junit.textui.TestRunner.main(testCaseName);
    }

    public void testNetscapeDomainValidate1() throws Exception {
        Cookie cookie = new Cookie("name", "value"); 
        CookieOrigin origin = new CookieOrigin("somehost", 80, "/", false); 
        CookieAttributeHandler h = new NetscapeDomainHandler();
        
        cookie.setDomain("somehost");
        h.validate(cookie, origin);

        cookie.setDomain("otherhost");
        try {
            h.validate(cookie, origin);
            fail("MalformedCookieException should have been thrown");
        } catch (MalformedCookieException ex) {
            // expected
        }
    }

    public void testNetscapeDomainValidate2() throws Exception {
        Cookie cookie = new Cookie("name", "value"); 
        CookieOrigin origin = new CookieOrigin("www.somedomain.com", 80, "/", false); 
        CookieAttributeHandler h = new NetscapeDomainHandler();
        
        cookie.setDomain(".somedomain.com");
        h.validate(cookie, origin);

        cookie.setDomain(".otherdomain.com");
        try {
            h.validate(cookie, origin);
            fail("MalformedCookieException should have been thrown");
        } catch (MalformedCookieException ex) {
            // expected
        }
        cookie.setDomain("www.otherdomain.com");
        try {
            h.validate(cookie, origin);
            fail("MalformedCookieException should have been thrown");
        } catch (MalformedCookieException ex) {
            // expected
        }
    }

    public void testNetscapeDomainValidate3() throws Exception {
        Cookie cookie = new Cookie("name", "value"); 
        CookieOrigin origin = new CookieOrigin("www.a.com", 80, "/", false); 
        CookieAttributeHandler h = new NetscapeDomainHandler();
        
        cookie.setDomain(".a.com");
        h.validate(cookie, origin);

        cookie.setDomain(".com");
        try {
            h.validate(cookie, origin);
            fail("MalformedCookieException should have been thrown");
        } catch (MalformedCookieException ex) {
            // expected
        }
    }

    public void testNetscapeDomainValidate4() throws Exception {
        Cookie cookie = new Cookie("name", "value"); 
        CookieOrigin origin = new CookieOrigin("www.a.b.c", 80, "/", false); 
        CookieAttributeHandler h = new NetscapeDomainHandler();
        
        cookie.setDomain(".a.b.c");
        h.validate(cookie, origin);

        cookie.setDomain(".b.c");
        try {
            h.validate(cookie, origin);
            fail("MalformedCookieException should have been thrown");
        } catch (MalformedCookieException ex) {
            // expected
        }
    }
    
    public void testNetscapeDomainMatch1() throws Exception {
        Cookie cookie = new Cookie("name", "value"); 
        CookieOrigin origin = new CookieOrigin("www.somedomain.com", 80, "/", false); 
        CookieAttributeHandler h = new NetscapeDomainHandler();

        cookie.setDomain(null);
        assertFalse(h.match(cookie, origin));
        
        cookie.setDomain(".somedomain.com");
        assertTrue(h.match(cookie, origin));
    }

    public void testNetscapeDomainMatch2() throws Exception {
        Cookie cookie = new Cookie("name", "value"); 
        CookieOrigin origin = new CookieOrigin("www.whatever.somedomain.com", 80, "/", false); 
        CookieAttributeHandler h = new NetscapeDomainHandler();

        cookie.setDomain(".somedomain.com");
        assertTrue(h.match(cookie, origin));
    }

    public void testNetscapeDomainInvalidInput() throws Exception {
        CookieAttributeHandler h = new NetscapeDomainHandler();
        try {
            h.match(null, null);
            fail("IllegalArgumentException must have been thrown");
        } catch (IllegalArgumentException ex) {
            // expected
        }
        try {
            h.match(new Cookie("name", "value"), null);
            fail("IllegalArgumentException must have been thrown");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
    
}