/*
 * $HeadURL$
 * $Revision$
 * $Date$
 *
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

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;

public class RFC2109VersionHandler extends AbstractCookieAttributeHandler {

    public RFC2109VersionHandler() {
        super();
    }
    
    public void parse(final Cookie cookie, final String value) 
            throws MalformedCookieException {
        if (cookie == null) {
            throw new IllegalArgumentException("Cookie may not be null");
        }
        if (value == null) {
            throw new MalformedCookieException("Missing value for version attribute");
        }
        if (value.trim().equals("")) {
            throw new MalformedCookieException("Blank value for version attribute");
        }
        try {
           cookie.setVersion(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            throw new MalformedCookieException("Invalid version: " 
                + e.getMessage());
        }
    }

    public void validate(final Cookie cookie, final CookieOrigin origin) 
            throws MalformedCookieException {
        if (cookie == null) {
            throw new IllegalArgumentException("Cookie may not be null");
        }
        if (cookie.getVersion() < 0) {
            throw new MalformedCookieException("Cookie version may not be negative");
        }
    }
    
}