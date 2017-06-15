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
package org.jimsey.projects.turbine.condenser.service;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.jimsey.projects.turbine.condenser.domain.indicators.trackers.BollingerBands;
import org.jimsey.projects.turbine.condenser.domain.strategies.SMAStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TurbineServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static ObjectMapper json = new ObjectMapper();

  @InjectMocks
  private TurbineService turbineService;

  @Mock
  private Ping ping;

  @Before
  public void setup() throws IOException {
    turbineService = new TurbineService();
    turbineService.init();
  }

  // @Test
  // public void testListStocks() throws Exception {
  // String stocks = turbineService.listStocks(ExchangeEnum.LSE.toString());
  // logger.info("{}", stocks);
  // assertThat(stocks).contains(Stocks.ABC.getExchange());
  // }

  @Test
  public void testListIndicators() throws Exception {
    String indicators = turbineService.listIndicators();
    logger.info("{}", indicators);
    assertThat(indicators).contains(BollingerBands.class.getSimpleName());
    IndicatorClientDefinitions definitions = json.readValue(indicators, IndicatorClientDefinitions.class);
    assertThat(definitions.getIndicators().size()).isGreaterThan(0);
  }

  @Test
  public void testListStrategies() throws Exception {
    String strategies = turbineService.listStrategies();
    logger.info("{}", strategies);
    assertThat(strategies).contains(SMAStrategy.class.getSimpleName());
  }

}
