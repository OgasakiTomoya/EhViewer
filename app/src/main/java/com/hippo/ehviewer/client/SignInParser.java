/*
 * Copyright (C) 2015 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.client;

import com.hippo.yorozuya.Say;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInParser {

    static final Pattern NAME_PATTERN = Pattern.compile("<p>You are now logged in as: (.+?)<");
    static final Pattern ERROR_PATTERN = Pattern.compile(
            "(?:<h4>The error returned was:</h4>\\s*<p>(.+?)</p>)"
            + "|(?:<span class=\"postcolor\">(.+?)</span>)");

    public static String parse(String body) throws Exception {
        Matcher m;

        Say.f("TAG", body);

        m = NAME_PATTERN.matcher(body);
        if (m.find()) {
            return m.group(1);
        } else {
            m = ERROR_PATTERN.matcher(body);
            if (m.find()) {
                throw new EhException(m.group(1) == null ? m.group(2) : m.group(1));
            } else {
                throw new EhException("Unknown error"); // TODO
            }
        }
    }
}
