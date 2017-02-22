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
package org.jimsey.projects.turbine.inlet.external.parsing;

import static java.lang.String.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jimsey.projects.turbine.inlet.external.domain.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javaslang.Function1;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import javaslang.collection.Stream;
import javaslang.control.Try;

@Component
@ManagedResource
public class LseParser {

  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  @Value("${markets.lse.companies.file}")
  private String lseCompaniesFile;

  @Value("${markets.lse.securities.file}")
  private String lseSecuritiesFile;

  private final Integer headerRowNumber = 6;

  private final List<String> expectedHeaders = List.of("List Date", "Company", "Group", "Sector", "Sub Sector",
      "Country of Incorporation",
      "Market", "Mkt Cap £m", "International Main Market");

  // POI needs help parsing 'N' and 'Y' string cells into a boolean...
  private static Function1<String, Boolean> convertToBoolean = (v) -> "Y".equals(v) ? true : false;

  @PostConstruct
  public void init() {
    logger.info("markets.lse.companies.file: {}", lseCompaniesFile);
    logger.info("markets.lse.securities.file: {}", lseSecuritiesFile);

    List<Company> companies = parseCompanies(lseCompaniesFile);
    // companies.forEach(c -> logger.info(c.toString()));
  }

  private List<Company> parseCompanies(String input) {
    InputStream xls = Try.of(() -> new FileInputStream(input))
        .getOrElseThrow(t -> new RuntimeException(format("unable to open stream for %s, reason:%s", input, t.getMessage())));
    Workbook wb = Try.of(() -> WorkbookFactory.create(xls))
        .getOrElseThrow(t -> new RuntimeException(format("unable to open workbook %s, reason:%s", xls, t.getMessage())));
    Sheet sheet = wb.getSheetAt(0);

    // validate the sheet...
    Row headerRow = findAndValidateHeaderRow(sheet, headerRowNumber, expectedHeaders);

    // log out the cell types...
    logger.info(findHeaderCellTypes(sheet, headerRow)
        .map(t -> format("%s:%s", t._1, t._2))
        .reduce((a, b) -> format("%s, %s", a, b)));

    // parse the sheet into Company pojos...
    List<Company> companies = Stream.ofAll((Iterable<Row>) sheet)
        .filter(row -> row.getRowNum() > 6)
        .map(row -> Company.of(
            row.getCell(0).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            row.getCell(1).getStringCellValue(),
            (int) row.getCell(2).getNumericCellValue(),
            row.getCell(3).getStringCellValue(),
            row.getCell(4).getStringCellValue(),
            row.getCell(5).getStringCellValue(),
            row.getCell(6).getStringCellValue(),
            row.getCell(7).getNumericCellValue(),
            convertToBoolean.apply(row.getCell(8).getStringCellValue())))
        .toList();

    return companies;
  }

  private List<Tuple2<String, CellType>> findHeaderCellTypes(Sheet sheet, Row headersAtRow) {
    Row firstRow = sheet.getRow(headerRowNumber + 1);
    return Stream.ofAll(IteratorUtils.asIterable(headersAtRow.cellIterator()))
        .zip(IteratorUtils.asIterable(firstRow.cellIterator()))
        .map(t -> Tuple.of(t._1.getStringCellValue(), t._2.getCellTypeEnum()))
        .toList();
    // .toJavaMap(v -> Tuple.of(v._1.getStringCellValue(), v._2.getCellTypeEnum()));
    // .forEach(t -> logger.info("{}:{}", t._1.getStringCellValue(), t._2.getCellTypeEnum().toString()));
  }

  private Row findAndValidateHeaderRow(Sheet sheet, Integer headersAtRow, List<String> expected) {
    Row headerRow = sheet.getRow(headersAtRow);
    List<String> actualHeaders = Stream.ofAll(IteratorUtils.asIterable(headerRow.cellIterator()))
        .map(cell -> cell.getStringCellValue())
        .toList();
    boolean isValid = actualHeaders.containsAll(expected);
    if (!isValid) {
      throw new RuntimeException("The expected header row is not present in the expected place. Maybe the sheet has changed?");
    }
    return headerRow;
  }

}
