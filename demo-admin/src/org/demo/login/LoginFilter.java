package org.demo.login;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LoginFilter implements Filter {
  
  private static final Logger LOG = Logger.getLogger(LoginFilter.class);

  private List<String> exceptList;

  public void destroy() {
    // TODO Auto-generated method stub

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String fileName = req.getRequestURI().split("/")[req.getRequestURI().split("/").length-1];
    boolean bExcept = false;
    for (String prefix : exceptList) {
    	if (fileName.startsWith(prefix)) {
    		bExcept = true;
    		break;
    	}
    }
    
    if (!bExcept){
      HttpSession session = ((HttpServletRequest) request).getSession();
      if (!(session!=null&&session.getAttribute("user")!=null)){
        session.setAttribute("lastFileName", fileName);
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect("login.jsp");
        return ;
      }
    }
//    String browserDet = ((HttpServletRequest) request).getHeader("User-Agent").toLowerCase();
//    if (browserDet.indexOf("msie") != -1) {
//      PrintWriter out = response.getWriter();
//      out.println("<html><head></head><body>");
//      out.println("<h1>Sorry, page cannot be displayed!</h1>");
//      out.println("</body></html>");
//      out.flush();
//      return;
//    }
    chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) throws ServletException {
		String exceptPages = filterConfig.getInitParameter("exceptPages");
		if (exceptPages != null) {
			exceptList = Arrays.asList(exceptPages.split(";"));
		} else {
			exceptList = Collections.emptyList();
		}
	}

}
