package com.jyothigas.app.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.jyothigas.service.EmailService;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	  @Override
	  protected Class<?>[] getRootConfigClasses() {
	    return new Class<?>[] {RootConfig.class , SecurityConfig.class,EmailService.class};
	  }
	  
	  @Override
	  protected Class<?>[] getServletConfigClasses() {
	    return new Class<?>[] {WebConfig.class};
	  }
	  
	  @Override
	  protected String[] getServletMappings() {
	    return new String[] {"/"};
	  }
	  
	  @Override
	  public void onStartup(ServletContext servletContext) throws ServletException {
	    super.onStartup(servletContext);
	  }
}
