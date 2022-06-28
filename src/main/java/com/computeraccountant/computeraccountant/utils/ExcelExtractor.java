package com.computeraccountant.computeraccountant.utils;

import com.computeraccountant.computeraccountant.exceptions.ProcessingException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;


@Component
public class ExcelExtractor {

  public static Optional<Map<Integer, ArrayList<String>>> extractRawData(String location) throws ProcessingException {
    try (FileInputStream file = new FileInputStream(location)) {
      Workbook workbook = new XSSFWorkbook(file);
      Formatter formatter = new Formatter(workbook);
      return  Optional.of(processing(Pair.of(workbook.getSheetAt(0), formatter)));

    } catch (Exception e) {
      throw new ProcessingException("Error trying to read file");
    }
  }

  private static Map<Integer, ArrayList<String>> processing(
      Pair<Sheet, Formatter> pairOfWorkBookAndFormatter) {
    Sheet sheet = pairOfWorkBookAndFormatter.getLeft();
    Formatter formatter = pairOfWorkBookAndFormatter.getRight();
    Map<Integer, ArrayList<String>> data = new HashMap<>();
    int i = 0;
    for (Row row : sheet) {
      data.put(i, new ArrayList<>());
      int lastColumn = Math.max(row.getLastCellNum(), 0);
      for (int cn = 0; cn < lastColumn; cn++) {
        Cell cell = row.getCell(cn, MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) {
          data.get(i).add("");
          continue;
        }
          switch (cell.getCellType()) {
            case STRING:
              data.get(i).add(cell.getRichStringCellValue().getString());
              break;
            case NUMERIC:
              if (DateUtil.isCellDateFormatted(cell)) {
                data.get(i).add(formatter.simpleDateFormat.format(cell.getDateCellValue()) + "");
              } else {
                data.get(i).add(cell.getNumericCellValue() + "");
              }
              break;
            default:
              data.get(i).add("lel ");
          }
        }
      i++;
    }
    return data;
  }

}
