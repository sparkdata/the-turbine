/**
 * The MIT License
 * Copyright (c) 2015 the-james-burton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jimsey.projects.turbine.spring.camel.typeconverters;

import java.io.IOException;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.jimsey.projects.turbine.spring.web.ReplyResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class TurbineObjectConverter {

  private static ObjectMapper json = new ObjectMapper();

  @Converter
  public static ReplyResponse toReplyResponse(final String text, final Exchange exchange) {
    ReplyResponse stock = null;
    try {
      stock = json.readValue(text, ReplyResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stock;
  }

  @Converter
  public static ReplyResponse toReplyResponse(final byte[] bytes, final Exchange exchange) {
    ReplyResponse stock = null;
    try {
      stock = json.readValue(bytes, ReplyResponse.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stock;
  }

}