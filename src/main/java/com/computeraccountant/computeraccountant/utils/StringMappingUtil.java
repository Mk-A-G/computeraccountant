package com.computeraccountant.computeraccountant.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringMappingUtil {

  private StringMappingUtil() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(StringMappingUtil.class);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String mapObjectToJSON(Object object) {
    try {

      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOG.error(e.getMessage());
      return "";
    }
  }

  public static  <T> T mapFromJson(String request, Class<T> clazz) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      return objectMapper.readValue(request, clazz);
    } catch (JsonProcessingException e) {
      LOG.error(e.getMessage());
      return null;
    }


  }

  public static  java.sql.Date mapDateToProstgresDate(String rawDate) {

    final String OLD_FORMAT = "dd/MM/yyyy";
    final String NEW_FORMAT = "yyyy-MM-dd";

    try {
    String newDateString;

    SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
    Date d = sdf.parse(rawDate);
    sdf.applyPattern(NEW_FORMAT);
    newDateString = sdf.format(d);
    return java.sql.Date.valueOf(newDateString);
    } catch (ParseException e) {
      LOG.error(e.getMessage());
      return null;
    }
  }
}
