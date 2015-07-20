package org.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.demo.info.Receive;

public class HttpSend implements Runnable {

	private Receive receive;
	private String sendUrl;

	public String getSendUrl(){
		return sendUrl;
	}

	public void setSendUrl(String sendUrl){
		this.sendUrl = sendUrl;
	}

	public Receive getReceive(){
		return receive;
	}

	public void setReceive(Receive receive){
		this.receive = receive;
	}

	@Override
	public void run(){
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
			String getUrl = this.sendUrl + "?id=" + receive.getId() + "&phone=" + receive.getPhone() + "&mo=" + receive.getMessage() + "&status=" + receive.getStatusReport() + "&target=" + receive.getDestAddr() + (receive.getAdditionalSendContent() != null && receive.getAdditionalSendContent().length() > 0 ? receive.getAdditionalSendContent() : "");
			HttpGet request = new HttpGet(getUrl);
			System.out.println("get begin:" + getUrl);
			request.setConfig(requestConfig);
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				System.out.println(str);
				// Do not need the rest
				request.abort();
			}
		}catch(ClientProtocolException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IllegalStateException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	}

	private String convertStreamToString(InputStream is){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try{
			while((line = reader.readLine()) != null){
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
