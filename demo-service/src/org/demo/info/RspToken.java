package org.demo.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.common.util.ConnectionService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RspToken {

	private static final Logger LOG = Logger.getLogger(RspToken.class);

	@Expose
	@SerializedName("access_token")
	private String accessToken;
	@Expose
	@SerializedName("expires_in")
	private long expiresIn;
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "RspToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", appId=" + appId + "]";
	}

	public boolean verify() {
		if (accessToken != null && accessToken.length() > 0 && expiresIn > 0) {
			return true;
		} else {
			LOG.error("verify fail:" + this);
			return false;
		}
	}

	public RspToken updateDb() {
		LOG.info(this);
		// String sql = "update `tbl_wechat_tokens` set
		// token=?,nextTime=?,lastModTime=?,validTime=? where appId=?";
		String sql = "update `tbl_wechat_tokens` set token=?,lastModTime=?,validTime=? where appId=?";
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionService.getInstance().getConnectionForLocal();
			ps = con.prepareStatement(sql);
			int m = 1;
			Long now = System.currentTimeMillis();
			ps.setString(m++, accessToken);
			// ps.setLong(m++,
			// Long.parseLong(ConfigManager.getConfigData("nextSuccessInterval")) +
			// now);
			ps.setLong(m++, now);
			ps.setLong(m++, expiresIn * 1000 + now);
			ps.setString(m++, appId);
			if (ps.executeUpdate() == 0) {
				LOG.error("update failure:" + this);
			} else {
				LOG.info("updated:" + appId);
			}
		} catch (Exception e) {
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
		return this;
	}
}
