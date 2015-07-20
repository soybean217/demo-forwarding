package org.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.common.util.ConfigManager;
import org.common.util.GenerateId;
import org.demo.dao.ConnectionService;
import org.demo.info.Receive;

public class ServiceReceive {

	public long addReceive(Receive receive){
		long result = 0;
		long tmpId = GenerateId.generateNew(Integer.parseInt(ConfigManager.getConfigData("server.id")), "receives");
		if(tmpId > 0){
			PreparedStatement ps = null;
			Connection con = null;
			try{
				con = ConnectionService.getInstance().getConnectionForLocal();
				ps = con.prepareStatement("insert into receives (id,phone,dest_addr,msg,link_id,service_id,add_time,from_ip,msg_type,annotation,send_status,status_report) values (?,?,?,?,?,?,?,?,?,?,?,?)");
				int m = 1;
				ps.setLong(m++, tmpId);
				ps.setLong(m++, receive.getPhone());
				ps.setString(m++, receive.getDestAddr());
				ps.setString(m++, receive.getMessage());
				ps.setString(m++, receive.getLinkId());
				ps.setString(m++, receive.getServiceId());
				ps.setLong(m++, System.currentTimeMillis());
				ps.setString(m++, receive.getFromIp());
				ps.setString(m++, receive.getMsgType());
				ps.setString(m++, receive.getAnnotation());
				ps.setInt(m++, receive.getSendStatus());
				ps.setInt(m++, receive.getStatusReport());
				if(ps.executeUpdate() == 1){
					result = tmpId;
				}
			}catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(con != null){
					try{
						con.close();
					}catch(SQLException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

}
