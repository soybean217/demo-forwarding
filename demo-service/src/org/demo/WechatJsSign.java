package org.demo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

public class WechatJsSign {

	private final static Logger LOG = Logger.getLogger(WechatJsSign.class);
	private String url = "";

	public WechatJsSign() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String jsapi_ticket = "jsapi_ticket";

		WechatJsSign wechatJsSign = new WechatJsSign();
		wechatJsSign.setUrl("http://xxx");
		Map<String, String> ret = wechatJsSign.sign(jsapi_ticket, wechatJsSign.getUrl());
		for (Map.Entry entry : ret.entrySet()) {
			LOG.debug(entry.getKey() + ", " + entry.getValue());
		}
	};

	public Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		LOG.debug(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
