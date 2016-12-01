/*package com.allowify.intercepter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.allowify.model.Event;
import com.allowify.repository.EventRepository;



*//**
 * 
 * @author Bharat
 *
 *//*
@Service
public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {
	
//	private static final Logger logger = LoggerFactory
//			.getLogger(RequestProcessingTimeInterceptor.class);

	private Set<String> uriToSkipSet = new HashSet<String>();
	
	
	@Autowired
	private EventRepository eventRepository;
	
	
	public RequestProcessingTimeInterceptor() {
		uriToSkipSet.add("/health");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
//		logger.info("Request URL::" + request.getRequestURL().toString()
//				+ ":: Start Time=" + System.currentTimeMillis());
		request.setAttribute("startTime", startTime);
		// if returned false, we need to make sure 'response' is sent

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		System.out.println("Request URL::" + request.getRequestURL().toString()
//				+ " " + request.getMethod() + " " + request.getParameterMap()
//				+ " Sent to Handler :: Current Time="
//				+ System.currentTimeMillis());

		// we can add attributes in the modelAndView and use that in the view
		// page
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
//		logger.info("Request URL::" + request.getRequestURL().toString()
//				+ ":: End Time=" + System.currentTimeMillis());
//		logger.info("Request URL::" + request.getRequestURL().toString()
//				+ ":: Time Taken=" + (System.currentTimeMillis() - startTime));

		
		Event event = new Event();
		event.setDuration(System.currentTimeMillis() - startTime);
		event.setJson((String) request.getAttribute("INTERCEPTED_JSON"));
		event.setParameters(request.getParameterMap().toString());
		event.setStartTime(new Date());
		event.setUrl(request.getRequestURL().toString());
		event.setMethod(request.getMethod().toString());
		event.setUri(request.getRequestURI());
		
		//ToDo make this out of band
		
		if(skipSave(request.getRequestURI())) {
			eventRepository.save(event);
		}
		
	}
	
	private boolean skipSave(String uri) {
		return !uriToSkipSet.contains(uri);
	}

}*/