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
package org.jimsey.projects.turbine.condenser.component;

import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

import org.jimsey.projects.turbine.condenser.StockFactory;
import org.jimsey.projects.turbine.condenser.domain.Market;
import org.jimsey.projects.turbine.fuel.domain.MarketEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketsManager {

  @Autowired
  @NotNull
  private StockFactory stockFactory;

  // TODO need a better implementation of some sort..?
  private ConcurrentHashMap<MarketEnum, Market> markets = new ConcurrentHashMap<>();

  public Market findMarket(MarketEnum market) {
    markets.computeIfAbsent(market, key -> {
      // TODO upfront setup of markets and stocks...
      return new Market(stockFactory);
    });
    return markets.get(market);
  }

}
