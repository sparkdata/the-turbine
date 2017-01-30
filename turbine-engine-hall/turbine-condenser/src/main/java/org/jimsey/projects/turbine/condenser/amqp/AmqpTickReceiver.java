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
package org.jimsey.projects.turbine.condenser.amqp;

import java.lang.invoke.MethodHandles;

import javax.validation.constraints.NotNull;

import org.jimsey.projects.turbine.condenser.reactor.ReactorTickReceiver;
import org.jimsey.projects.turbine.fuel.domain.DomainObjectGenerator;
import org.jimsey.projects.turbine.fuel.domain.RandomDomainObjectGenerator;
import org.jimsey.projects.turbine.fuel.domain.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javaslang.control.Try;

/**
 * See AmqpSetup for details of how this class is wired up to RabbitMQ
 * @author the-james-burton
 */
@Component
@ManagedResource
public class AmqpTickReceiver extends BaseConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  @NotNull
  private ReactorTickReceiver tickReceiver;
  
  /** TESTING ONLY **/
  private DomainObjectGenerator dog = new RandomDomainObjectGenerator(Ticker.of("ABC.L"));
  /**
   * TESTING ONLY
   */
  @ManagedOperation
  public void sendMessage() {
    Try.run(() -> handleMessage(dog.newTick().toString())).orElseRun((e) -> logger.info("could not send tick:{}", e));
  }  

  /**
   * handle inbound ticks from RabbitMQ
   * @param the inbound message from RabbitMQ as a String
   */
  @RabbitListener(queues = "#{queueTicks}")
  public void handleMessage(String message) {
    logger.info(" ...> TickReceiver: Spring AMQP received [{}]", message);
    getTickReceiver().getTopic().onNext(message);
  }

  public ReactorTickReceiver getTickReceiver() {
    return tickReceiver;
  }

  public void setTickReceiver(ReactorTickReceiver tickReceiver) {
    this.tickReceiver = tickReceiver;
  }

}
