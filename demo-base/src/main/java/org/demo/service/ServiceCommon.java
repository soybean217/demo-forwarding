package org.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.demo.dao.ConnectionService;

public class ServiceCommon {

  private static Random rnd = new Random();

  public static long generateIdByTableName(String table) {
    // perhaps need lock when site become hot
    long result = 0;
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    // max loop try time = 5
    for (int i = 0; i < 5; i++) {
      long tmp = System.currentTimeMillis() * 100000L + rnd.nextInt(99999);
      try {
        con = ConnectionService.getInstance().getConnectionForLocal();
        ps = con.prepareStatement("select id from "+table+" where id = ?");
        int m = 1;
        ps.setLong(m++, tmp);
        rs = ps.executeQuery();
        if (!rs.next()) {
          result = tmp;
          break;
        }
        Thread.sleep(1);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } finally {
        if (con != null) {
          try {
            con.close();
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }
    return result;
  }

}
