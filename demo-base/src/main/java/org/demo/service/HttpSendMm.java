package org.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.demo.info.MmRecord;

public class HttpSendMm {

	private MmRecord mmRecord;
	private String sendUrl;

	public String getSendUrl(){
		return sendUrl;
	}

	public void setSendUrl(String sendUrl){
		this.sendUrl = sendUrl;
	}

	public MmRecord getMmRecord(){
		return mmRecord;
	}

	public void setMmRecord(MmRecord mmRecord){
		this.mmRecord = mmRecord;
	}

	public byte[] getHttpResponseContent(){
	  // mm 专用进行编码转换
		byte[] result = "error".getBytes();
		CloseableHttpClient client = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		InputStream instreams = null;
		try{
			// TODO Auto-generated method stub
			// 创建HttpClient实例

			// 创建Get方法实例
			// HttpGet httpgets = new
			// HttpGet("http://127.0.0.1/testhttp.php?username=yonghuming");
			// httpgets = new HttpGet();
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
			// String getUrl = this.sendUrl + "?cpid=" + mmRecord.getCpId() +
			// "&imei=" + mmRecord.getImei() + "&imsi=" + mmRecord.getImsi() +
			// "&chargeId=" + mmRecord.getChargeId();
			String getUrl = this.sendUrl + "?imei=" + mmRecord.getImei() + "&imsi=" + mmRecord.getImsi() + "&chargeId=" + mmRecord.getChargeId();
			HttpGet request = new HttpGet(getUrl);
			System.out.println("get begin:" + getUrl);
			request.setConfig(requestConfig);
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if(entity != null){

				InputStream inputStream = entity.getContent();
				long length = entity.getContentLength();
				if(length <= 0)
					return result;
				int len = (int) length;
				byte[] b = new byte[len];
				int readCount = 0;
				while(readCount < len){
					readCount += inputStream.read(b, readCount, len - readCount);
				}
				result = b;

				// instreams = entity.getContent();
				// result = convertStreamToString(instreams);
				// System.out.println(result);
				// Do not need the rest
				request.abort();
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "error exception".getBytes();
		}finally{
			if(instreams != null){
				try{
					instreams.close();
				}catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(response != null){
				try{
					response.close();
				}catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// response.
			}
		}
		return result;
	}

	private String convertStreamToString(InputStream is){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try{
			while((line = reader.readLine()) != null){
				line = new String(line.getBytes("UTF-8"));
				sb.append(line + "\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				is.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
