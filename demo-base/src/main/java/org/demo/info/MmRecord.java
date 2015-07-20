package org.demo.info;

public class MmRecord {
	
	private long id;
	private String imei;
	private String imsi;
	private String cpId;
	private String chargeId;
	private String content;
	private String ip;
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public String getImei(){
		return imei;
	}
	public void setImei(String imei){
		this.imei = imei;
	}
	public String getImsi(){
		return imsi;
	}
	public void setImsi(String imsi){
		this.imsi = imsi;
	}
	public String getCpId(){
		return cpId;
	}
	public void setCpId(String cpId){
		this.cpId = cpId;
	}
	public String getChargeId(){
		return chargeId;
	}
	public void setChargeId(String chargeId){
		this.chargeId = chargeId;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}

}
