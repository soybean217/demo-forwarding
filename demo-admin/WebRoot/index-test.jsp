<%@page import="java.io.InputStream"%>
<%
  java.util.Enumeration headerNames = request.getHeaderNames();
  while (headerNames.hasMoreElements()) {
    String headerKey = (String) headerNames.nextElement();
    //System.out.println(headerKey + ":" + request.getHeader(headerKey));
  }
  String info = null;
  int len = 0;
  int temp = 0;
  InputStream is = request.getInputStream();
  byte[] b = new byte[10000];
  while ((temp = is.read()) != -1) {
    b[len] = (byte) temp;
    len++;
  }
  is.close();
  info = new String(b, 0, len, "utf-8");
  System.out.println("####notice:\n" + info);
  System.out.println("####end:" );
%>