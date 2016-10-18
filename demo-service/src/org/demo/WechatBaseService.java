package org.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.common.util.ConnectionService;
import org.demo.info.WechatToken;

public class WechatBaseService {

	private static final Logger LOG = Logger.getLogger(WechatBaseService.class);
	private static WechatBaseService instance = new WechatBaseService();

	private WechatBaseService() {
	}

	public static WechatBaseService getInstance() {
		return instance;
	}

	public String sendWechatInterface(String url, HttpEntity entity) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			httppost.setEntity(entity);

			LOG.debug("Executing request: " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				LOG.debug("----------------------------------------");
				LOG.debug(response.getStatusLine());
				result = EntityUtils.toString(response.getEntity());
				LOG.debug(result);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	public WechatToken getToken(String appId) throws SQLException, IllegalArgumentException, IllegalStateException {
		WechatToken result = new WechatToken();
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionService.getInstance().getConnectionForLocal();
			String sql = "SELECT * FROM tbl_wechat_tokens where appId=?;";
			ps = con.prepareStatement(sql);
			int m = 1;
			ps.setString(m++, appId);
			rs = ps.executeQuery();
			if (rs.next()) {
				result.setAppId(rs.getString("appId"));
				result.setToken(rs.getString("token"));
				result.setNextTime(rs.getLong("nextTime"));
				result.setLastModTime(rs.getLong("lastModTime"));
				result.setValidTime(rs.getLong("validTime"));
				result.setSecret(rs.getString("secret"));
				if (!result.isInValidTime()) {
					throw new IllegalStateException("Wechat token expired .appID:" + appId);
				}
			} else {
				throw new IllegalArgumentException("Can not find token of  appId:" + appId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private String getTokenFromWechat(WechatToken wechatToken) throws ClientProtocolException, IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wechatToken.getAppId()
				+ "&secret=" + wechatToken.getSecret();
		return sendWechatInterface(url, null);
	}

	public static void main(String[] args) throws IllegalArgumentException, SQLException {
		LOG.debug(WechatBaseService.getInstance().getToken(""));
	}
}
