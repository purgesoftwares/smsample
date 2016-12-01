package com.allowify.controller.main;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.allowify.model.User;
import com.allowify.user.service.UserDao;




/**
 * 
 * @author Bharat
 *
 */
@Controller
public class PageController {

	@Autowired
	private Environment env;
	

	/*@Autowired
	private VerificationTokenRepository verificationTokenRepository;*/
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;
		
	@Autowired
	private MessageSource messageSource;

	// View JSP template name for welcome(main root)
	private static final String WELCOME_TEMPLATE = "welcome";
	
    // View JSP template name for signup page.
    private static final String SIGNUP_TEMPLATE = "signup";

	
    @RequestMapping("/")
    public String welcome(
    		Locale locale,
    		HttpSession session,
      		@RequestParam(value = "error", required = false) String error,
      		@RequestParam(value = "blocked", required = false) String blocked,
      		@RequestParam(value = "recaptcha", required = false) String recaptcha,
      		@RequestParam(value = "logout", required = false) String logout,
      		@RequestParam(value = "updated", required = false) String updated,
      		@RequestParam(value = "success", required = false) String success,
      		@RequestParam(value = "invalidConfirm", required = false) String invalidConfirm,
      		@RequestParam(value = "expiredConfirm", required = false) String expiredConfirm,
      		@RequestParam(value = "successConfirm", required = false) String successConfirm,
      		@RequestParam(value = "resent", required = false) String resent,
      		Model model,final HttpServletRequest request) throws IOException {
    		
    	   	  
    	// Redirect the user to the secure/app, If user is logged in and not Anonymous
    	if (!authenticationTrustResolver.isAnonymous(
    			SecurityContextHolder.getContext().getAuthentication())) {
    		return "redirect:/secure/app";
	    }
        	    		
    	if (error != null) {
  			model.addAttribute("error",
  					messageSource.getMessage("user.errorInvalid",null,locale));
  		}
    	if (blocked != null) {
  			model.addAttribute("error", 
  					messageSource.getMessage("user.errorInvalid",null,locale));
  		}
    	
  		if(recaptcha  != null){
  			model.addAttribute("recaptcha",
  					messageSource.getMessage("user.errorBlocked",null,locale));
  		}	
  		if(invalidConfirm  != null){
  			model.addAttribute("error",
  					messageSource.getMessage("user.invalidConfirm",null,locale));
  		}
  		if(expiredConfirm  != null){
  			model.addAttribute("error",
  					messageSource.getMessage("user.expiredConfirm",null,locale));
  		}
  		
  		if (logout != null) {
  			model.addAttribute("msg",
  					messageSource.getMessage("user.logoutSuccess",null,locale));
  		}
  		if (updated != null) {
  			model.addAttribute("msg",
  					messageSource.getMessage("user.updatePassword",null,locale));
  		}
  		if (success != null) {
  			model.addAttribute("msg",
  					messageSource.getMessage("user.successRegistration",null,locale));
  		}
  		if (successConfirm != null) {
  			model.addAttribute("msg",
  					messageSource.getMessage("user.successConfirm",null,locale));
  		}
  		if (resent != null) {
  			model.addAttribute("msg",
  					messageSource.getMessage("user.resendToken",null,locale));
  		}
  	  		 		
        return WELCOME_TEMPLATE;
    }
    
    @RequestMapping("secure/app")
    public String app(HttpSession session, final HttpServletRequest request,final HttpServletResponse response, Model model) {
    	
    	final User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        	
    	// Set locale for user, if the locale is set in User in db 
    	if(user.getLocale() != null){
    		RequestContextUtils.getLocaleResolver(request).setLocale(request, response,StringUtils.parseLocaleString(user.getLocale().toString()));
    	}
    	
    /*	// If user is not verified, notVerified attribute set in the model to show the message in toastr in secure/app
    	if(!user.isVerified()){
    		final VerificationToken newToken = verificationTokenRepository.findByUser(user);
    		if(newToken != null){
    			// Generates link for resends verification mail, If user not verified and wants to resends verification email
	    		final String resendVerificationLink = "http://" + request.getServerName() 
	    				+ ":" + request.getServerPort() + request.getContextPath() 
	    				+ API_VERSION + "/user/resend-registration-confirmation?token=" + newToken.getToken();
	    		model.addAttribute("notVerified", true);
	    		model.addAttribute("resendVerificationLink", resendVerificationLink);
    		}
    	}*/
    	// Get message from the session if any message have to show in secure/app toastr 
    	// And set the message attribute to show 
    	final String message = (String) session.getAttribute("message");
    	
    	if(message != null){
    		model.addAttribute("message", message);
    		session.removeAttribute("message");
    	}
    	
        return "secure/app";
    }
    
