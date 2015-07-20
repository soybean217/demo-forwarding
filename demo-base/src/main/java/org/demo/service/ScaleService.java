package org.demo.service;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class ScaleService {

  static Map<String, Integer> serviceIdDateCountMap = new ConcurrentHashMap<String, Integer>();
  static Map<String, String> serviceIdDateMap = new ConcurrentHashMap<String, String>();

  static {
    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
  }
  static public int getServiceIdScale(String serviceId) {
    int result = 0;
    Calendar calendar = Calendar.getInstance();
    if (calendar.get(Calendar.HOUR_OF_DAY) < 9) {
      calendar.add(Calendar.DATE, -1);
    }
    String currentDay = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
        + calendar.get(Calendar.DAY_OF_MONTH);
    if (serviceIdDateMap.containsKey(serviceId)) {
      if (serviceIdDateMap.get(serviceId).equals(currentDay)){
        serviceIdDateCountMap.put(serviceId, serviceIdDateCountMap.get(serviceId)+1);
        if (serviceIdDateCountMap.get(serviceId)>50){
          result = 5 ;
        }
      }else{
        serviceIdDateMap.put(serviceId, currentDay);
        serviceIdDateCountMap.put(serviceId, 1);
        result = 0;
      }
    } else {
      serviceIdDateMap.put(serviceId, currentDay);
      serviceIdDateCountMap.put(serviceId, 1);
      result = 0;
    }
    return result;
  }

}
