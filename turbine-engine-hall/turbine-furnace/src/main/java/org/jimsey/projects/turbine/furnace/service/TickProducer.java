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
package org.jimsey.projects.turbine.furnace.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jimsey.projects.turbine.fuel.domain.DomainObjectGenerator;
import org.jimsey.projects.turbine.fuel.domain.RandomDomainObjectGenerator;
import org.jimsey.projects.turbine.fuel.domain.TickJson;
import org.jimsey.projects.turbine.furnace.TurbineFurnaceConstants;
import org.jimsey.projects.turbine.furnace.camel.routes.TickPublishingRoute;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;

import com.sun.istack.NotNull;

@ConfigurationProperties(prefix = "producer")
public class TickProducer {

  private static final Logger logger = LoggerFactory.getLogger(TickProducer.class);

  @Autowired
  @NotNull
  private CamelContext camel;

  private final String market;

  private final String symbol;

  private final DomainObjectGenerator rdog;

  private ProducerTemplate producer;

  public TickProducer(String market, String symbol) {
    this.market = market;
    this.symbol = symbol;
    this.rdog = new RandomDomainObjectGenerator(market, symbol);
  }

  @PostConstruct
  public void init() {
    logger.info(String.format("camel=%s", camel.getName()));
    producer = camel.createProducerTemplate();
    logger.info("producer initialised");
  }

  public TickJson createBody() {
    TickJson tick = rdog.newTick();
    return tick;
  }

  @Scheduled(fixedDelay = TurbineFurnaceConstants.PRODUCER_PERIOD)
  public void produce() {

    Map<String, Object> headers = new HashMap<String, Object>();

    // byte[] body = DomainConverter.toBytes(quote, null);
    // byte[] body = mCamel.getTypeConverter().convertTo(byte[].class, object);
    TickJson tick = createBody();
    headers.put(TurbineFurnaceConstants.HEADER_FOR_OBJECT_TYPE, tick.getClass().getName());

    logger.info("producing: [body: {}, headers: {}]", tick.toString(), new JSONObject(headers));

    String text = camel.getTypeConverter().convertTo(String.class, tick);
    producer.sendBodyAndHeaders(TickPublishingRoute.IN, text, headers);
  }

  public String getMarket() {
    return market;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
