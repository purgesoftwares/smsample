/** Copyright © 2015 by 1986 Tech, LLC. All Rights Reserved. **/

package com.allowify.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 
 * @author Bharat
 *
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

	//@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		 response.sendRedirect("secure/app");
		
	}

}