    @RequestMapping("/login")
    public String login(
    		Locale locale,
    		HttpSession session,
    		@RequestParam(value = "error", required = false) String error,
    		@RequestParam(value = "blocked", required = false) String blocked,
    		@RequestParam(value = "recaptcha", required = false) String recaptcha,
    		@RequestParam(value = "logout", required = false) String logout,
    		Model model,final HttpServletRequest request) {
    	  
    	// Redirect the user to the secure/app, If user is logged in and not Anonymous
    	if (!authenticationTrustResolver.isAnonymous(SecurityContextHolder
    			.getContext().getAuthentication())) {
    		return "redirect:/secure/app";
    	}

    	int loginAttempts = 1;
    	
    	if (session.getAttribute("loginAttempts") != null) {
    		loginAttempts = (Integer) session.getAttribute("loginAttempts");
    	}

    	if (error != null) {
    		model.addAttribute("error",
    				messageSource.getMessage("user.errorInvalid",null,locale));
    		loginAttempts++;
    		session.setAttribute("loginAttempts", loginAttempts);
    	}
    	
    	if (blocked != null) {
    		model.addAttribute("error",
    				messageSource.getMessage("user.errorBlocked",null,locale));
    	}
    	
    	if (recaptcha != null) {
    		model.addAttribute("recaptcha",
    				messageSource.getMessage("user.recaptcha",null,locale));
    	}
    	
    	if (logout != null) {
    		model.addAttribute("msg",
    				messageSource.getMessage("user.logoutSuccess",null,locale));
    	}

    	model.addAttribute("isRecaptcha", (loginAttempts > 3) ? 1 : 0);

    	return "login";
    }
    
    
    
	/**
	 * Method: showRegisterationPage
	 * ==============================
	 * This method displays the page where you can manually register.
	 * It does not need an API version prefix. Return web-page
	 * @param already
	 * @param nomatch
	 * @param empty
	 * @param invalid
	 * @param model
	 * @param locale
	 * @return
	 */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showRegisterationPage(@RequestParam(value = "already", required = false) String already,
      		@RequestParam(value = "nomatch", required = false) String nomatch,
      		@RequestParam(value = "empty", required = false) String empty,
      		@RequestParam(value = "invalid", required = false) String invalid,
      		Model model,
      		Locale locale) {

  		if (empty != null){
  			model.addAttribute("error", messageSource.getMessage("user.errorFillFields", null, locale));
  		}	
  		if (invalid != null){
  			model.addAttribute("error", messageSource.getMessage("user.invalidEmail", null, locale));
  		}	
  		if (nomatch != null) {
  			model.addAttribute("error", messageSource.getMessage("user.errorConfirmPass", null, locale));
  		}
  		if (already != null) {
  			model.addAttribute("error", messageSource.getMessage("user.alreadyExists", null, locale));
  		}
  		
       return SIGNUP_TEMPLATE;
    }
    
    
	/**
	 * Method: facebookConnect
	 * ==============================
	 * This method displays the page to connect with facebook account
	 * From the user view in secure/app.
	 * It does not need an API version prefix. Return web-page
	 * @param session
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value="/facebook/connect", method=RequestMethod.GET)
	public String facebookConnect(
			HttpSession session,
			ModelMap model,
			HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException {
		model.addAttribute("isSocialConnected", true);
		return "connect/connect";
	}

	*//**
	 * Method: connect
	 * ==============================
	 * This method displays the page to connect with social account
	 * From the user view in secure/app.
	 * It does not need an API version prefix. Return web-page
	 * @param session
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value="/social/connect/{providerId}", method=RequestMethod.GET)
	public String connect(
			HttpSession session,
			Model model,
			@PathVariable String providerId,
			NativeWebRequest request) {
		session.setAttribute("socialConnectDest", "profile");
		model.addAttribute("socialConnectType", providerId);
		return "connect/connect";
	}
	*/

}