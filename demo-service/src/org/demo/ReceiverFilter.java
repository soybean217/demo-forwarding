package org.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ReceiverFilter implements Filter {

  private static final Logger LOG = Logger.getLogger(ReceiverFilter.class);

  private List<String> exceptList;

  public void destroy() {
    // TODO Auto-generated method stub

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    LOG.debug(req.getRequestURI());

    String info = null;
    int len = 0;
    int temp = 0;
    InputStream is = request.getInputStream();
    byte[] b = new byte[1000000];
    while ((temp = is.read()) != -1) {
      b[len] = (byte) temp;
      len++;
    }
    is.close();
    info = new String(b, 0, len, "utf-8");
    if (info != null & info.length() > 0) {
      LOG.debug("####receive post:\n" + info);
      LOG.debug("####end:");
    }

    chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) throws ServletException {
    // String exceptPages = filterConfig.getInitParameter("exceptPages");
  }

}
