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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ConfigurationProperties(prefix = "infrastructure")
public class InfrastructureProperties {

  private static final Logger logger = LoggerFactory.getLogger(InfrastructureProperties.class);

  @NotNull
  private String environmentName;

  @NotNull
  private String amqpServer;

  @NotNull
  private String amqpCamelComponent;

  @NotNull
  private String amqpTicksExchange;

  @NotNull
  private String amqpIndicatorsExchange;

  @NotNull
  private String amqpStrategiesExchange;

  @NotNull
  private String amqpTicksQueue;

  @NotNull
  private String amqpIndicatorsQueue;

  @NotNull
  private String amqpStrategiesQueue;

  @NotNull
  private String elasticsearchCamelComponent;

  @NotNull
  private String elasticsearchCluster;

  @NotNull
  private String elasticsearchHost;

  @NotNull
  private Integer elasticsearchNativePort;

  @NotNull
  private Integer elasticsearchRestPort;

  @NotNull
  private String elasticsearchIndexForTickers;

  @NotNull
  private String elasticsearchIndexForTicks;

  @NotNull
  private String elasticsearchIndexForIndicators;

  @NotNull
  private String elasticsearchIndexForStrategies;

  @NotNull
  private String elasticsearchTypeForTickers;

  @NotNull
  private String elasticsearchTypeForTicks;

  @NotNull
  private String elasticsearchTypeForIndicators;

  @NotNull
  private String elasticsearchTypeForStrategies;

  @NotNull
  private String websocketTicks;

  @NotNull
  private String websocketCamelComponent;

  @NotNull
  private String websocketIndicators;

  @NotNull
  private String websocketStrategies;

  @NotNull
  private String websocketReply;

