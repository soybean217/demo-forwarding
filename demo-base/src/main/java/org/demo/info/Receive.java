package org.demo.info;

public class Receive {
	
	private long id;
	private long phone;
	private String destAddr;
	private String message;
	private String linkId;
	private String serviceId;
	private long addTime;
	private String fromIp;
	private int statusReport;
	private String msgType;
	private String annotation;
	private int sendStatus;
	private String additionalSendContent;
	public String getAdditionalSendContent(){
		return additionalSendContent;
	}
	public void setAdditionalSendContent(String additionalSendContent){
		this.additionalSendContent = additionalSendContent;
	}
	public int getSendStatus(){
		return sendStatus;
	}
	public void setSendStatus(int sendStatus){
		this.sendStatus = sendStatus;
	}
	public String getMsgType(){
		return msgType;
	}
	public void setMsgType(String msgType){
		this.msgType = msgType;
	}
	public String getAnnotation(){
		return annotation;
	}
	public void setAnnotation(String annotation){
		this.annotation = annotation;
	}
	public int getStatusReport(){
		return statusReport;
	}
	public void setStatusReport(int statusReport){
		this.statusReport = statusReport;
	}
	public String getFromIp(){
		return fromIp;
	}
	public void setFromIp(String fromIp){
		this.fromIp = fromIp;
	}
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public long getPhone(){
		return phone;
	}
	public void setPhone(long phone){
		this.phone = phone;
	}
	public String getDestAddr(){
		return destAddr;
	}
	public void setDestAddr(String destAddr){
		this.destAddr = destAddr;
	}
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public String getLinkId(){
		return linkId;
	}
	public void setLinkId(String linkId){
		this.linkId = linkId;
	}
	public String getServiceId(){
		return serviceId;
	}
	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}
	public long getAddTime(){
		return addTime;
	}
	public void setAddTime(long addTime){
		this.addTime = addTime;
	}
}
