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

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.apache.camel.CamelContext;
import org.apache.camel.component.log.LogComponent;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.jimsey.projects.camel.components.SpringSimpleMessagingComponent;
import org.jimsey.projects.turbine.condenser.camel.formatters.ToStringLogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class CamelSetup {

  private static final Logger logger = LoggerFactory.getLogger(CamelSetup.class);

  @Autowired
  @NotNull
  private SimpMessagingTemplate websockets;

  @PostConstruct
  public void init() {
    logger.info("camel setup init started...");

    logger.info("camel setup init completed");
  }

  @Bean
  CamelContextConfiguration camelContextConfiguration() {
    return new CamelContextConfiguration() {
      @Override
      public void beforeApplicationStart(CamelContext camel) {
        logger.info("beforeApplicationStart hook started...");

        LogComponent slog = new LogComponent();
        slog.setExchangeFormatter(new ToStringLogFormatter());

        SpringSimpleMessagingComponent ssm = new SpringSimpleMessagingComponent();
        ssm.setMessageSendingOperations(websockets);

        camel.setUseMDCLogging(true);
        camel.addRoutePolicyFactory(new MetricsRoutePolicyFactory());

        camel.addComponent("slog", slog);
        camel.addComponent("ssm", ssm);

        logger.info("beforeApplicationStart hook completed");
      }

      @Override
      public void afterApplicationStart(CamelContext camelContext) {
        // TODO Auto-generated method stub

      }
    };
  }
}
