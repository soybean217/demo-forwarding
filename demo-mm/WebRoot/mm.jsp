<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="org.demo.info.MmRecord"%>
<%@page import="org.demo.service.HttpSendMm"%>
<%@page import="org.demo.service.MmRecordService"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(this.getClass());
	//check available of parameter
	boolean checkResult = true;
	if(request.getParameter("cpId") == null){
		checkResult = false;
	}else if(request.getParameter("cpId").length() == 0){
		checkResult = false;
	}else if(request.getParameter("imei") == null){
		checkResult = false;
	}else if(request.getParameter("imei").length() == 0){
		checkResult = false;
	}else if(request.getParameter("imsi") == null){
		checkResult = false;
	}else if(request.getParameter("imsi").length() == 0){
		checkResult = false;
	}else if(request.getParameter("chargeId") == null){
		checkResult = false;
	}else if(request.getParameter("chargeId").length() == 0){
		checkResult = false;
	}
	if(checkResult){
		MmRecord mmRecord = new MmRecord();
		mmRecord.setCpId(request.getParameter("cpid"));
		mmRecord.setImei(request.getParameter("imei"));
		mmRecord.setImsi(request.getParameter("imsi"));
		mmRecord.setChargeId(request.getParameter("chargeId"));
		mmRecord.setIp(request.getRemoteAddr());

		MmRecordService mmRecordService = new MmRecordService();
		mmRecordService.addRecord(mmRecord);
		HttpSendMm httpSendMm = new HttpSendMm();
		httpSendMm.setMmRecord(mmRecord);
		httpSendMm.setSendUrl("http://127.0.0.1:8080/xxx.jsp");
		
		mmRecord.setContent(new String(httpSendMm.getHttpResponseContent(),"utf-8"));
		mmRecordService.updateContentByRecord(mmRecord);
		response.getWriter().print(mmRecord.getContent());
	}else{
		System.out.println("error");
		response.getWriter().print("error");
	}
%>