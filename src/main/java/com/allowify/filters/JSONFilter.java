package com.allowify.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.allowify.intercepter.RequestWrapper;



/**
 * 
 * @author Bharat
 *
 */
@WebFilter(urlPatterns = { "/*" })
public class JSONFilter implements Filter {

    //@Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

	//@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
      RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
		
      String body = myRequestWrapper.getBody();      
      request.setAttribute("INTERCEPTED_JSON", body);      
      
      chain.doFilter(myRequestWrapper, response);		
	}

	//@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}