package org.common.util;

import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class GenerateId {

  static final Map<String, Long> currentTitleMap = new ConcurrentHashMap<String, Long>();
  static final Map<String, Vector<Integer>> currentTitleUsedMap = new ConcurrentHashMap<String, Vector<Integer>>();
  static Random rnd = new Random();
  static int count = 0;
  static int tempRnd = 0;

  /**
   * @param must
   *           serverId <=99
   * */
  static public synchronized long generateNew(int serverId, String key) {
    long result = 0L;
    long current = System.currentTimeMillis();
    // +serverId*1000
    if (currentTitleMap.containsKey(key)) {
      if (currentTitleMap.get(key).longValue()==current ) {
        int tempRnd = 0;
        count = 0;
        while (result == 0L && count < 5) {
          tempRnd = rnd.nextInt(999);
          if (!currentTitleUsedMap.get(key).contains(tempRnd)) {
            currentTitleUsedMap.get(key).add(tempRnd);
            result = current * 100000L + serverId * 1000L + tempRnd;
          }
          count++;
        }
      } else {
        currentTitleMap.put(key, current);
        currentTitleUsedMap.get(key).clear();
        tempRnd = 0;
        currentTitleUsedMap.get(key).add(tempRnd);
        result = current * 100000L + serverId * 1000L + tempRnd;
      }
    } else {
      tempRnd = 0;
      result = current * 100000L + serverId * 1000L + tempRnd;
      currentTitleMap.put(key, current);
      Vector<Integer> tmpVector = new Vector<Integer>();
      tmpVector.add(tempRnd);
      currentTitleUsedMap.put(key,tmpVector);
    }
    return result;
  }
}
