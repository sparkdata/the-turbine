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
package org.jimsey.projects.turbine.condenser.domain.indicators.trackers;

import java.util.HashMap;
import java.util.Map;

import org.jimsey.projects.turbine.condenser.domain.indicators.BaseIndicator;
import org.jimsey.projects.turbine.condenser.domain.indicators.IndicatorInstance;

import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.keltner.KeltnerChannelLowerIndicator;
import eu.verdelhan.ta4j.indicators.trackers.keltner.KeltnerChannelMiddleIndicator;
import eu.verdelhan.ta4j.indicators.trackers.keltner.KeltnerChannelUpperIndicator;

public class KeltnerChannels extends BaseIndicator {

  private KeltnerChannelLowerIndicator keltnerChannelLower;
  private KeltnerChannelMiddleIndicator keltnerChannelMiddle;
  private KeltnerChannelUpperIndicator keltnerChannelUpper;

  public KeltnerChannels(IndicatorInstance instance, TimeSeries series, ClosePriceIndicator closePriceIndicator) {
    super(instance, series, closePriceIndicator);
  }

  @Override
  protected void init() {
    validateThree();
    keltnerChannelMiddle = new KeltnerChannelMiddleIndicator(series, instance.getTimeframe1());
    keltnerChannelUpper = new KeltnerChannelUpperIndicator(
        keltnerChannelMiddle, Decimal.valueOf(instance.getTimeframe2()), instance.getTimeframe3());
    keltnerChannelLower = new KeltnerChannelLowerIndicator(
        keltnerChannelMiddle, Decimal.valueOf(instance.getTimeframe2()), instance.getTimeframe3());
  }

  @Override
  public Map<String, Double> computeValues() {
    Map<String, Double> values = new HashMap<>();
    values.put(instance.generateName("keltnerChannelLower"), keltnerChannelLower.getValue(series.getEnd()).toDouble());
    values.put(instance.generateName("keltnerChannelMiddle"), keltnerChannelMiddle.getValue(series.getEnd()).toDouble());
    values.put(instance.generateName("keltnerChannelUpper"), keltnerChannelUpper.getValue(series.getEnd()).toDouble());
    return values;
  }

}
