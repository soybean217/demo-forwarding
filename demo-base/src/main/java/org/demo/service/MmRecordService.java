package org.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.common.util.ConfigManager;
import org.common.util.GenerateId;
import org.demo.dao.ConnectionService;
import org.demo.info.MmRecord;

public class MmRecordService {

	public long addRecord(MmRecord mmRecord){
		long result = 0;
		long tmpId = GenerateId.generateNew(Integer.parseInt(ConfigManager.getConfigData("server.id")), "mm_records");
		if(tmpId > 0){
			PreparedStatement ps = null;
			Connection con = null;
			try{
				con = ConnectionService.getInstance().getConnectionForLocal();
				ps = con.prepareStatement("insert into mm_records " + "(`id`,`imei`,`imsi`,`cp_id`,`charge_id`,`ip`) values (?,?,?,?,?,?)");
				int m = 1;
				ps.setLong(m++, tmpId);
				ps.setString(m++, mmRecord.getImei());
				ps.setString(m++, mmRecord.getImsi());
				ps.setString(m++, mmRecord.getCpId());
				ps.setString(m++, mmRecord.getChargeId());
				ps.setString(m++, mmRecord.getIp());
				if(ps.executeUpdate() == 1){
					result = tmpId;
					mmRecord.setId(tmpId);
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

	public int updateContentByRecord(MmRecord mmRecord){
		int result = 0;
		PreparedStatement ps = null;
		Connection con = null;
		try{
			con = ConnectionService.getInstance().getConnectionForLocal();
			ps = con.prepareStatement("update mm_records set content=? where id=? ");
			int m = 1;
			ps.setString(m++, mmRecord.getContent());
			ps.setLong(m++, mmRecord.getId());
			result = ps.executeUpdate();
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
		return result;
	}
}