  @PostConstruct
  public void init() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    logger.info(objectMapper.writeValueAsString(this));
  }

  // ------------------------------------------
  public String getEnvironmentName() {
    return environmentName;
  }

  public void setEnvironmentName(String environmentName) {
    this.environmentName = environmentName;
  }

  public String getAmqpServer() {
    return amqpServer;
  }

  public void setAmqpServer(String amqpServer) {
    this.amqpServer = amqpServer;
  }

  public String getAmqpTicksExchange() {
    return amqpTicksExchange;
  }

  public void setAmqpTicksExchange(String amqpTicksExchange) {
    this.amqpTicksExchange = amqpTicksExchange;
  }

  public String getAmqpIndicatorsExchange() {
    return amqpIndicatorsExchange;
  }

  public void setAmqpIndicatorsExchange(String amqpIndicatorsExchange) {
    this.amqpIndicatorsExchange = amqpIndicatorsExchange;
  }

  public String getAmqpStrategiesExchange() {
    return amqpStrategiesExchange;
  }

  public void setAmqpStrategiesExchange(String amqpStrategiesExchange) {
    this.amqpStrategiesExchange = amqpStrategiesExchange;
  }

  public String getAmqpTicksQueue() {
    return amqpTicksQueue;
  }

  public void setAmqpTicksQueue(String amqpTicksQueue) {
    this.amqpTicksQueue = amqpTicksQueue;
  }

  public String getAmqpIndicatorsQueue() {
    return amqpIndicatorsQueue;
  }

  public void setAmqpIndicatorsQueue(String amqpIndicatorsQueue) {
    this.amqpIndicatorsQueue = amqpIndicatorsQueue;
  }

  public String getAmqpStrategiesQueue() {
    return amqpStrategiesQueue;
  }

  public void setAmqpStrategiesQueue(String amqpStrategiesQueue) {
    this.amqpStrategiesQueue = amqpStrategiesQueue;
  }

  public String getElasticsearchHost() {
    return elasticsearchHost;
  }

  public void setElasticsearchHost(String elasticsearchHost) {
    this.elasticsearchHost = elasticsearchHost;
  }

  public String getWebsocketTicks() {
    return websocketTicks;
  }

  public void setWebsocketTicks(String websocketTicks) {
    this.websocketTicks = websocketTicks;
  }

  public String getWebsocketIndicators() {
    return websocketIndicators;
  }

  public void setWebsocketIndicators(String websocketIndicators) {
    this.websocketIndicators = websocketIndicators;
  }

  public String getWebsocketStrategies() {
    return websocketStrategies;
  }

  public void setWebsocketStrategies(String websocketStrategies) {
    this.websocketStrategies = websocketStrategies;
  }

  public String getWebsocketReply() {
    return websocketReply;
  }

  public void setWebsocketReply(String websocketReply) {
    this.websocketReply = websocketReply;
  }

  public String getAmqpCamelComponent() {
    return amqpCamelComponent;
  }

  public void setAmqpCamelComponent(String amqpCamelComponent) {
    this.amqpCamelComponent = amqpCamelComponent;
  }

  public String getElasticsearchCamelComponent() {
    return elasticsearchCamelComponent;
  }

  public void setElasticsearchCamelComponent(String elasticsearchCamelComponent) {
    this.elasticsearchCamelComponent = elasticsearchCamelComponent;
  }

  public String getWebsocketCamelComponent() {
    return websocketCamelComponent;
  }

  public void setWebsocketCamelComponent(String websocketCamelComponent) {
    this.websocketCamelComponent = websocketCamelComponent;
  }

  public Integer getElasticsearchNativePort() {
    return elasticsearchNativePort;
  }

  public void setElasticsearchNativePort(Integer elasticsearchNativePort) {
    this.elasticsearchNativePort = elasticsearchNativePort;
  }

  public Integer getElasticsearchRestPort() {
    return elasticsearchRestPort;
  }

  public void setElasticsearchRestPort(Integer elasticsearchRestPort) {
    this.elasticsearchRestPort = elasticsearchRestPort;
  }

  public String getElasticsearchIndexForTickers() {
    return elasticsearchIndexForTickers;
  }

  public void setElasticsearchIndexForTickers(String elasticsearchIndexForTickers) {
    this.elasticsearchIndexForTickers = elasticsearchIndexForTickers;
  }

  public String getElasticsearchIndexForTicks() {
    return elasticsearchIndexForTicks;
  }

  public void setElasticsearchIndexForTicks(String elasticsearchIndexForTicks) {
    this.elasticsearchIndexForTicks = elasticsearchIndexForTicks;
  }

  public String getElasticsearchIndexForStrategies() {
    return elasticsearchIndexForStrategies;
  }

  public void setElasticsearchIndexForStrategies(String elasticsearchIndexForStrategies) {
    this.elasticsearchIndexForStrategies = elasticsearchIndexForStrategies;
  }

  public String getElasticsearchIndexForIndicators() {
    return elasticsearchIndexForIndicators;
  }

  public void setElasticsearchIndexForIndicators(String elasticsearchIndexForIndicators) {
    this.elasticsearchIndexForIndicators = elasticsearchIndexForIndicators;
  }

  public String getElasticsearchTypeForTickers() {
    return elasticsearchTypeForTickers;
  }

  public void setElasticsearchTypeForTickers(String elasticsearchTypeForTickers) {
    this.elasticsearchTypeForTickers = elasticsearchTypeForTickers;
  }

  public String getElasticsearchTypeForTicks() {
    return elasticsearchTypeForTicks;
  }

  public void setElasticsearchTypeForTicks(String elasticsearchTypeForTicks) {
    this.elasticsearchTypeForTicks = elasticsearchTypeForTicks;
  }

  public String getElasticsearchTypeForIndicators() {
    return elasticsearchTypeForIndicators;
  }

  public void setElasticsearchTypeForIndicators(String elasticsearchTypeForIndicators) {
    this.elasticsearchTypeForIndicators = elasticsearchTypeForIndicators;
  }

  public String getElasticsearchTypeForStrategies() {
    return elasticsearchTypeForStrategies;
  }

  public void setElasticsearchTypeForStrategies(String elasticsearchTypeForStrategies) {
    this.elasticsearchTypeForStrategies = elasticsearchTypeForStrategies;
  }

  public String getElasticsearchCluster() {
    return elasticsearchCluster;
  }

  public void setElasticsearchCluster(String elasticsearchCluster) {
    this.elasticsearchCluster = elasticsearchCluster;
  }

}
