package org.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.demo.dao.ConnectionService;
import org.demo.info.User;

public class UserService {

  private static Random rnd = new Random();

  private long generateUserId() {
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
        ps = con.prepareStatement("select id from base_users where id = ?");
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

  public boolean checkAvailableUserName(String userName) {
    boolean result = false;
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    // max loop try time = 5
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      ps = con.prepareStatement("select id from base_users where username = ?");
      int m = 1;
      ps.setString(m++, userName);
      rs = ps.executeQuery();
      if (!rs.next()) {
        result = true;
      }
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
    return result;
  }

  public long addUser(String pwd, User user) {
    long result = 0;
    long tmpId = generateUserId();
    if (tmpId > 0) {
      PreparedStatement ps = null;
      Connection con = null;
      try {
        con = ConnectionService.getInstance().getConnectionForLocal();
        ps = con.prepareStatement("insert into base_users (id,username,pwd,email,add_time) values (?,?,md5(?),?,?)");
        int m = 1;
        ps.setLong(m++, tmpId);
        ps.setString(m++, user.getUserName());
        ps.setString(m++, pwd);
        ps.setString(m++, user.getEmail());
        ps.setLong(m++, System.currentTimeMillis());
        if (ps.executeUpdate() == 1) {
          result = tmpId;
        }
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
  
  public long addAnonymousUser() {
    long result = 0;
    long tmpId = generateUserId();
    if (tmpId > 0) {
      PreparedStatement ps = null;
      Connection con = null;
      try {
        con = ConnectionService.getInstance().getConnectionForLocal();
        ps = con.prepareStatement("insert into base_users (id,username,pwd,email,add_time) values (?,?,md5(?),?,?)");
        int m = 1;
        ps.setLong(m++, tmpId);
        ps.setString(m++, "anonymous-"+tmpId);
        ps.setString(m++, Long.toString(tmpId));
        ps.setString(m++, "a");
        ps.setLong(m++, System.currentTimeMillis());
        if (ps.executeUpdate() == 1) {
          result = tmpId;
        }
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
  
  

  public long checkLoginUser(String pwd, User user) {
    long result = 0;
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      ps = con.prepareStatement("select id from base_users where username=? and pwd=md5(?)");
      int m = 1;
      ps.setString(m++, user.getUserName());
      ps.setString(m++, pwd);
      rs = ps.executeQuery();
      if (rs.next()) {
        result = rs.getLong("id");
      }
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
    return result;
  }
  
  public User getUserById(long userId){
    User user = null;
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      ps = con.prepareStatement("select username,email from base_users where id=? ");
      int m = 1;
      ps.setLong(m++, userId);
      rs = ps.executeQuery();
      if (rs.next()) {
        user = new User();
        user.setEmail(rs.getString("email"));
        user.setUserName(rs.getString("username"));
        user.setId(userId);
      }
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
    return user;
  }
  
  /**
   * consume
   * real-time through database
   * @return balance . if balance < 0 consume failure
   * */
  public float consume(Long userId, float consumeValue){
    float result = 0;
//    User user = null;
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ConnectionService.getInstance().getConnectionForLocal();
      ps = con.prepareStatement("select balance from base_users where id=? ");
      int m = 1;
      ps.setLong(m++, userId);
      rs = ps.executeQuery();
      if (rs.next()) {
        float balance = rs.getFloat("balance");
        if (balance>=consumeValue){
          ps.execute("update balance = balance - ? from base_users where id = ?");
          //todo:lock and log
          result = balance - consumeValue;
        }else{
          result = -1f;
        }
      }
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
    return result ;
  }
}
