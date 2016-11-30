/**
 * The MIT License
 * Copyright (c) ${project.inceptionYear} the-james-burton
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
package org.jimsey.projects.turbine.condenser.camel.splitters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.Message;
import org.jimsey.projects.turbine.condenser.domain.Stock;
import org.jimsey.projects.turbine.fuel.domain.TickJson;
import org.jimsey.projects.turbine.fuel.domain.Ticker;
import org.springframework.stereotype.Component;

@Component
public class StrategySplitter extends BaseSplitter {

  public List<Message> split(@Headers Map<String, Object> headers, @Body TickJson tick) {
    logger.debug(" ---- in strategy splitter");
    // Stock stock = marketsManager.findMarket(tick.getTickerAsObject().getMarket()).findStock(tick.getTickerAsObject());
    Ticker ticker = tick.getTickerAsObject();
    Stock stock = tickerManager.findStock(ticker);
    return stock.getStrategies().stream()
        .map(strategy -> createMessage(strategy.run(tick), headers))
        .collect(Collectors.toList());
  }

}
