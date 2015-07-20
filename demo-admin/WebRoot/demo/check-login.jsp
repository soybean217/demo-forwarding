<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.demo.service.UserService"%>
<%@page import="org.demo.info.User"%>
<%
  String userName = new String(request.getParameter("username").trim());
  String pass = new String(request.getParameter("password").trim());
  //String roomCode = new String(request.getParameter("roomCode").trim());
  UserService userService = new UserService();
  //check user name 
  User user = new User();
  user.setUserName(userName);
  long userId = userService.checkLoginUser(pass, user);
  //System.out.println(userId);
  if (userId > 0) {
    request.getSession(true);
    //add global cache when begin change
    user = userService.getUserById(userId);
    session.setAttribute("user", user);
    //    response.sendRedirect("control.jsp?roomCode="+roomCode);
    if (session.getAttribute("lastFileName") != null
        && session.getAttribute("lastFileName").toString().length() > 0) {
      response.sendRedirect(session.getAttribute("lastFileName").toString());
      session.removeAttribute("lastFileName");
      return;
    } else {
      response.sendRedirect("console.jsp");
      return;
    }
  } else {
    out.print("<script>alert('Wrong Username or Password!');location.href='login.jsp';</script>");
    return;
  }
%>