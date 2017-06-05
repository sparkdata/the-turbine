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
package org.jimsey.projects.turbine.condenser.domain.indicators.volume;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.jimsey.projects.turbine.condenser.domain.indicators.BaseIndicator;
import org.jimsey.projects.turbine.condenser.domain.indicators.EnableTurbineIndicator;

import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.volume.NVIIndicator;

/**
 * @author the-james-burton
 */
@EnableTurbineIndicator(name = "NegativeVolumeIndex", isOverlay = true)
public class NegativeVolumeIndex extends BaseIndicator {

  private final NVIIndicator negativeVolumeIndex;

  public NegativeVolumeIndex(final TimeSeries series, final ClosePriceIndicator indicator) {
    super(0, series, MethodHandles.lookup().lookupClass().getSimpleName(), indicator);

    // strategy idea: enter/exit when crossing 255-MA
    negativeVolumeIndex = new NVIIndicator(series);
  }

  @Override
  public Map<String, Double> computeValues() {
    Map<String, Double> values = new HashMap<>();
    values.put("negativeVolumeIndex", negativeVolumeIndex.getValue(series.getEnd()).toDouble());
    return values;
  }

}
