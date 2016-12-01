package com.allowify.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;


public class AbstractAuthenticationProcessingFilterImpl extends
		AbstractAuthenticationProcessingFilter {

	/*@Autowired
	private VerifyRecaptchaService verifyRecaptcha;*/

	public AbstractAuthenticationProcessingFilterImpl() {
		super("/login");
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource
				.buildDetails(request));
	}

	/*
	 * @Override public Authentication attemptAuthentication(HttpServletRequest
	 * request, HttpServletResponse response) throws AuthenticationException {
	 * if (!request.getMethod().equals("POST")) { throw new
	 * AuthenticationServiceException("Authentication method not supported: " +
	 * request.getMethod()); } HttpServletResponse res = response;
	 * 
	 * try { String isRecaptcha = request .getParameter("is-recaptcha");
	 * if(!isRecaptcha.isEmpty() &&
	 * (request.getParameter("g-recaptcha-response") == null ||
	 * request.getParameter("g-recaptcha-response").isEmpty() ||
	 * VerifyRecaptcha.verify(request.getParameter("g-recaptcha-response")))){
	 * System.out.println("fail");
	 * //failureHandler.onAuthenticationFailure(request, response, new
	 * BadCredentialsException("Captcha invalid!"));
	 * //request.getRequestDispatcher("/").forward(request, response);
	 * response.sendRedirect("/login"); throw new
	 * AuthenticationServiceException("Authentication method not supported: " +
	 * request.getMethod());
	 * 
	 * }else{
	 * 
	 * System.out.println("running my own version of UsernmePasswordFilter ... ")
	 * ;
	 * 
	 * String username = obtainUsername(request); String password =
	 * obtainPassword(request);
	 * 
	 * if (username == null) { username = ""; }
	 * 
	 * if (password == null) { password = ""; }
	 * 
	 * username = username.trim();
	 * 
	 * UsernamePasswordAuthenticationToken authRequest = new
	 * UsernamePasswordAuthenticationToken(username, password);
	 * 
	 * // Allow subclasses to set the "details" property setDetails(request,
	 * authRequest);
	 * 
	 * return this.getAuthenticationManager().authenticate(authRequest);
	 * 
	 * }
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); throw new
	 * AuthenticationServiceException("Authentication Exception"); }
	 * 
	 * }
	 */

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		if (request.getMethod().equals("POST")) {
			String isRecaptcha = request.getParameter("is-recaptcha");
			if (isRecaptcha != null
					&& !isRecaptcha.isEmpty()
					/*&& (request.getParameter("g-recaptcha-response") == null
							|| request.getParameter("g-recaptcha-response")
									.isEmpty() || !verifyRecaptcha
								.verify(request
										.getParameter("g-recaptcha-response")))*/) {

				response.sendRedirect("?recaptcha");
			} else {
				// If the incoming request is a POST and Re-captcha varification
				// , then we send it up
				// to the AbstractAuthenticationProcessingFilter.
				super.doFilter(request, response, chain);
			}

		} else {
			// If it's a GET, we ignore this request and send it
			// to the next filter in the chain. In this case, that
			// pretty much means the request will hit the /login
			// controller which will process the request to show the
			// login page.
			chain.doFilter(request, response);
		}
	}

}